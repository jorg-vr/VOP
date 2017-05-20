package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.FunctionDAO;
import model.account.Function;
import model.account.Resource;
import model.account.Role;
import model.account.User;
import model.identity.Company;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * For more information of what this class does, see AbstractController
 */
public class FunctionController extends AbstractController<Function> {

    private DAOManager manager;
    private FunctionDAO dao;

    public FunctionController(Function function, DAOManager manager) {
        super(manager, manager.getFunctionDAO(), Resource.FUNCTION, function);
        dao = manager.getFunctionDAO();
    }

    @Override
    public boolean isOwner(Function function, Function function2) {
        return function.equals(function2);
    }

    public Collection<Function> getFiltered(User user, Company company, Role role) throws DataAccessException, UnAuthorizedException {
        Collection<Function> result = getAll(
                dao.byCompany(company),
                dao.byUser(user));

        // Filter companies on criteria that are not supported by the database
        return result.stream()
                .filter(c -> role == null || c.getRole().equals(role))
                .collect(Collectors.toList());
    }
}
