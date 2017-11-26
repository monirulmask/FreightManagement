package com.tendereasy.freightmanagement.dao;

import com.tendereasy.freightmanagement.dto.LocationInfoDTO;
import com.tendereasy.freightmanagement.dto.RouteDetailsDTO;
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
    public List<RouteDetailsDTO> getAllRouteListByInitialCriteria(SearchCriteriaDTO searchCriteriaDTO, String modeOfTransportQuery) {
        String query = "SELECT lc.source AS sourceID,s.locationname AS sourceName, lc.destination AS destinationID, d.locationname AS destinationName, lc.modeOfTransports AS modeOfTransports, lc.containerSize AS containerSize, lc.cost as  cost , lc.duration AS duration FROM locationvscost lc INNER JOIN location s ON lc.source = s.locationid INNER JOIN location d ON lc.destination = d.locationid WHERE lc.containerSize=:containerSize " + modeOfTransportQuery;
        org.hibernate.Query hQuery = hibernateQuery(query, RouteDetailsDTO.class);
        hQuery.setParameter("containerSize", searchCriteriaDTO.getContainerSize());
        List<RouteDetailsDTO> routeDetailsDTOList = hQuery.list();
        return routeDetailsDTOList;
    }

    @Override
    public Integer getLocationID(String locationName) {
        String query = "SELECT s.locationid AS sourceID FROM location s WHERE s.locationname=:locationName";
        org.hibernate.Query hQuery = hibernateUniqueQuery(query);
        hQuery.setParameter("locationName", locationName);
        Integer locationID = (Integer) hQuery.uniqueResult();
        return locationID;
    }

    @Override
    public List<LocationInfoDTO> getLocationInfoDTOList(String sourceName) {
        String query = "SELECT l.locationid AS locationID,l.locationname AS locationName, l.locationlat AS latitude, l.locationlong AS longitude FROM location l WHERE l.locationname<>:sourceName";
        org.hibernate.Query hQuery = hibernateQuery(query, LocationInfoDTO.class);
        hQuery.setParameter("sourceName", sourceName);
        List<LocationInfoDTO> locationInfoDTOList = hQuery.list();
        return locationInfoDTOList;
    }

    @Override
    public List<SearchResponseDTO> getAllRouteListBySP(SearchCriteriaDTO searchCriteriaDTO) {
        //String query = "CALL usp_get_route_details('Stockholm','Orlando','',20,null,null,null,null)";
        String query = "CALL usp_get_route_details(:source,:destination,'',:containerSize,null,null,null,null)";
        org.hibernate.Query hQuery = hibernateQuery(query, SearchResponseDTO.class);
        hQuery.setParameter("source", searchCriteriaDTO.getSource());
        hQuery.setParameter("destination", searchCriteriaDTO.getDestination());
        hQuery.setParameter("containerSize", searchCriteriaDTO.getContainerSize());
        List<SearchResponseDTO> searchResponseDTOList = hQuery.list();
        return searchResponseDTOList;
    }


}
