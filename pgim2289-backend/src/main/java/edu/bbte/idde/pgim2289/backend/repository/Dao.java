package edu.bbte.idde.pgim2289.backend.repository;

import edu.bbte.idde.pgim2289.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.backend.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.backend.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    Collection<T> findAll();

    void create(T entity) throws InvalidInputException;

    void delete(Long id) throws EntityNotFoundException;

    void update(T entity) throws EntityNotFoundException;

    T findById(Long id) throws EntityNotFoundException;
}
