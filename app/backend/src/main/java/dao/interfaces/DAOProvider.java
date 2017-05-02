package dao.interfaces;

/**
 * Created by sam on 4/30/17.
 */
public interface DAOProvider extends AutoCloseable {

    DAOManager getDaoManager();

}
