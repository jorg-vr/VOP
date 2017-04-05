package controller;

import controller.exceptions.UnAuthorizedException;
import dao.interfaces.*;
import main.BackendApplication;
import model.account.Account;
import model.account.Function;
import model.account.Resource;
import model.account.Role;
import model.identity.Company;
import spring.exceptions.NotImplementedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * For more information of what this class does, see AbstractController
 */
public class FunctionController extends AbstractController<Function> {

    private DAOProvider provider;
    private FunctionDAO functionDAO;
    //todo function should not be authorized by function
    public FunctionController(Function function) {
        super(BackendApplication.getProvider().getFunctionDAO(), Resource.FUNCTION,function);
        provider = BackendApplication.getProvider();
        functionDAO = provider.getFunctionDAO();
    }



    public Collection<Function> getFiltered(Company company, Account account, Boolean active) throws UnAuthorizedException {
        try {
            List<Filter<Function>> filters = new ArrayList<>();

            if (account != null) {
                filters.add(functionDAO.byAccount(account));
            }

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
