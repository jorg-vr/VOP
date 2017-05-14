package database.constraints;

import dao.database.ProductionProvider;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import model.billing.Invoice;
import model.billing.InvoiceType;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;

import static database.DAOTestUtil.*;
import static org.junit.Assert.fail;

public class InvoiceParametersTest {

    private static Customer customer;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");

        Address address = new Address("Street", "55", "Town", "9000", "Country");
        customer = createCustomer(new Customer(address, "123456789", "customerName", "BE123123123B01"));
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        removeCustomer(customer.getUuid());

        ProductionProvider.getInstance().close();
    }

    @Test
    public void allFields() throws Exception {
        try {
            Invoice invoice = createInvoice(new Invoice(customer, null, InvoiceType.BILLING, false, LocalDateTime.of(2017, 7, 15, 0, 0), LocalDateTime.of(2017, 9, 15, 0, 0)));
            removeInvoice(invoice.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
            fail("Invoice failed to create despite all required field being filled in");
        }
    }

    @Test
    public void payerField() throws Exception {
        try {
            Invoice invoice = createInvoice(new Invoice(null, null, InvoiceType.BILLING, false, LocalDateTime.of(2017, 7, 15, 0, 0), LocalDateTime.of(2017, 9, 15, 0, 0)));
            removeInvoice(invoice.getUuid());
            fail("Invoice succesfully created with payer field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void typeField() throws Exception {
        try {
            Invoice invoice = createInvoice(new Invoice(customer, null, null, false, LocalDateTime.of(2017, 7, 15, 0, 0), LocalDateTime.of(2017, 9, 15, 0, 0)));
            removeInvoice(invoice.getUuid());
            fail("Invoice succesfully created with type field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void startDateField() throws Exception {
        try {
            Invoice invoice = createInvoice(new Invoice(customer, null, InvoiceType.BILLING, false, null, LocalDateTime.of(2017, 9, 15, 0, 0)));
            removeInvoice(invoice.getUuid());
            fail("Invoice succesfully created with startDate field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void endDateField() throws Exception {
        try {
            Invoice invoice = createInvoice(new Invoice(customer, null, InvoiceType.BILLING, false, LocalDateTime.of(2017, 7, 15, 0, 0), null));
            removeInvoice(invoice.getUuid());
            fail("Invoice succesfully created with endDate field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }
}
