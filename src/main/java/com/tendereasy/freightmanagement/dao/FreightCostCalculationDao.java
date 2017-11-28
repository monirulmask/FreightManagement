package com.tendereasy.freightmanagement.dao;

import com.tendereasy.freightmanagement.dto.SearchCriteriaDTO;
import com.tendereasy.freightmanagement.dto.SearchResponseDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by monir on 10/5/2017.
 */
@Repository
@Transactional
public class FreightCostCalculationDao extends BaseDao implements IFreightCostCalculationDao {

    @Override
    public List<SearchResponseDTO> getAllRouteList(SearchCriteriaDTO searchCriteriaDTO) {
        //String query = "CALL usp_get_route_details('Stockholm','Orlando','',20,null,null,null,null)";
        String query = "CALL usp_get_route_details(:source,:destination,'',:containerSize,null,null,null,null)";
        org.hibernate.Query hQuery = hibernateQuery(query, SearchResponseDTO.class);
        hQuery.setParameter("source", searchCriteriaDTO.getSource());
        hQuery.setParameter("destination", searchCriteriaDTO.getDestination());
        hQuery.setParameter("containerSize", searchCriteriaDTO.getContainerSize());
        List<SearchResponseDTO> searchResponseDTOList = hQuery.list();
        return searchResponseDTOList;
    }

    @Override
    public List<SearchResponseDTO> getAllNearestRouteList(SearchCriteriaDTO searchCriteriaDTO, double lat, double lng) {
        String query = "CALL usp_get_near_route_details(:source,:destination,'',:containerSize,null,null,null,null, :lat,:lng, :destination)";
        org.hibernate.Query hQuery = hibernateQuery(query, SearchResponseDTO.class);
        hQuery.setParameter("source", searchCriteriaDTO.getSource());
        hQuery.setParameter("destination", searchCriteriaDTO.getDestination());
        hQuery.setParameter("containerSize", searchCriteriaDTO.getContainerSize());
        hQuery.setParameter("lat", lat);
        hQuery.setParameter("lng", lng);
        List<SearchResponseDTO> searchResponseDTOList = hQuery.list();
        return searchResponseDTOList;
    }


}
