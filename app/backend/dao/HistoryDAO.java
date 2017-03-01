package dao;

import java.util.Collection;

public interface HistoryDAO<T> {

    Collection<T> listFiltered(Filter... filters);
}
