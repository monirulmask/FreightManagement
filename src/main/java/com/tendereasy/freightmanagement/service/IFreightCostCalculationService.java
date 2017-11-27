package com.tendereasy.freightmanagement.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.tendereasy.freightmanagement.dto.AllPossibleRouteDetailsDTO;
import com.tendereasy.freightmanagement.dto.SearchCriteriaDTO;

import java.io.IOException;
import java.util.List;

/**
 * Created by monir on 10/5/2017.
 */
public interface IFreightCostCalculationService {

    public List<AllPossibleRouteDetailsDTO> searchRouteForScenarioOne(SearchCriteriaDTO searchCriteriaDTO);

    public List<AllPossibleRouteDetailsDTO> searchRouteForScenarioTwo(SearchCriteriaDTO searchCriteriaDTO) throws Exception;

    public List<AllPossibleRouteDetailsDTO> searchRouteForScenarioThree(SearchCriteriaDTO searchCriteriaDTO) throws IOException;
}
