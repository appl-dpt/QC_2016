/**
 * @author Rogulya Volodymyr
 */
package com.softserve.hotels.service;

import java.util.List;

public interface AbstractService<T> {

    List<T> findAll();

    void create(T entity);

    T findById(Object id);

    void deleteById(Object id);

    void delete(T entity);

    void deleteAll();

    T update(T entity);

}
