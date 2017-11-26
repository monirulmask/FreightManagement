package com.tendereasy.freightmanagement.dao;

import com.tendereasy.freightmanagement.dto.LocationInfoDTO;
import com.tendereasy.freightmanagement.dto.RouteDetailsDTO;
import com.tendereasy.freightmanagement.dto.SearchCriteriaDTO;
import com.tendereasy.freightmanagement.dto.SearchResponseDTO;

import java.util.List;

/**
 * Created by monir on 10/5/2017.
 */
public interface IFreightCostCalculationDao {

    public List<RouteDetailsDTO> getAllRouteListByInitialCriteria(SearchCriteriaDTO searchCriteriaDTO, String modeOfTransportQuery);

    public Integer getLocationID(String locationName);

    public List<LocationInfoDTO> getLocationInfoDTOList(String sourceName);

    public List<SearchResponseDTO> getAllRouteListBySP(SearchCriteriaDTO searchCriteriaDTO);
}
