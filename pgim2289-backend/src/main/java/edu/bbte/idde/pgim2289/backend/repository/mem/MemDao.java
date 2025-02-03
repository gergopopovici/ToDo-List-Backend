package edu.bbte.idde.pgim2289.backend.repository.mem;

import edu.bbte.idde.pgim2289.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.backend.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.backend.model.BaseEntity;
import edu.bbte.idde.pgim2289.backend.repository.Dao;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public abstract class MemDao<T extends BaseEntity> implements Dao<T> {

    protected Map<Long, T> entities = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    @Override
    public void create2(Collection<T> entities2) throws InvalidInputException {
        for (T entity : entities2) {
            if (entity.getId() == null) {
                entity.setId(nextId.getAndIncrement());
            }
            if (entities.containsKey(entity.getId())) { // Fix: check entity.getId() instead of nextId.get() - 1
                throw new InvalidInputException("Entity with ID " + entity.getId() + " is already taken");
            }
            entities.put(entity.getId(), entity); // Fix: insert before logging
            log.info("Inserted entity: {}", entity);
            log.info("Updated entities map: {}", entities);
        }
    }

    @Override
    public void create(T entity) throws InvalidInputException {
        if (entity.getId() == null) {
            entity.setId(nextId.getAndIncrement());
        }
        if (entities.containsKey(entity.getId())) { // Fix: check entity.getId() instead of nextId.get() - 1
            throw new InvalidInputException("Entity with ID " + entity.getId() + " is already taken");
        }
        entities.put(entity.getId(), entity);
        log.info("Inserted entity: {}", entity);
        log.info("Updated entities map: {}", entities);
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        if (!entities.containsKey(id)) {
            throw new EntityNotFoundException("Entity with ID " + id + " does not exist");
        }
        entities.remove(id);
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        if (!entities.containsKey(entity.getId())) {
            throw new EntityNotFoundException("Entity with ID " + entity.getId() + " does not exist");
        }
        entities.put(entity.getId(), entity);
    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        T entity = entities.get(id);
        if (entity == null) {
            throw new EntityNotFoundException("Entity with ID " + id + " does not exist");
        }
        return entity;
    }
}
