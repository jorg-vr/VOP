package dao.database.util.unique;

import org.hibernate.Session;

/**
 * Created by sam on 4/18/17.
 */
public interface EntityManagerAwareValidator {
    void setSession(Session session);
}
