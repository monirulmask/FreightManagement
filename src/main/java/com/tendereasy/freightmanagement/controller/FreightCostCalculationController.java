package com.tendereasy.freightmanagement.controller;

import com.tendereasy.freightmanagement.dto.*;
import com.tendereasy.freightmanagement.model.EmployeeEntity;
import com.tendereasy.freightmanagement.service.IFreightCostCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by monir on 10/5/2017.
 */
@RestController
@RequestMapping("/freightCostCalculation")
public class FreightCostCalculationController {
    @Autowired
    private IFreightCostCalculationService freightCostCalculationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<EmployeeEntity> index(Model model) {
        return null;
    }

    @RequestMapping(value = "/getSingleEmployee", method = RequestMethod.GET)
    public EmployeeDTO setupForm(Model model) {
        EmployeeDTO employee = freightCostCalculationService.getEmployee();
        return employee;
    }

    @RequestMapping(value = "/scenarioOne", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> scenarioOne(@Valid @RequestBody SearchCriteriaDTO searchCriteriaDTO , Errors errors) {
        CustomerResponseMessageDTO response = new CustomerResponseMessageDTO();

        if (errors.hasErrors()) {
            // get all errors
            response.setMessage(errors.getAllErrors()
                    .stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(response);
        }
        List<AllPossibleRouteDetailsDTO> results = freightCostCalculationService.searchRouteForScenarioOne(searchCriteriaDTO);
        if (results.isEmpty()) {
            response.setMessage("no route found!");
        } else {
            response.setMessage("success");
        }
        response.setResults(results);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/scenarioTwo", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> scenarioTwo(@Valid @RequestBody SearchCriteriaDTO searchCriteriaDTO , Errors errors) throws Exception {
        CustomerResponseMessageDTO response = new CustomerResponseMessageDTO();

        if (errors.hasErrors()) {
            // get all errors
            response.setMessage(errors.getAllErrors()
                    .stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(response);
        }
        List<AllPossibleRouteDetailsDTO> results = freightCostCalculationService.searchRouteForScenarioTwo(searchCriteriaDTO);
        if (results.isEmpty()) {
            response.setMessage("no route found!");
        } else {
            response.setMessage("success");
        }
        response.setResults(results);
        return ResponseEntity.ok(response);
    }

}
