package controller;

import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.FunctionDAO;
import main.BackendApplication;
import model.account.Account;
import model.account.Function;
import model.account.Role;
import model.identity.Company;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Billie Devolder on 11/03/2017.
 */
public class FunctionController extends AbstractController<Function> {

    private DAOProvider provider;
    private FunctionDAO functionDAO;

    public FunctionController() {
        super(BackendApplication.PROVIDER.getFunctionDAO());
        provider = BackendApplication.PROVIDER;
        functionDAO = provider.getFunctionDAO();
    }

    public Function create(UUID companyId, String roleString, UUID accountId, LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException {
        Company company = provider.getCompanyDAO().get(companyId);
        Role role = new Role(roleString);
        Account account = provider.getAccountDao().get(accountId);
        return functionDAO.create(company, role, account, startDate, endDate);
    }

    public Function update(UUID functionId, UUID companyId, String roleString, UUID accountId, LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException {
        Company company = provider.getCompanyDAO().get(companyId);
        Role role = new Role(roleString);
        Account account = provider.getAccountDao().get(accountId);
        return functionDAO.update(functionId, company, role, account, startDate, endDate);
    }
}
