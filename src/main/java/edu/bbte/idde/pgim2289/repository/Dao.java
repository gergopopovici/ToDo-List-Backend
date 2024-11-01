package edu.bbte.idde.pgim2289.repository;

import edu.bbte.idde.pgim2289.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.model.BaseEntity;
import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    Collection<T> findAll();
    void create(T entity) throws InvalidInputException;
    void delete(Long ID) throws EntityNotFoundException;
    void update(T entity) throws EntityNotFoundException;
    T findById(Long id) throws EntityNotFoundException;
}
