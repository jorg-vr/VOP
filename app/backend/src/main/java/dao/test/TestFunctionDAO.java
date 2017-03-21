package dao.test;

import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.FunctionDAO;
import model.account.Account;
import model.account.Function;
import model.account.Role;
import model.identity.Company;

import java.time.LocalDateTime;
import java.util.*;

public class TestFunctionDAO extends TestDAO<Function> implements FunctionDAO {

    private Map<UUID, Function> functions;

    private DAOProvider provider;

    public TestFunctionDAO() {
        super();
        this.functions = new HashMap<>();

        UUID zero = new UUID(123, 0);
        Function f0 = new Function();
        f0.setUuid(zero);
        f0.setRole(new Role("customer"));
        f0.setAccount(TestAccountDAO.ACCOUNT_0);
        f0.setStartDate(LocalDateTime.MIN);
        f0.setEndDate(LocalDateTime.MAX);
        functions.put(zero, f0);

        setMapping(functions);
    }

    @Override
    public Function create(Company company, Role role, Account account, LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException {
        // TODO {company, role, account} is an unique key
        Function function = new Function(company, role, account, startDate, endDate);
        functions.put(function.getUuid(), function);
        return function;
    }

    @Override
    public Function update(UUID id, Company company, Role role, Account account, LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException {
        if (!functions.containsKey(id)) {
            throw new DataAccessException();
        }
        Function function = functions.get(id);
        function.setCompany(company);
        function.setRole(role);
        function.setAccount(account);
        function.setStartDate(startDate);
        function.setEndDate(endDate);
        return function;
    }

    @Override
    public Filter<Function> byAccount(Account account) {
        return null;
    }

    @Override
    public Filter<Function> byCompany(Company company) {
        return null;
    }

    @Override
    public void remove(UUID id) throws DataAccessException {

    }
}
