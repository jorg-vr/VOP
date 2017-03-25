package controller;

import dao.interfaces.*;
import main.BackendApplication;
import model.account.Account;
import model.account.Function;
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

    public FunctionController() {
        super(BackendApplication.getProvider().getFunctionDAO());
        provider = BackendApplication.getProvider();
        functionDAO = provider.getFunctionDAO();
    }

    public Function create(UUID companyId, String roleString, UUID accountId, LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException {
        Company company = provider.getCustomerDAO().get(companyId);

        Account account = provider.getAccountDao().get(accountId);

        Role role = new Role(roleString);
        return functionDAO.create(company, role, account, startDate, endDate);
    }

    public Function update(UUID functionId, UUID companyId, String roleString, UUID accountId, LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException {
        Company company = provider.getCustomerDAO().get(companyId);
        Role role = new Role(roleString);
        Account account = provider.getAccountDao().get(accountId);
        return functionDAO.update(functionId, company, role, account, startDate, endDate);
    }

    public Collection<Function> getFiltered(Company company, Account account, Boolean active) {
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
}
