package com.tendereasy.freightmanagement.service;

import com.tendereasy.freightmanagement.dao.IFreightCostCalculationDao;
import com.tendereasy.freightmanagement.dto.EmployeeDTO;
import com.tendereasy.freightmanagement.model.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
