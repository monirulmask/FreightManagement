package com.tendereasy.freightmanagement.service;

import com.tendereasy.freightmanagement.dao.IFreightCostCalculationDao;
import com.tendereasy.freightmanagement.dto.*;
import com.tendereasy.freightmanagement.model.EmployeeEntity;
import com.tendereasy.freightmanagement.util.DFSGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by monir on 10/5/2017.
 */
@Service
public class FreightCostCalculationService implements IFreightCostCalculationService {

    @Autowired
    private IFreightCostCalculationDao freightCostCalculationDao;

    @Override
    public List<EmployeeEntity> getAllEmployees() {
        return freightCostCalculationDao.getAllEmployees();
    }

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

        /*if (false && sourceLocationID == null || destinationLocationID == null) {
            return routeList;
        }*/

        if (searchCriteriaDTO.getModeOfTransports() != null && !Arrays.asList(searchCriteriaDTO.getModeOfTransports()).contains("All")) {
            /*String delim = searchCriteriaDTO.getModeOfTransports().stream().
                    map(Object::toString).
                    collect(Collectors.joining(",")).toString();*/
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
        graph.setV(uniqueRoute.size());
        List<List<Integer>> possiblePathList = graph.getAllPaths(1, 4);

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
