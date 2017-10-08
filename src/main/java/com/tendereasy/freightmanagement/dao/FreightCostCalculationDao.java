package com.tendereasy.freightmanagement.dao;

import com.tendereasy.freightmanagement.dto.EmployeeDTO;
import com.tendereasy.freightmanagement.model.EmployeeEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by monir on 10/5/2017.
 */
@Repository
@Transactional
public class FreightCostCalculationDao extends BaseDao implements IFreightCostCalculationDao {

    public List<EmployeeEntity> getAllEmployees() {
        List<EmployeeEntity> employees = em.createQuery("Select a From EmployeeEntity a", EmployeeEntity.class).getResultList();
        return employees;
    }

    public EmployeeDTO getEmployeesDTO() {
        String query = "SELECT A.id AS id, A.firstName AS firstName, A.lastName AS lastName, A.email AS email FROM employee A WHERE id=:id";
        //List<EmployeeDTO> employeeList = query.setParameter("id", 1).getResultList();

        org.hibernate.Query hQuery = hibernateQuery(query, EmployeeDTO.class);
        hQuery.setParameter("id",1);
        List<EmployeeDTO> employeeList = hQuery.list();
        return employeeList != null ? employeeList.get(0): null;
    }


}
