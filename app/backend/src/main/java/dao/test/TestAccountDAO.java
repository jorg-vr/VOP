package dao.test;

import dao.interfaces.AccountDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.account.Account;
import model.identity.Identity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Billie Devolder on 8/03/2017.
 */
public class TestAccountDAO extends TestDAO<Account> implements AccountDAO {

    public static final Account ACCOUNT_0 = new Account();
    public static final Account ACCOUNT_1 = new Account();

    public static final UUID ACCOUNT_0_ID = new UUID(10, 0);
    public static final UUID ACCOUNT_1_ID = new UUID(10, 1);

    static {
        ACCOUNT_0.setUuid(ACCOUNT_0_ID);
        ACCOUNT_0.setLogin("jan.janssens@mail.com");
        ACCOUNT_0.setHashedPassword("wachtwoord123");

        ACCOUNT_1.setUuid(ACCOUNT_1_ID);
        ACCOUNT_1.setLogin("rik.peeters@mail.com");
        ACCOUNT_1.setHashedPassword("ea14J489M");
    }

    private static Map<UUID, Account> accountsID = new HashMap<>();

    public TestAccountDAO() {
        super();

        ACCOUNT_0.setPerson(TestPersonDAO.PERSON_0);
        accountsID.put(ACCOUNT_0_ID, ACCOUNT_0);

        ACCOUNT_1.setPerson(TestPersonDAO.PERSON_1);
        accountsID.put(ACCOUNT_1_ID, ACCOUNT_1);

        setMapping(accountsID);
    }

    @Override
    public Filter<Account> bySecurity(String login, String password) {
        return null;
    }

    @Override
    public Filter<Account> byIdentity(Identity identity) {
        return null;
    }
}
