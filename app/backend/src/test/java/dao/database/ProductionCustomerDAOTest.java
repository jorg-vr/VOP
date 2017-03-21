package dao.database;

import dao.interfaces.CustomerDAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.identity.Customer;
import org.junit.*;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ProductionCustomerDAOTest {
    private static DAOProvider daoProvider;
    private static CustomerDAO customerDAO;
    private static Customer v1, v2, v3;

    @BeforeClass
    public static void init() throws Exception {
        //ProductionProvider.initializeProvider(false);
        daoProvider = ProductionProvider.getInstance();
        customerDAO = daoProvider.getCustomerDAO();
    }

    @AfterClass
    public static void end() throws Exception {
        //daoProvider.close();
    }

    @Before
    public void startUp() throws DataAccessException {

        v1 = customerDAO.create("test1", null, "123", "123");
        v2 = customerDAO.create("test2", null, "234", "123");
        v3 = customerDAO.create("test3", null, "345", "12345");
    }

    @After
    public void cleanUp() throws DataAccessException {

        customerDAO.remove(v1.getUuid());
        customerDAO.remove(v2.getUuid());
        customerDAO.remove(v3.getUuid());
    }


    @Test
    public void get() throws Exception {
        assertEquals(v1, customerDAO.get(v1.getUuid()));
        assertEquals(v2, customerDAO.get(v2.getUuid()));
        assertEquals(v3, customerDAO.get(v3.getUuid()));
    }

    @Test
    public void update() throws Exception {
        customerDAO.update(v1.getUuid(), v1.getName(), v1.getAddress(), v1.getPhoneNumber(), "321");
        v1.setBtwNumber("321");
        assertEquals(v1, customerDAO.get(v1.getUuid()));
    }

    @Test
    public void remove() throws Exception {

        Customer v4 = customerDAO.create("test1", null, "123", "123");
        Customer v5 = customerDAO.create("test2", null, "234", "123");
        Customer v6 = customerDAO.create("test3", null, "345", "12345");
        customerDAO.remove(v4.getUuid());
        customerDAO.remove(v5.getUuid());
        customerDAO.remove(v6.getUuid());
        Collection<Customer> customers = customerDAO.listFiltered(new Filter[]{});
        assert (!customers.contains(v4) && !customers.contains(v5) && !customers.contains(v6));
    }

    @Test
    public void listFiltered() throws Exception {
        Collection<Customer> customers = customerDAO.listFiltered(customerDAO.byName("test1"), customerDAO.byVatNumber("123"));
        assertTrue("Filter byName doesn't work", customers.contains(v1) && !customers.contains(v2) && !customers.contains(v3));
        customers = customerDAO.listFiltered(customerDAO.byName("test3"), customerDAO.byVatNumber("123"));
        assertTrue("Filter byName doesn't work", !customers.contains(v1) && !customers.contains(v2) && !customers.contains(v3));
    }

    @Test
    public void containsFleet() throws Exception {
        //TODO
        assertTrue("Does not contain fleet", true);
    }

    @Test
    public void byName() throws Exception {
        Collection<Customer> customers = customerDAO.listFiltered(customerDAO.byName("test1"));
        assertTrue("Filter byName doesn't work", customers.contains(v1) && !customers.contains(v2) && !customers.contains(v3));
    }

    @Test
    public void containsName() throws Exception {
        Collection<Customer> customers = customerDAO.listFiltered(customerDAO.containsName("test"));
        assertTrue("Filter containsName doesn't work", customers.contains(v1) && customers.contains(v2) && customers.contains(v3));
    }

    @Test
    public void byVatNumber() throws Exception {
        Collection<Customer> customers = customerDAO.listFiltered(customerDAO.byVatNumber("123"));
        assertTrue("Filter containsName doesn't work", customers.contains(v1) && customers.contains(v2) && !customers.contains(v3));
    }

    @Test
    public void byPhoneNumber() throws Exception {
        Collection<Customer> customers = customerDAO.listFiltered(customerDAO.byPhoneNumber("234"));
        assertTrue("Filter phonenumber doesn't work", !customers.contains(v1) && customers.contains(v2) && !customers.contains(v3));
    }

    @Test
    public void byAddress() throws Exception {
        //TODO
    }

    @Test
    public void byBankAccountNummber() throws Exception {
        //TODO
    }

    @Test
    public void byEmail() throws Exception {
        //TODO
    }

}