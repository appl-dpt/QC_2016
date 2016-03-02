package com.softserve.hotels.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public interface AbstractDao<T> {

    List<T> findAll();

    void create(T entity);

    T findById(Object id);

    void deleteById(Object id);

    void delete(T entity);

    void deleteAll();

    T update(T entity);

    List<T> findByRange(CriteriaQuery<T> query, int startPage, int pageSize);

    long getEntityCount(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> query, Root<?> entityRoot);
    
    Long getEntityPagesCount(Long entityCount, Integer pageSize);
    
}
