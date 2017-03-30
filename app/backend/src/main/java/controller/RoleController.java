package controller;

import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.account.Role;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 3/30/17.
 */
public class RoleController extends AbstractController<Role> {

    public RoleController() {
        //TODO !!
        super(new DAO<Role>() {
            @Override
            public Role get(UUID id) throws DataAccessException {
                return null;
            }

            @Override
            public void remove(UUID id) throws DataAccessException {

            }

            @Override
            public Collection<Role> listFiltered(Filter<Role>... filters) throws DataAccessException {
                return null;
            }

            @Override
            public Role create(Role role) {
                return null;
            }

            @Override
            public Role update(Role role) {
                return null;
            }
        });
    }
}
