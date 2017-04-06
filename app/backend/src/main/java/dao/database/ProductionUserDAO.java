package dao.database;

import dao.interfaces.UserDAO;
import model.account.User;
import org.hibernate.Session;

/**
 * Created by Billie Devolder on 5/04/2017.
 */
public class ProductionUserDAO extends ProductionDAO<User> implements UserDAO {

    public ProductionUserDAO(Session session) {
        super(session, User.class);
    }
}
