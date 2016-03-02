/**
 * @author Rogulya Volodymyr
 */
package com.softserve.hotels.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.dao.AbstractDao;

@Transactional
public abstract class AbstractServiceImpl<T> implements AbstractService<T> {

    @Autowired
    private AbstractDao<T> abstractDao;

    @Override
    public List<T> findAll() {
        return this.abstractDao.findAll();
    }

    @Override
    public void create(T entity) {
        this.abstractDao.create(entity);
    }

    @Override
    public T findById(Object id) {
        return this.abstractDao.findById(id);
    }

    @Override
    public void deleteById(Object id) {
        this.abstractDao.deleteById(id);
    }

    @Override
    public void delete(T entity) {
        this.abstractDao.delete(entity);
    }

    @Override
    public void deleteAll() {
        this.abstractDao.deleteAll();
    }

    @Override
    public T update(T entity) {
        return this.abstractDao.update(entity);
    }
}
