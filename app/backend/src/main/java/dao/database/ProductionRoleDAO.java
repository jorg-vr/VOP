package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.RoleDAO;
import model.account.Role;
import org.hibernate.Session;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by sam on 4/4/17.
 */
public class ProductionRoleDAO extends ProductionDAO<Role> implements RoleDAO  {

    public ProductionRoleDAO(Session session) {
        super(session, Role.class);
    }
}
