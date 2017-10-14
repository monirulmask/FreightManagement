package com.tendereasy.freightmanagement.service;

import com.tendereasy.freightmanagement.dto.AllPossibleRouteDetailsDTO;
import com.tendereasy.freightmanagement.dto.EmployeeDTO;
import com.tendereasy.freightmanagement.dto.SearchCriteriaDTO;
import com.tendereasy.freightmanagement.model.EmployeeEntity;

import java.util.List;

/**
 * Created by monir on 10/5/2017.
 */
public interface IFreightCostCalculationService {

    public EmployeeDTO getEmployee();

    public List<AllPossibleRouteDetailsDTO> searchRouteForScenarioOne(SearchCriteriaDTO searchCriteriaDTO);

    public List<AllPossibleRouteDetailsDTO> searchRouteForScenarioTwo(SearchCriteriaDTO searchCriteriaDTO) throws Exception;
}
