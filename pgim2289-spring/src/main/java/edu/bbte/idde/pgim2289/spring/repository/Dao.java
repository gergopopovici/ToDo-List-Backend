package edu.bbte.idde.pgim2289.spring.repository;

import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    Collection<T> findAll();

    void create(T entity) throws InvalidInputException;

    void delete(Long id) throws EntityNotFoundException;

    void update(T entity) throws EntityNotFoundException;

    T findById(Long id) throws EntityNotFoundException;
}
