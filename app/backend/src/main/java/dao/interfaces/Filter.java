package dao.interfaces;

/**
 * Functional interface for filtering in DAO's, when fieldname is null in filter, filter should be ignored
 * Created by sam on 3/7/17.
 */
public interface Filter<T> {

    /**
     * Filter to use (created by lambda), used like Runnable
     */
    public void filter();
}
