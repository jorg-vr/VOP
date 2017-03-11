package dao.test;

import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.FunctionDAO;
import model.account.Account;
import model.account.Function;
import model.account.Role;
import model.identity.Company;

import java.util.*;

public class TestFunctionDAO extends TestDAO<Function> implements FunctionDAO {

    private Map<UUID, Function> functions;

    private DAOProvider provider;

    public TestFunctionDAO() {
        super();
        this.provider = TestDAOProvider.getInstance();
        this.functions = new HashMap<>();

        UUID zero = new UUID(123, 0);
        Function f0 = new Function();
        f0.setUuid(zero);
        f0.setRole(new Role("customer"));
        try {
            provider.getAccountDao();
            f0.setAccount(provider.getAccountDao().get(TestAccountDAO.ACCOUNT_0_ID));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        functions.put(zero, f0);

        setMapping(functions);
    }

    @Override
    public Filter<Function> byAccount(Account account) {
        return null;
    }

    @Override
    public Filter<Function> byCompany(Company company) {
        return null;
    }

}
