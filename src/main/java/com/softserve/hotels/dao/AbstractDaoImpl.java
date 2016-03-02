package com.softserve.hotels.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public abstract class AbstractDaoImpl<T> implements AbstractDao<T> {

    @PersistenceContext
    private EntityManager entityManager;
    private Class<?> entityClass;

    public AbstractDaoImpl() {
        entityClass = getEntityClass();
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("From " + entityClass.getSimpleName() + " order by id").getResultList();
    }

    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public T findById(Object id) {
        return entityManager.find(getEntityClass(), id);
    }

    @Override
    public void deleteById(Object id) {
        T entity = entityManager.find(getEntityClass(), id);
        entityManager.remove(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM " + entityClass.getSimpleName()).executeUpdate();
    }

    @Override
    public List<T> findByRange(CriteriaQuery<T> query, int startPage, int pageSize) {

        TypedQuery<T> typedQuery = getEntityManager().createQuery(query);
        typedQuery.setFirstResult(startPage);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }

    @Override
    public long getEntityCount(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> query,  Root<?> entitRoot) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<?> entityRoot = countQuery.from(entitRoot.getJavaType());
        entitRoot.alias(entitRoot.getAlias());
        doJoins(entitRoot.getJoins(), entityRoot);
        countQuery.select(criteriaBuilder.count(entityRoot));
        if (query.getRestriction() != null) {
            countQuery.where(query.getRestriction());
        }
        return entityManager.createQuery(countQuery).getSingleResult();
    }
    
    @Override
    public Long getEntityPagesCount(Long entityCount, Integer pageSize) {
        return (long) Math.ceil(((double) entityCount) / pageSize);
    }
    

    private static void doJoins(Set<? extends Join<?, ?>> joins, Root<?> root) {
        for (Join<?, ?> join: joins) {
            Join<?, ?> joined = root.join(join.getAttribute().getName(), join.getJoinType());
            doJoins(join.getJoins(), joined);
        }
    }

    private static void doJoins(Set<? extends Join<?, ?>> joins, Join<?, ?> root) {
        for (Join<?, ?> join: joins) {
            Join<?, ?> joined = root.join(join.getAttribute().getName(), join.getJoinType());
            doJoins(join.getJoins(), joined);
        }
    }

    @SuppressWarnings("unchecked")
    protected final Class<T> getEntityClass() {
        final Type type = getClass().getGenericSuperclass() instanceof ParameterizedType
                ? getClass().getGenericSuperclass() : getClass().getSuperclass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            final ParameterizedType paramType = (ParameterizedType) type;
            return (Class<T>) paramType.getActualTypeArguments()[0];
        } else
            throw new IllegalArgumentException("Could not guess entity class by reflection");
    }
    
    /*
     * http://answers.candoerz.com/question/67408/in-jpa-2-using-a-criteriaquery-how-to-count-resu.aspx
     * */

}