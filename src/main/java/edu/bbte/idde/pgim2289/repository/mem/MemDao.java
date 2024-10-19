package edu.bbte.idde.pgim2289.repository.mem;

import edu.bbte.idde.pgim2289.model.BaseEntity;
import edu.bbte.idde.pgim2289.repository.Dao;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MemDao<T extends BaseEntity> implements Dao<T> {

    protected Map<Long, T> entities = new ConcurrentHashMap<>();

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    @Override
    public void create(T entity) {
        entities.put(entity.getId(), entity);
    }
    @Override
    public void delete(T entity){
        entities.remove(entity.getId());
    }
    @Override
    public void update(T entity){
        if(entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        }
    }
}
