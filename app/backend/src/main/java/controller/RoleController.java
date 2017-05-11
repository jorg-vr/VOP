package controller;

import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Resource;
import model.account.Role;

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
}
