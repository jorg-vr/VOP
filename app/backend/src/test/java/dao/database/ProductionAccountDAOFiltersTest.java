package dao.database;

import dao.interfaces.AccountDAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.PersonDAO;
import model.account.Account;
import model.identity.Person;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;


public class ProductionAccountDAOFiltersTest {
    private static DAOProvider daoProvider;
    private static AccountDAO accountDAO;
    private static PersonDAO personDAO;
    private static Account a1, a2, a3;
    private static Person p1, p2;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        accountDAO = daoProvider.getAccountDao();
        personDAO = daoProvider.getPersonDAO();

        p1 = personDAO.create("Firstname 1", "Lastname 1", "Email@address1.com");
        p2 = personDAO.create("Firstname 2", "Lastname 2", "Email@address2.com");
        a1 = accountDAO.create("login1", "hashedPassword1", p1);
        a2 = accountDAO.create("login1", "hashedPassword2", p2);
        a3 = accountDAO.create("login2", "hashedPassword2", p1);
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        accountDAO.remove(a1.getUuid());
        accountDAO.remove(a2.getUuid());
        accountDAO.remove(a3.getUuid());
        personDAO.remove(p1.getUuid());
        personDAO.remove(p2.getUuid());
        daoProvider.close();
    }

    @Ignore
    @Test
    public void bySecurity() throws Exception {
        Collection<Account> c1 = accountDAO.listFiltered(accountDAO.bySecurity("login1", "hashedPassword1"));
        Collection<Account> c2 = accountDAO.listFiltered(accountDAO.bySecurity("login1", "hashedPassword2"));
        Collection<Account> c3 = accountDAO.listFiltered(accountDAO.bySecurity("login2", "hashedPassword2"));
        assertTrue("bySecurity filter doesn't work", c1.contains(a1) && !c1.contains(a2) && !c1.contains(a3));
        assertTrue("bySecurity filter doesn't work", !c2.contains(a1) && c2.contains(a2) && !c2.contains(a3));
        assertTrue("bySecurity filter doesn't work", !c3.contains(a1) && !c3.contains(a2) && c3.contains(a3));
    }

    @Ignore
    @Test
    public void byPerson() throws Exception {
        Collection<Account> c1 = accountDAO.listFiltered(accountDAO.byPerson(p1));
        Collection<Account> c2 = accountDAO.listFiltered(accountDAO.byPerson(p2));
        assertTrue("byPerson filter doesn't work", c1.contains(a1) && !c1.contains(a2) && c1.contains(a3));
        assertTrue("byPerson filter doesn't work", !c2.contains(a1) && c2.contains(a2) && !c2.contains(a3));
    }

    //TODO: test multiple filters in 1 request

    //TODO: test what happens when a Person is removed from the database that's stil related to an Account depending on need
}
