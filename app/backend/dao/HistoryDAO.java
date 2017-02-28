package dao;

import java.util.Collection;

/**
 * Created by sam on 2/26/17.
 */
public interface HistoryDAO<T> {
    public Collection<T> listFiltered(Filter... filters);

}
