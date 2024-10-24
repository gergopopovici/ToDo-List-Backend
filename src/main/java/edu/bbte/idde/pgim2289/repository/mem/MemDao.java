package edu.bbte.idde.pgim2289.repository.mem;
import edu.bbte.idde.pgim2289.exceptions.EntityNotFoundException;
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
    public void delete(Long ID) throws EntityNotFoundException{
        if (!entities.containsKey(ID)) {
            throw new EntityNotFoundException("Entity with the ID of " + ID +" is non existent");
        }
        entities.remove(ID);
    }
    @Override
    public void update(T entity) throws EntityNotFoundException{

        if(entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        }else{
            throw new EntityNotFoundException("Entity with the ID of " + entity.getId() +" is non existent");
        }
    }
}
