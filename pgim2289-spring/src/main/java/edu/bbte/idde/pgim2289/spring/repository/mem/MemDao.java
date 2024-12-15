package edu.bbte.idde.pgim2289.spring.repository.mem;

import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.BaseEntity;
import edu.bbte.idde.pgim2289.spring.repository.Dao;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class MemDao<T extends BaseEntity> implements Dao<T> {

    protected Map<Long, T> entities = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    @Override
    public void create(T entity) throws InvalidInputException {
        if (entity.getId() == null) {
            entity.setId(nextId.getAndIncrement());
        }
        if (entities.containsKey(nextId.get() - 1)) {
            throw new InvalidInputException("This + " + nextId + "is already taken");
        }
        entities.put(entity.getId(), entity);
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        if (!entities.containsKey(id)) {
            throw new EntityNotFoundException("Entity with the ID of " + id + " is non existent");
        }
        entities.remove(id);
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {

        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        } else {
            throw new EntityNotFoundException("Entity with the ID of " + entity.getId() + " is non existent");
        }
    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        if (entities.containsKey(id)) {
            return entities.get(id);
        } else {
            throw new EntityNotFoundException("Entity with the ID of " + id + " is non existent");
        }
    }
}
