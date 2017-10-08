package com.tendereasy.freightmanagement.dao;

import com.tendereasy.freightmanagement.dto.EmployeeDTO;
import com.tendereasy.freightmanagement.dto.RouteDetailsDTO;
import com.tendereasy.freightmanagement.dto.SearchCriteriaDTO;
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

    @Override
    public List<RouteDetailsDTO> getAllRouteListByInitialCriteria(SearchCriteriaDTO searchCriteriaDTO) {
        String query = "SELECT lc.source AS sourceID,s.locationname AS sourceName, lc.destination AS destinationID, d.locationname AS destinationName, lc.modeOfTransports AS modeOfTransports, lc.containerSize AS containerSize, lc.cost AS cost FROM locationvscost lc INNER JOIN location s ON lc.source = s.locationid INNER JOIN location d ON lc.destination = d.locationid WHERE lc.containerSize=:containerSize";
        org.hibernate.Query hQuery = hibernateQuery(query, RouteDetailsDTO.class);
        hQuery.setParameter("containerSize",searchCriteriaDTO.getContainerSize());
        List<RouteDetailsDTO> routeDetailsDTOList = hQuery.list();
        return routeDetailsDTOList;
    }


}
