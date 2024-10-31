package edu.bbte.idde.pgim2289.repository.jdbc;

import edu.bbte.idde.pgim2289.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.exceptions.IllegalArgument;
import edu.bbte.idde.pgim2289.model.BaseEntity;
import edu.bbte.idde.pgim2289.repository.Dao;

import java.util.Collection;

public abstract class JdbcDao<T extends BaseEntity> implements Dao<T>{
    @Override
    public Collection<T> findAll() {
        return null;
    }

    @Override
    public void delete(Long ID) throws EntityNotFoundException {

    }
    @Override
    public void create(T entity) throws IllegalArgument {

    }

    @Override
    public void update(T entity) throws EntityNotFoundException {

    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        return null;
    }
}
