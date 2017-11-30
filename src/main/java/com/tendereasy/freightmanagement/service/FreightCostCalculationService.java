package com.tendereasy.freightmanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.tendereasy.freightmanagement.dao.IFreightCostCalculationDao;
import com.tendereasy.freightmanagement.dto.AllPossibleRouteDetailsDTO;
import com.tendereasy.freightmanagement.dto.ResponseRouteDetailsDTO;
import com.tendereasy.freightmanagement.dto.SearchCriteriaDTO;
import com.tendereasy.freightmanagement.dto.SearchResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by monir on 10/5/2017.
 */
@Service
public class FreightCostCalculationService implements IFreightCostCalculationService {

    private static final String API_KEY = "AIzaSyBrVPkbE0U46p8i40X7QVL1mdspKu1pqdI";
    private static final GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
    @Autowired
    private IFreightCostCalculationDao freightCostCalculationDao;

    /***
     * Return all possible route between source and destination where both source and destination are available in system
     * @param searchCriteriaDTO
     * @return
     * @throws IOException
     */
    @Override
    public List<AllPossibleRouteDetailsDTO> searchRouteForScenarioOne(SearchCriteriaDTO searchCriteriaDTO) throws IOException {
        List<AllPossibleRouteDetailsDTO> routeList = new ArrayList<>();
        String modeOfTransportQuery = "";
        Boolean isModeAll = searchCriteriaDTO.getModeOfTransports().contains("All");
        if (!isModeAll) {
            modeOfTransportQuery = searchCriteriaDTO.getModeOfTransports().stream()
                    .map((s) -> "^" + s + "$")
                    .collect(Collectors.joining("|"));
        }

        List<SearchResponseDTO> searchResponseDTOList = freightCostCalculationDao.getAllRouteList(searchCriteriaDTO, modeOfTransportQuery);
        if (searchResponseDTOList != null && !searchResponseDTOList.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            for (SearchResponseDTO searchResponseDTO : searchResponseDTOList) {
                AllPossibleRouteDetailsDTO allPossibleRouteDetailsDTO = new AllPossibleRouteDetailsDTO();
                String jsonRoute = "[" + searchResponseDTO.getFullRoute() + "]";
                List<ResponseRouteDetailsDTO> routeDetailsList = objectMapper.readValue(jsonRoute, new TypeReference<List<ResponseRouteDetailsDTO>>() {
                });
                allPossibleRouteDetailsDTO.setRoute(routeDetailsList);
                allPossibleRouteDetailsDTO.setTotalDuration(searchResponseDTO.getDuration());
                allPossibleRouteDetailsDTO.setTotalCost(searchResponseDTO.getCost());
                routeList.add(allPossibleRouteDetailsDTO);
            }
        }
        return routeList;
    }

    /***
     * Return all possible route between source and destination where source is available in system but destination is not available. So system will find all location near destination in 50KM radius and all possible path between them.
     * @param searchCriteriaDTO
     * @return
     * @throws Exception
     */
    @Override
    public List<AllPossibleRouteDetailsDTO> searchRouteForScenarioTwo(SearchCriteriaDTO searchCriteriaDTO) throws Exception {
        List<AllPossibleRouteDetailsDTO> routeList = new ArrayList<>();
        String modeOfTransportQuery = "";
        Boolean isModeAll = searchCriteriaDTO.getModeOfTransports().contains("All");
        if (!isModeAll) {
            modeOfTransportQuery = searchCriteriaDTO.getModeOfTransports().stream()
                    .map((s) -> "^" + s + "$")
                    .collect(Collectors.joining("|"));
        }

        GeocodingResult geocodingResult = getLatLongByCity(searchCriteriaDTO.getDestination());
        if (geocodingResult != null) {
            List<SearchResponseDTO> searchResponseDTOList = freightCostCalculationDao.getAllNearestRouteList(searchCriteriaDTO, geocodingResult.geometry.location.lat, geocodingResult.geometry.location.lng, modeOfTransportQuery);
            if (searchResponseDTOList != null && !searchResponseDTOList.isEmpty()) {
                ObjectMapper objectMapper = new ObjectMapper();
                for (SearchResponseDTO searchResponseDTO : searchResponseDTOList) {
                    AllPossibleRouteDetailsDTO allPossibleRouteDetailsDTO = new AllPossibleRouteDetailsDTO();
                    String jsonRoute = "[" + searchResponseDTO.getFullRoute() + "]";
                    List<ResponseRouteDetailsDTO> routeDetailsList = objectMapper.readValue(jsonRoute, new TypeReference<List<ResponseRouteDetailsDTO>>() {
                    });
                    allPossibleRouteDetailsDTO.setRoute(routeDetailsList);
                    allPossibleRouteDetailsDTO.setTotalDuration(searchResponseDTO.getDuration());
                    allPossibleRouteDetailsDTO.setTotalCost(searchResponseDTO.getCost());
                    routeList.add(allPossibleRouteDetailsDTO);
                }
            }
        }
        return routeList;
    }

    /**
     * Return Return Lat and Log By City Name(Uses Google GeoCode API)
     *
     * @param cityName
     * @return
     * @throws Exception
     */
    private GeocodingResult getLatLongByCity(String cityName) throws Exception {
        GeocodingResult[] results = new GeocodingResult[0];
        results = GeocodingApi.geocode(context,
                cityName).await();
        return results.length > 0 ? results[0] : null;
    }

}
