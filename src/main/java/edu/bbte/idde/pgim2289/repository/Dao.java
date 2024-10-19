package edu.bbte.idde.pgim2289.repository;

import edu.bbte.idde.pgim2289.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    Collection<T> findAll();
    void create(T entity);
    void delete(Long ID);
    void update(T entity);
}
