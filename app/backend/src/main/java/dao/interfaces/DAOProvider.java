package dao.interfaces;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by sam on 4/30/17.
 */
public interface DAOProvider extends AutoCloseable {

    /**
     * Returns a new DAOManager, one manager has one transaction,
     * so everything done within DAO's got by the manager will be committed or nothing will
     * @return a new DAOManager
     */
    DAOManager getDaoManager();

}
