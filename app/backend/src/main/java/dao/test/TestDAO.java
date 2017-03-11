package dao.test;

import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.history.EditableObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Billie Devolder on 11/03/2017.
 */
public abstract class TestDAO<T extends EditableObject> implements DAO<T> {

    private Map<UUID, T> mapping;

    public TestDAO() {
    }

    protected void setMapping(Map<UUID, T> mapping) {
        this.mapping = mapping;
    }

    @Override
    public T get(UUID id) throws DataAccessException {
        if (! mapping.containsKey(id))  {
            throw new DataAccessException();
        }
        return mapping.get(id);
    }

    @Override
    public Collection<T> listFiltered(Filter... filters) throws DataAccessException {
        return new ArrayList(mapping.values());
    }


    @Override
    public T create(T t) throws DataAccessException {
        return null;
    }


    @Override
    public void update(T t) throws DataAccessException {
        if (!mapping.containsKey(t.getUuid())) {
            throw new DataAccessException();
        }
        mapping.put(t.getUuid(), t);
    }

    @Override
    public void remove(T t) throws DataAccessException {
        if (!mapping.containsKey(t.getUuid())) {
            throw new DataAccessException();
        }
        mapping.remove(t.getUuid());
    }

    @Override
    public void close() {

    }
}
