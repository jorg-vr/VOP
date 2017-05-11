package database.dao;

import dao.database.ProductionProvider;
import dao.interfaces.CustomerDAO;
import dao.interfaces.DAOManager;
import database.DAOTestUtil;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;


public class ProductionCustomerDAOFiltersTest {
    private static Address a1, a2, a3;
    private static Customer v1, v2, v3;

    @BeforeClass
    public static void init() throws Exception {
        ProductionProvider.initializeProvider("unittest");

        a1 = new Address("streettest n1", "59", "town 1", "9999", "country 1");
        a2 = new Address("streettest n2", "60", "town 2", "99999", "country 2");
        a3 = new Address("streettest n2", "60", "town 2", "99999", "country 2");
        v1 = DAOTestUtil.createCustomer(new Customer(a1, "911", "customername 1", "btw123"));
        v2 = DAOTestUtil.createCustomer(new Customer(a2, "911", "customername 2", "btw123"));
        v3 = DAOTestUtil.createCustomer(new Customer(a3, "912", "customername 2", "btw124"));
    }

    @AfterClass
    public static void end() throws Exception {

        DAOTestUtil.removeCustomer(v1.getUuid());
        DAOTestUtil.removeCustomer(v2.getUuid());
        DAOTestUtil.removeCustomer(v3.getUuid());

        ProductionProvider.getInstance().close();
    }

    @Test
    public void listFiltered() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            CustomerDAO customerDAO = daoManager.getCustomerDAO();
            Collection<Customer> customers = customerDAO.listFiltered(customerDAO.byName("customername 2"), customerDAO.byVatNumber("btw123"));
            assertTrue("Combining filters doesn't work", !customers.contains(v1) && customers.contains(v2) && !customers.contains(v3));
            customers = customerDAO.listFiltered(customerDAO.byName("customername 1"), customerDAO.byVatNumber("btw123"));
            assertTrue("Combining filters doesn't work", customers.contains(v1) && !customers.contains(v2) && !customers.contains(v3));
        }
    }

    @Test
    public void containsFleet() throws Exception {
        //TODO
        assertTrue("Does not contain fleet", true);
    }

    @Test
    public void byName() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            CustomerDAO customerDAO = daoManager.getCustomerDAO();
            Collection<Customer> c1 = customerDAO.listFiltered(customerDAO.byName("customername 1"));
            Collection<Customer> c2 = customerDAO.listFiltered(customerDAO.byName("customername 2"));
            assertTrue("byStreetNumber filter doesn't work", c1.contains(v1) && !c1.contains(v2) && !c1.contains(v3));
            assertTrue("byStreetNumber filter doesn't work", !c2.contains(v1) && c2.contains(v2) && c2.contains(v3));
        }
    }

    @Test
    public void containsName() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            CustomerDAO customerDAO = daoManager.getCustomerDAO();
            Collection<Customer> c1 = customerDAO.listFiltered(customerDAO.containsName("customer"));
            Collection<Customer> c2 = customerDAO.listFiltered(customerDAO.containsName("name 2"));
            assertTrue("containsName filter doesn't work", c1.contains(v1) && c1.contains(v2) && c1.contains(v3));
            assertTrue("containsName filter doesn't work", !c2.contains(v1) && c2.contains(v2) && c2.contains(v3));
        }
    }

    @Test
    public void byVatNumber() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            CustomerDAO customerDAO = daoManager.getCustomerDAO();
            Collection<Customer> c1 = customerDAO.listFiltered(customerDAO.byVatNumber("btw123"));
            Collection<Customer> c2 = customerDAO.listFiltered(customerDAO.byVatNumber("btw124"));
            assertTrue("byVatNumber filter doesn't work", c1.contains(v1) && c1.contains(v2) && !c1.contains(v3));
            assertTrue("byVatNumber filter doesn't work", !c2.contains(v1) && !c2.contains(v2) && c2.contains(v3));
        }
    }

    @Test
    public void byPhoneNumber() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            CustomerDAO customerDAO = daoManager.getCustomerDAO();
            Collection<Customer> c1 = customerDAO.listFiltered(customerDAO.byPhoneNumber("911"));
            Collection<Customer> c2 = customerDAO.listFiltered(customerDAO.byPhoneNumber("912"));
            assertTrue("byPhoneNumber filter doesn't work", c1.contains(v1) && c1.contains(v2) && !c1.contains(v3));
            assertTrue("byPhoneNumber filter doesn't work", !c2.contains(v1) && !c2.contains(v2) && c2.contains(v3));
        }
    }


    @Test
    public void byAddress() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            CustomerDAO customerDAO = daoManager.getCustomerDAO();
            Collection<Customer> c1 = customerDAO.listFiltered(customerDAO.byAddress(a1));
            Collection<Customer> c2 = customerDAO.listFiltered(customerDAO.byAddress(a2));
            assertTrue("byAddress filter doesn't work", c1.contains(v1) && !c1.contains(v2) && !c1.contains(v3));
            assertTrue("byAddress filter doesn't work", !c2.contains(v1) && c2.contains(v2) && !c2.contains(v3));
        }
    }

}