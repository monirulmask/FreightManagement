package com.tendereasy.freightmanagement.service;

import com.tendereasy.freightmanagement.dao.IFreightCostCalculationDao;
import com.tendereasy.freightmanagement.dto.EmployeeDTO;
import com.tendereasy.freightmanagement.dto.RouteContainer;
import com.tendereasy.freightmanagement.dto.RouteDetailsDTO;
import com.tendereasy.freightmanagement.dto.SearchCriteriaDTO;
import com.tendereasy.freightmanagement.model.EmployeeEntity;
import com.tendereasy.freightmanagement.util.DFSGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<RouteContainer> searchRouteForScenarioOne(SearchCriteriaDTO searchCriteriaDTO) {
        Set<Integer> uniqueRoute = new HashSet<Integer>();
        List<RouteDetailsDTO> routeDetailsDTOList = freightCostCalculationDao.getAllRouteListByInitialCriteria(searchCriteriaDTO);

        //Finding unique route number
        for(RouteDetailsDTO routeDetailsDTO : routeDetailsDTOList){
            uniqueRoute.add(routeDetailsDTO.getSourceID());
            uniqueRoute.add(routeDetailsDTO.getDestinationID());
        }

        DFSGraph graph = new DFSGraph(uniqueRoute.size());

        //Adding all nodes
        for(RouteDetailsDTO routeDetailsDTO : routeDetailsDTOList){
            graph.addEdge(routeDetailsDTO.getSourceID(),routeDetailsDTO.getDestinationID());
        }

        List<List<Integer>> possiblePathList = graph.getAllPaths(1,4);

        return null;
    }


}
