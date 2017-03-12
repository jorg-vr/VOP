package dao.test;

import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.history.EditableObject;
import spring.Exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * This class contains a generic implementation of the get(id), remove(t) and listFiltered(Filter... filters)
 * The create has not been implemented because it is deprecated. updated has been implemented but don't use it!
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
        throw new NotImplementedException();
    }


    /**
     * DEPRECATED DO NOT USE!
     */
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
