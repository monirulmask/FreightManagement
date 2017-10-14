package com.tendereasy.freightmanagement.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.tendereasy.freightmanagement.dao.IFreightCostCalculationDao;
import com.tendereasy.freightmanagement.dto.*;
import com.tendereasy.freightmanagement.util.DFSGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by monir on 10/5/2017.
 */
@Service
public class FreightCostCalculationService implements IFreightCostCalculationService {

    private static final String API_KEY = "AIzaSyBrVPkbE0U46p8i40X7QVL1mdspKu1pqdI";
    private static final GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
    @Autowired
    private IFreightCostCalculationDao freightCostCalculationDao;

    @Override
    public EmployeeDTO getEmployee() {
        return freightCostCalculationDao.getEmployeesDTO();
    }

    @Override
    public List<AllPossibleRouteDetailsDTO> searchRouteForScenarioOne(SearchCriteriaDTO searchCriteriaDTO) {
        List<AllPossibleRouteDetailsDTO> routeList = new ArrayList<>();
        Set<Integer> uniqueRoute = new HashSet<Integer>();
        String modeOfTransportQuery = "";

        //get Source and destination ID
        Integer sourceLocationID = freightCostCalculationDao.getLocationID(searchCriteriaDTO.getSource());
        Integer destinationLocationID = freightCostCalculationDao.getLocationID(searchCriteriaDTO.getDestination());

        if (sourceLocationID == null || destinationLocationID == null) {
            return routeList;
        }

        Boolean isModeAll = searchCriteriaDTO.getModeOfTransports().contains("All");
        if (!isModeAll) {
            String delim = searchCriteriaDTO.getModeOfTransports().stream()
                    .map((s) -> "\"" + s + "\"")
                    .collect(Collectors.joining(", "));

            modeOfTransportQuery = " AND lc.modeOfTransports IN(".concat(delim).concat(")");
        }

        List<RouteDetailsDTO> routeDetailsDTOList = freightCostCalculationDao.getAllRouteListByInitialCriteria(searchCriteriaDTO, modeOfTransportQuery);


        DFSGraph graph = new DFSGraph();

        //Adding all nodes
        //Finding unique route number
        for (RouteDetailsDTO routeDetailsDTO : routeDetailsDTOList) {
            uniqueRoute.add(routeDetailsDTO.getSourceID());
            uniqueRoute.add(routeDetailsDTO.getDestinationID());
            graph.addEdge(routeDetailsDTO.getSourceID(), routeDetailsDTO.getDestinationID());
        }
        Integer edge = Collections.max(uniqueRoute)+1;
        graph.setV(edge);
        List<List<Integer>> possiblePathList = graph.getAllPaths(sourceLocationID, destinationLocationID);

        if (possiblePathList != null && !possiblePathList.isEmpty()) {
            for (List<Integer> possiblePath : possiblePathList) {
                BigDecimal totalCost = BigDecimal.ZERO;
                Integer totalDuration = 0;
                AllPossibleRouteDetailsDTO allPossibleRouteDetailsDTO = new AllPossibleRouteDetailsDTO();
                List<ResponseRouteDetailsDTO> responseRouteDetailsDTOList = new ArrayList<>();
                for (Integer i = 0; i < possiblePath.size() - 1; i++) {
                    Integer source = possiblePath.get(i);
                    Integer destination = possiblePath.get(i + 1);
                    RouteDetailsDTO routeDetailsDTO = routeDetailsDTOList.stream().
                            filter(x -> x.getSourceID() == source && x.getDestinationID() == destination)
                            .findAny()
                            .orElse(null);
                    if (routeDetailsDTO != null) {
                        ResponseRouteDetailsDTO responseRouteDetailsDTO = convertToResponseRouteDetailsDTO(routeDetailsDTO);
                        totalCost = totalCost.add(responseRouteDetailsDTO.getCost());
                        totalDuration += responseRouteDetailsDTO.getDuration();
                        responseRouteDetailsDTOList.add(responseRouteDetailsDTO);
                    }
                }

                if (isValidateCostAndDuration(searchCriteriaDTO, totalCost, totalDuration)) {
                    allPossibleRouteDetailsDTO.setRoute(responseRouteDetailsDTOList);
                    allPossibleRouteDetailsDTO.setTotalCost(totalCost);
                    allPossibleRouteDetailsDTO.setTotalDuration(totalDuration);
                    routeList.add(allPossibleRouteDetailsDTO);
                }
            }
        }

        return routeList;
    }

    @Override
    public List<AllPossibleRouteDetailsDTO> searchRouteForScenarioTwo(SearchCriteriaDTO searchCriteriaDTO) throws Exception {
        List<AllPossibleRouteDetailsDTO> routeList = new ArrayList<>();
        Set<Integer> uniqueRoute = new HashSet<Integer>();
        String modeOfTransportQuery = "";

        //get Source and destination ID
        Integer sourceLocationID = freightCostCalculationDao.getLocationID(searchCriteriaDTO.getSource());
        Integer destinationLocationID = null;//freightCostCalculationDao.getLocationID(searchCriteriaDTO.getDestination());

        if (sourceLocationID != null && destinationLocationID == null) {
            List<Integer> derivedLocation = getDerivedLocation(searchCriteriaDTO.getSource() ,searchCriteriaDTO.getDestination());
            for(Integer derivedLocationID : derivedLocation){
                //Boolean isModeAll = Stream.of().anyMatch(x -> x.equals("All"));
                Boolean isModeAll = searchCriteriaDTO.getModeOfTransports().contains("All");
                if (!isModeAll) {
                    String delim = searchCriteriaDTO.getModeOfTransports().stream()
                            .map((s) -> "\"" + s + "\"")
                            .collect(Collectors.joining(", "));

                    modeOfTransportQuery = " AND lc.modeOfTransports IN(".concat(delim).concat(")");
                }

                List<RouteDetailsDTO> routeDetailsDTOList = freightCostCalculationDao.getAllRouteListByInitialCriteria(searchCriteriaDTO, modeOfTransportQuery);


                DFSGraph graph = new DFSGraph();

                //Adding all nodes
                //Finding unique route number
                for (RouteDetailsDTO routeDetailsDTO : routeDetailsDTOList) {
                    uniqueRoute.add(routeDetailsDTO.getSourceID());
                    uniqueRoute.add(routeDetailsDTO.getDestinationID());
                    graph.addEdge(routeDetailsDTO.getSourceID(), routeDetailsDTO.getDestinationID());
                }
                Integer edge = Collections.max(uniqueRoute)+1;
                graph.setV(edge);
                List<List<Integer>> possiblePathList = graph.getAllPaths(sourceLocationID, derivedLocationID);

                if (possiblePathList != null && !possiblePathList.isEmpty()) {
                    for (List<Integer> possiblePath : possiblePathList) {
                        BigDecimal totalCost = BigDecimal.ZERO;
                        Integer totalDuration = 0;
                        AllPossibleRouteDetailsDTO allPossibleRouteDetailsDTO = new AllPossibleRouteDetailsDTO();
                        List<ResponseRouteDetailsDTO> responseRouteDetailsDTOList = new ArrayList<>();
                        for (Integer i = 0; i < possiblePath.size() - 1; i++) {
                            Integer source = possiblePath.get(i);
                            Integer destination = possiblePath.get(i + 1);
                            RouteDetailsDTO routeDetailsDTO = routeDetailsDTOList.stream().
                                    filter(x -> x.getSourceID() == source && x.getDestinationID() == destination)
                                    .findAny()
                                    .orElse(null);
                            if (routeDetailsDTO != null) {
                                ResponseRouteDetailsDTO responseRouteDetailsDTO = convertToResponseRouteDetailsDTO(routeDetailsDTO);
                                totalCost = totalCost.add(responseRouteDetailsDTO.getCost());
                                totalDuration += responseRouteDetailsDTO.getDuration();
                                responseRouteDetailsDTOList.add(responseRouteDetailsDTO);
                            }
                        }
                        allPossibleRouteDetailsDTO.setRoute(responseRouteDetailsDTOList);
                        allPossibleRouteDetailsDTO.setTotalCost(totalCost);
                        allPossibleRouteDetailsDTO.setTotalDuration(totalDuration);
                        routeList.add(allPossibleRouteDetailsDTO);
                    }
                }
            }

        }

        return routeList;
    }

    private List<Integer> getDerivedLocation(String sourceName, String destinationName) throws Exception {
        List<Integer> derivedLocation = new ArrayList<>();
        List<LocationInfoDTO> locationInfoDTOList = freightCostCalculationDao.getLocationInfoDTOList(sourceName);
        for (LocationInfoDTO locationInfoDTO : locationInfoDTOList) {
            GeocodingResult geocodingResult = getLatLongByCity(destinationName);
            if (geocodingResult != null) {
                LatLng origin = new LatLng(locationInfoDTO.getLatitude(), locationInfoDTO.getLongitude());
                LatLng destination = new LatLng(geocodingResult.geometry.location.lat, geocodingResult.geometry.location.lng);
                DistanceMatrix distanceMatrix = estimateRouteDistance(origin, destination);
                if (distanceMatrix.rows[0].elements[0].distance != null && distanceMatrix.rows[0].elements[0].distance.inMeters <= 50000) {
                    derivedLocation.add(locationInfoDTO.getLocationID());
                }
            }
        }
        return derivedLocation;
    }

    private DistanceMatrix estimateRouteDistance(LatLng origin, LatLng destination) throws Exception {
        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
        DistanceMatrix trix = req.origins(origin)
                .destinations(destination)
                .mode(TravelMode.DRIVING)
                .await();
        return trix;
    }

    private GeocodingResult getLatLongByCity(String cityName) throws Exception {
        GeocodingResult[] results = new GeocodingResult[0];
        results = GeocodingApi.geocode(context,
                cityName).await();
        return results.length > 0 ? results[0] : null;
    }


    private boolean isValidateCostAndDuration(SearchCriteriaDTO searchCriteriaDTO, BigDecimal totalCost, Integer totalDuration) {
        if (searchCriteriaDTO.getDurationFrom() != null && searchCriteriaDTO.getDurationTo() != null && !(searchCriteriaDTO.getDurationFrom() <= totalDuration && searchCriteriaDTO.getDurationTo() >= totalDuration)) {
            return false;
        }

        if (searchCriteriaDTO.getCostFrom() != null && searchCriteriaDTO.getCostTo() != null && !(searchCriteriaDTO.getCostFrom().compareTo(totalCost) < 1 && searchCriteriaDTO.getCostTo().compareTo(totalCost) > -1)) {
            return false;
        }
        return true;
    }

    private ResponseRouteDetailsDTO convertToResponseRouteDetailsDTO(RouteDetailsDTO routeDetailsDTO) {
        ResponseRouteDetailsDTO responseRouteDetailsDTO = new ResponseRouteDetailsDTO();
        responseRouteDetailsDTO.setFrom(routeDetailsDTO.getSourceName());
        responseRouteDetailsDTO.setTo(routeDetailsDTO.getDestinationName());
        responseRouteDetailsDTO.setTransportType(routeDetailsDTO.getModeOfTransports());
        responseRouteDetailsDTO.setDuration(routeDetailsDTO.getDuration());
        responseRouteDetailsDTO.setCost(routeDetailsDTO.getCost());
        return responseRouteDetailsDTO;
    }

}
