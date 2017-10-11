package com.tendereasy.freightmanagement.dao;


import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import javax.persistence.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by monirul.islam on 10/8/2017.
 */
public abstract class BaseDao {
    @PersistenceContext
    protected EntityManager em;

    protected Session getCurrentSession(){
        return em.unwrap(Session.class);
    }

    protected org.hibernate.Query hibernateQuery(String query, Class dtoClazz){
        return getCurrentSession().createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(dtoClazz));
    }

    protected Query persistenceQuery(String query){
        return em.createNativeQuery(query);
    }

    protected Query persistenceQuery(String query, Class entityClazz){
        return em.createNativeQuery(query, entityClazz);
    }
}
