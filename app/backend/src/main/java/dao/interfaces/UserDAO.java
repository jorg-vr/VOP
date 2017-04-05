package dao.interfaces;

import model.account.User;

/**
 * Created by Billie Devolder on 5/04/2017.
 */
public interface UserDAO extends DAO<User> {

    /**
     * TODO filteren op
     * firstName
     * lastName
     * email
     *
     * Er was ook een bySecurity(name, password) filter, maar misschien doen we dit best anders?
     */
}
