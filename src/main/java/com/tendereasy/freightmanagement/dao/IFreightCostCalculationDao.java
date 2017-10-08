package com.tendereasy.freightmanagement.dao;

import com.tendereasy.freightmanagement.dto.EmployeeDTO;
import com.tendereasy.freightmanagement.dto.RouteDetailsDTO;
import com.tendereasy.freightmanagement.dto.SearchCriteriaDTO;
import com.tendereasy.freightmanagement.model.EmployeeEntity;

import java.util.List;

/**
 * Created by monir on 10/5/2017.
 */
public interface IFreightCostCalculationDao {
    public List<EmployeeEntity> getAllEmployees();
    public EmployeeDTO getEmployeesDTO();

    public List<RouteDetailsDTO> getAllRouteListByInitialCriteria(SearchCriteriaDTO searchCriteriaDTO);
}
