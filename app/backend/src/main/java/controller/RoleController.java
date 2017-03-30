package controller;

import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.account.Function;
import model.account.Resource;
import model.account.Role;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 3/30/17.
 */
public class RoleController extends AbstractController<Role> {

    public RoleController(Function function) {
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
        }, Resource.ROLE,function);
    }

    @Override
    public boolean isOwner(Role role, Function function) {
        return false;// Role never has an owner
    }
}
