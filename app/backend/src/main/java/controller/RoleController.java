package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Resource;
import model.account.Role;

import java.util.Collection;
import java.util.stream.Collectors;

import static util.Compare.containsIgnoreCase;

/**
 * Created by jorg on 3/30/17.
 */
public class RoleController extends AbstractController<Role> {

    public RoleController(Function function, DAOManager manager) {
        super(manager, manager.getRoleDAO(), Resource.ROLE,function);
    }

    @Override
    public boolean isOwner(Role role, Function function) {
        return function.getRole().equals(role);
    }

    /**
     *
     * @param name only return roles of which the name contains the pattern name
     * @return all roles, filtered on the arguments
     * @throws DataAccessException Something went horribly wrong with the database
     * @throws UnAuthorizedException Function is not authorized to get all the objects.
     */
    public Collection<Role> getFiltered(String name) throws DataAccessException, UnAuthorizedException {

        Collection<Role> result = getAll();

        // Filter companies on criteria that are not supported by the database
        return result.stream()
                .filter(c -> name == null || (c.getName() != null && containsIgnoreCase(c.getName(), name)))
                .collect(Collectors.toList());
    }
}
