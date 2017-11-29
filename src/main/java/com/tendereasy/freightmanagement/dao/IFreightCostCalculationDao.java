package com.tendereasy.freightmanagement.dao;

import com.tendereasy.freightmanagement.dto.SearchCriteriaDTO;
import com.tendereasy.freightmanagement.dto.SearchResponseDTO;

import java.util.List;

/**
 * Created by monir on 10/5/2017.
 */
public interface IFreightCostCalculationDao {

    public List<SearchResponseDTO> getAllRouteList(SearchCriteriaDTO searchCriteriaDTO, String modeOfTransportQuery);

    public List<SearchResponseDTO> getAllNearestRouteList(SearchCriteriaDTO searchCriteriaDTO, double lat, double lng , String modeOfTransportQuery );
}
