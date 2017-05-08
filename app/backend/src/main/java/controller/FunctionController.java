package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.interfaces.*;
import model.account.*;
import model.identity.Company;
import spring.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * For more information of what this class does, see AbstractController
 */
public class FunctionController extends AbstractController<Function> {

    private DAOManager manager;
    private FunctionDAO functionDAO;

    public FunctionController(Function function, DAOManager manager) {
        super(manager.getFunctionDAO(), Resource.FUNCTION,function);
        this.manager = manager;
        functionDAO = this.manager.getFunctionDAO();
    }



    public Collection<Function> getFiltered(Company company, User user, Boolean active) throws UnAuthorizedException {
        try {
            List<Filter<Function>> filters = new ArrayList<>();


            if (company != null) {
                filters.add(functionDAO.byCompany(company));
            }

            if (active != null) {
                // ignore for now
                throw new NotImplementedException();
            }


            return getAll(filters.toArray(new Filter[filters.size()]));
        } catch (DataAccessException e) {
            // return empty list
            return new ArrayList<>();
        }
    }

    @Override
    public boolean isOwner(Function function, Function function2) {
        return function.equals(function2);
    }
}
