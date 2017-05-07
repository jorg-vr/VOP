package database;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.interfaces.*;
import model.billing.Invoice;
import model.billing.InvoiceType;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.fail;

@Ignore
public class InvoiceParametersTest {

    private static DAOManager daoManager;
    private static Address address;
    private static Customer customer;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoManager = ProductionProvider.getInstance().getDaoManager();
        AddressDAO addressDAO = daoManager.getAddressDao();
        CustomerDAO customerDAO = daoManager.getCustomerDAO();

        address = addressDAO.create(new Address("Street", "55", "Town", "9000", "Country"));
        customer = customerDAO.create(new Customer(address, "123456789", "customerName", "BE123123123B01"));
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        AddressDAO addressDAO = daoManager.getAddressDao();
        CustomerDAO customerDAO = daoManager.getCustomerDAO();

        customerDAO.remove(customer.getUuid());
        addressDAO.remove(address.getUuid());

        daoManager.close();
        ProductionProvider.getInstance().close();
    }

    @Test
    public void allFields() throws Exception {
        Invoice invoice = null;
        try {
            InvoiceDAO invoiceDAO = daoManager.getInvoiceDao();
            invoice = invoiceDAO.create(new Invoice(customer, null, InvoiceType.BILLING, false, LocalDateTime.of(2017, 7, 15, 0, 0), LocalDateTime.of(2017, 9, 15, 0, 0)));
            invoiceDAO.remove(invoice.getUuid());
        } catch (DataAccessException d) {
            if (invoice == null) {
                fail("Invoice failed to create despite all required field being filled in");
            }
        }
    }

    @Test
    public void payerField() throws Exception {
        Invoice invoice = null;
        try {
            InvoiceDAO invoiceDAO = daoManager.getInvoiceDao();
            invoice = invoiceDAO.create(new Invoice(null, null, InvoiceType.BILLING, false, LocalDateTime.of(2017, 7, 15, 0, 0), LocalDateTime.of(2017, 9, 15, 0, 0)));
            invoiceDAO.remove(invoice.getUuid());
            fail("Invoice succesfully created with payer field null when an exception was expected");
        } catch (DataAccessException d) {
            if (invoice != null) {
                fail("Invoice succesfully created with payer field null when an exception was expected");
            }
        }
    }

    @Test
    public void typeField() throws Exception {
        Invoice invoice = null;
        try {
            InvoiceDAO invoiceDAO = daoManager.getInvoiceDao();
            invoice = invoiceDAO.create(new Invoice(customer, null, null, false, LocalDateTime.of(2017, 7, 15, 0, 0), LocalDateTime.of(2017, 9, 15, 0, 0)));
            invoiceDAO.remove(invoice.getUuid());
            fail("Invoice succesfully created with type field null when an exception was expected");
        } catch (DataAccessException d) {
            if (invoice != null) {
                fail("Invoice succesfully created with type field null when an exception was expected");
            }
        }
    }

    @Test
    public void startDateField() throws Exception {
        Invoice invoice = null;
        try {
            InvoiceDAO invoiceDAO = daoManager.getInvoiceDao();
            invoice = invoiceDAO.create(new Invoice(customer, null, InvoiceType.BILLING, false, null, LocalDateTime.of(2017, 9, 15, 0, 0)));
            invoiceDAO.remove(invoice.getUuid());
            fail("Invoice succesfully created with startDate field null when an exception was expected");
        } catch (DataAccessException d) {
            if (invoice != null) {
                fail("Invoice succesfully created with startDate field null when an exception was expected");
            }
        }
    }

    @Test
    public void endDateField() throws Exception {
        Invoice invoice = null;
        try {
            InvoiceDAO invoiceDAO = daoManager.getInvoiceDao();
            invoice = invoiceDAO.create(new Invoice(customer, null, InvoiceType.BILLING, false, LocalDateTime.of(2017, 7, 15, 0, 0), null));
            invoiceDAO.remove(invoice.getUuid());
            fail("Invoice succesfully created with endDate field null when an exception was expected");
        } catch (DataAccessException d) {
            if (invoice != null) {
                fail("Invoice succesfully created with endDate field null when an exception was expected");
            }
        }
    }
}
