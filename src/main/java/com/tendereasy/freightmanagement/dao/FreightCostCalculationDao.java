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

    /***
     * This method interacts with DB and call stored procedure and returns all information for first scenario
     * @param searchCriteriaDTO
     * @param modeOfTransportQuery
     * @return
     */
    @Override
    public List<SearchResponseDTO> getAllRouteList(SearchCriteriaDTO searchCriteriaDTO, String modeOfTransportQuery ) {
        String query = "CALL usp_get_route_details(:source,:destination,:modeOfTransports,:containerSize,:durationFrom,:durationTo,:costFrom,:costTo)";
        org.hibernate.Query hQuery = hibernateQuery(query, SearchResponseDTO.class);
        hQuery.setParameter("source", searchCriteriaDTO.getSource());
        hQuery.setParameter("destination", searchCriteriaDTO.getDestination());
        hQuery.setParameter("modeOfTransports", modeOfTransportQuery);
        hQuery.setParameter("containerSize", searchCriteriaDTO.getContainerSize());
        hQuery.setParameter("durationFrom", searchCriteriaDTO.getDurationFrom());
        hQuery.setParameter("durationTo", searchCriteriaDTO.getDurationTo());
        hQuery.setParameter("costFrom", searchCriteriaDTO.getCostFrom());
        hQuery.setParameter("costTo", searchCriteriaDTO.getCostTo());
        List<SearchResponseDTO> searchResponseDTOList = hQuery.list();
        return searchResponseDTOList;
    }

    /***
     * This method interacts with DB and call stored procedure and returns all information for 2nd scenario
     * @param searchCriteriaDTO
     * @param lat
     * @param lng
     * @param modeOfTransportQuery
     * @return
     */
    @Override
    public List<SearchResponseDTO> getAllNearestRouteList(SearchCriteriaDTO searchCriteriaDTO, double lat, double lng , String modeOfTransportQuery ) {
        String query = "CALL usp_get_near_route_details(:source,:destination,:modeOfTransports,:containerSize, :lat,:lng, :destination)";
        org.hibernate.Query hQuery = hibernateQuery(query, SearchResponseDTO.class);
        hQuery.setParameter("source", searchCriteriaDTO.getSource());
        hQuery.setParameter("destination", searchCriteriaDTO.getDestination());
        hQuery.setParameter("modeOfTransports", modeOfTransportQuery);
        hQuery.setParameter("containerSize", searchCriteriaDTO.getContainerSize());
        hQuery.setParameter("lat", lat);
        hQuery.setParameter("lng", lng);
        List<SearchResponseDTO> searchResponseDTOList = hQuery.list();
        return searchResponseDTOList;
    }


}
