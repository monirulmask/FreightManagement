package com.tendereasy.freightmanagement.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

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
}
