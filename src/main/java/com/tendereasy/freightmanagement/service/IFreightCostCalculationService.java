package com.tendereasy.freightmanagement.service;

import com.tendereasy.freightmanagement.dto.EmployeeDTO;
import com.tendereasy.freightmanagement.dto.RouteContainer;
import com.tendereasy.freightmanagement.dto.SearchCriteriaDTO;
import com.tendereasy.freightmanagement.model.EmployeeEntity;

import java.util.List;

/**
 * Created by monir on 10/5/2017.
 */
public interface IFreightCostCalculationService {
    public List<EmployeeEntity> getAllEmployees();
    public EmployeeDTO getEmployee();

    public List<RouteContainer> searchRouteForScenarioOne(SearchCriteriaDTO searchCriteriaDTO);
}
