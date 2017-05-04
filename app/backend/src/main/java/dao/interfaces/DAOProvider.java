package dao.interfaces;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by sam on 4/30/17.
 */
public interface DAOProvider extends AutoCloseable {

    DAOManager getDaoManager();

    //TODO use injection
    @Deprecated
    SessionFactory getSessionFactory();

}
