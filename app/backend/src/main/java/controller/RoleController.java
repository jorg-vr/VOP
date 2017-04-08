package controller;

import dao.database.ProductionProvider;
import dao.database.ProductionRoleDAO;
import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import main.BackendApplication;
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
        super(BackendApplication.getProvider().getRoleDAO(), Resource.ROLE,function);
    }

    @Override
    public boolean isOwner(Role role, Function function) {
        return false;// Role never has an owner
    }
}
