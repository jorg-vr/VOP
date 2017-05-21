package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.billing.Invoice;
import model.billing.InvoiceType;
import model.billing.VehicleInvoice;
import model.history.A;
import model.identity.Address;
import model.identity.Company;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by sam on 5/21/17.
 */
public class ProductionInvoiceDAOTest {
    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        ProductionProvider.getInstance().close();
    }


    @Test
    public void byPayer() throws Exception {
        Address address = new Address();
        address.setCountry("Country");
        address.setTown("Town");
        address.setPostalCode("9000");
        address.setStreetNumber("10T");
        address.setStreet("Street");

        Company company = new Company();
        company.setName("CC1");
        company.setAddress(address);

        Company company2 = new Company();
        company2.setName("CC2");
        company2.setAddress(address);

        Invoice invoice = new Invoice();
        invoice.setType(InvoiceType.CORRECTION);
        invoice.setPaid(false);
        invoice.setPayer(company);

        Invoice invoice2 = new Invoice();
        invoice2.setType(InvoiceType.BILLING);
        invoice2.setPaid(false);
        invoice2.setPayer(company2);

        Invoice invoice3 = new Invoice();
        invoice3.setType(InvoiceType.STATEMENT);
        invoice3.setPaid(false);
        invoice3.setPayer(company);

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getCompanyDAO().create(company);
            manager.getCompanyDAO().create(company2);
            manager.getInvoiceDao().create(invoice);
            manager.getInvoiceDao().create(invoice2);
            manager.getInvoiceDao().create(invoice3);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Collection<Invoice> invoices = manager.getInvoiceDao().listFiltered(manager.getInvoiceDao().byPayer(company));
            assertTrue(invoices.contains(invoice));
            assertTrue(!invoices.contains(invoice2));
            assertTrue(invoices.contains(invoice3));


            invoices = manager.getInvoiceDao().listFiltered(manager.getInvoiceDao().byPayer(company2));
            assertTrue(!invoices.contains(invoice));
            assertTrue(invoices.contains(invoice2));
            assertTrue(!invoices.contains(invoice3));
        }
    }

    @Test
    public void timeFilter() throws Exception {
        Address address = new Address();
        address.setCountry("Country");
        address.setTown("Town");
        address.setPostalCode("9000");
        address.setStreetNumber("10T");
        address.setStreet("Street");

        Company company = new Company();
        company.setName("CC1");
        company.setAddress(address);

        Company company2 = new Company();
        company2.setName("CC2");
        company2.setAddress(address);

        Invoice invoice = new Invoice();
        invoice.setType(InvoiceType.BILLING);
        invoice.setPaid(false);
        invoice.setPayer(company);
        invoice.setStartDate(LocalDateTime.of(2000, 1, 1, 1, 1));
        invoice.setEndDate(LocalDateTime.of(2017, 1, 1, 1, 1));

        Invoice invoice2 = new Invoice();
        invoice2.setType(InvoiceType.BILLING);
        invoice2.setPaid(false);
        invoice2.setPayer(company2);
        invoice2.setStartDate(LocalDateTime.of(2002, 1, 1, 1, 1));
        invoice2.setEndDate(LocalDateTime.of(2015, 1, 1, 1, 1));

        Invoice invoice3 = new Invoice();
        invoice3.setType(InvoiceType.STATEMENT);
        invoice3.setPaid(true);
        invoice3.setPayer(company);
        invoice3.setStartDate(LocalDateTime.of(2004, 1, 1, 1, 1));
        invoice3.setEndDate(LocalDateTime.of(2013, 1, 1, 1, 1));

        Invoice invoice4 = new Invoice();
        invoice4.setType(InvoiceType.STATEMENT);
        invoice4.setPaid(true);
        invoice4.setPayer(company);
        invoice4.setStartDate(LocalDateTime.of(2008, 1, 1, 1, 1));
        invoice4.setEndDate(LocalDateTime.of(2017, 1, 1, 1, 1));

        Invoice invoice5 = new Invoice();
        invoice5.setType(InvoiceType.CORRECTION);
        invoice5.setPaid(true);
        invoice5.setPayer(company);
        invoice5.setStartDate(LocalDateTime.of(2007, 1, 1, 1, 1));
        invoice5.setEndDate(LocalDateTime.of(2020, 1, 1, 1, 1));

        Invoice invoice6 = new Invoice();
        invoice6.setType(InvoiceType.CORRECTION);
        invoice6.setPaid(true);
        invoice6.setPayer(company);
        invoice6.setStartDate(LocalDateTime.of(2010, 1, 1, 1, 1));
        invoice6.setEndDate(LocalDateTime.of(2011, 1, 1, 1, 1));
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getCompanyDAO().create(company);
            manager.getCompanyDAO().create(company2);
            manager.getInvoiceDao().create(invoice);
            manager.getInvoiceDao().create(invoice2);
            manager.getInvoiceDao().create(invoice3);
            manager.getInvoiceDao().create(invoice4);
            manager.getInvoiceDao().create(invoice5);
            manager.getInvoiceDao().create(invoice6);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Collection<Invoice> invoices = manager.getInvoiceDao().listFiltered(manager.getInvoiceDao().startsBefore(LocalDateTime.of(2005, 1, 1, 1, 1)),
                    manager.getInvoiceDao().endsAfter(LocalDateTime.of(2014, 1, 1, 1, 1)));
            assertTrue(invoices.contains(invoice));
            assertTrue(invoices.contains(invoice2));
            assertTrue(!invoices.contains(invoice3));
            assertTrue(!invoices.contains(invoice4));
            assertTrue(!invoices.contains(invoice5));
            assertTrue(!invoices.contains(invoice6));

            invoices = manager.getInvoiceDao().listFiltered(manager.getInvoiceDao().startsBefore(LocalDateTime.of(2009, 1, 1, 1, 1)),
                    manager.getInvoiceDao().endsAfter(LocalDateTime.of(2016, 1, 1, 1, 1)));
            assertTrue(invoices.contains(invoice));
            assertTrue(!invoices.contains(invoice2));
            assertTrue(!invoices.contains(invoice3));
            assertTrue(invoices.contains(invoice4));
            assertTrue(invoices.contains(invoice5));
            assertTrue(!invoices.contains(invoice6));

            invoices = manager.getInvoiceDao().listFiltered(manager.getInvoiceDao().startsBefore(LocalDateTime.of(2009, 1, 1, 1, 1)),
                    manager.getInvoiceDao().endsAfter(LocalDateTime.of(2016, 1, 1, 1, 1)));
            assertTrue(invoices.contains(invoice));
            assertTrue(!invoices.contains(invoice2));
            assertTrue(!invoices.contains(invoice3));
            assertTrue(invoices.contains(invoice4));
            assertTrue(invoices.contains(invoice5));
            assertTrue(!invoices.contains(invoice6));
        }
    }

    @Test
    public void byPaid() throws Exception {
        Address address = new Address();
        address.setCountry("Country");
        address.setTown("Town");
        address.setPostalCode("9000");
        address.setStreetNumber("10T");
        address.setStreet("Street");

        Company company = new Company();
        company.setName("CC1");
        company.setAddress(address);

        Company company2 = new Company();
        company2.setName("CC2");
        company2.setAddress(address);

        Invoice invoice = new Invoice();
        invoice.setType(InvoiceType.CORRECTION);
        invoice.setPaid(false);
        invoice.setPayer(company);

        Invoice invoice2 = new Invoice();
        invoice2.setType(InvoiceType.BILLING);
        invoice2.setPaid(false);
        invoice2.setPayer(company2);

        Invoice invoice3 = new Invoice();
        invoice3.setType(InvoiceType.STATEMENT);
        invoice3.setPaid(true);
        invoice3.setPayer(company);

        Invoice invoice4 = new Invoice();
        invoice4.setType(InvoiceType.STATEMENT);
        invoice4.setPaid(true);
        invoice4.setPayer(company);

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getCompanyDAO().create(company);
            manager.getCompanyDAO().create(company2);
            manager.getInvoiceDao().create(invoice);
            manager.getInvoiceDao().create(invoice2);
            manager.getInvoiceDao().create(invoice3);
            manager.getInvoiceDao().create(invoice4);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Collection<Invoice> invoices = manager.getInvoiceDao().listFiltered(manager.getInvoiceDao().byPaid(true));
            assertTrue(!invoices.contains(invoice));
            assertTrue(!invoices.contains(invoice2));
            assertTrue(invoices.contains(invoice3));
            assertTrue(invoices.contains(invoice4));


            invoices = manager.getInvoiceDao().listFiltered(manager.getInvoiceDao().byPaid(false));
            assertTrue(invoices.contains(invoice));
            assertTrue(invoices.contains(invoice2));
            assertTrue(!invoices.contains(invoice3));
            assertTrue(!invoices.contains(invoice4));
        }
    }

    @Test
    public void byType() throws Exception {
        Address address = new Address();
        address.setCountry("Country");
        address.setTown("Town");
        address.setPostalCode("9000");
        address.setStreetNumber("10T");
        address.setStreet("Street");

        Company company = new Company();
        company.setName("CC1");
        company.setAddress(address);

        Company company2 = new Company();
        company2.setName("CC2");
        company2.setAddress(address);

        Invoice invoice = new Invoice();
        invoice.setType(InvoiceType.BILLING);
        invoice.setPaid(false);
        invoice.setPayer(company);

        Invoice invoice2 = new Invoice();
        invoice2.setType(InvoiceType.BILLING);
        invoice2.setPaid(false);
        invoice2.setPayer(company2);

        Invoice invoice3 = new Invoice();
        invoice3.setType(InvoiceType.STATEMENT);
        invoice3.setPaid(true);
        invoice3.setPayer(company);

        Invoice invoice4 = new Invoice();
        invoice4.setType(InvoiceType.STATEMENT);
        invoice4.setPaid(true);
        invoice4.setPayer(company);

        Invoice invoice5 = new Invoice();
        invoice5.setType(InvoiceType.CORRECTION);
        invoice5.setPaid(true);
        invoice5.setPayer(company);

        Invoice invoice6 = new Invoice();
        invoice6.setType(InvoiceType.CORRECTION);
        invoice6.setPaid(true);
        invoice6.setPayer(company);

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getCompanyDAO().create(company);
            manager.getCompanyDAO().create(company2);
            manager.getInvoiceDao().create(invoice);
            manager.getInvoiceDao().create(invoice2);
            manager.getInvoiceDao().create(invoice3);
            manager.getInvoiceDao().create(invoice4);
            manager.getInvoiceDao().create(invoice5);
            manager.getInvoiceDao().create(invoice6);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Collection<Invoice> invoices = manager.getInvoiceDao().listFiltered(manager.getInvoiceDao().byType(InvoiceType.CORRECTION));
            assertTrue(!invoices.contains(invoice));
            assertTrue(!invoices.contains(invoice2));
            assertTrue(!invoices.contains(invoice3));
            assertTrue(!invoices.contains(invoice4));
            assertTrue(invoices.contains(invoice5));
            assertTrue(invoices.contains(invoice6));

            invoices = manager.getInvoiceDao().listFiltered(manager.getInvoiceDao().byType(InvoiceType.STATEMENT));
            assertTrue(!invoices.contains(invoice));
            assertTrue(!invoices.contains(invoice2));
            assertTrue(invoices.contains(invoice3));
            assertTrue(invoices.contains(invoice4));
            assertTrue(!invoices.contains(invoice5));
            assertTrue(!invoices.contains(invoice6));

            invoices = manager.getInvoiceDao().listFiltered(manager.getInvoiceDao().byType(InvoiceType.BILLING));
            assertTrue(invoices.contains(invoice));
            assertTrue(invoices.contains(invoice2));
            assertTrue(!invoices.contains(invoice3));
            assertTrue(!invoices.contains(invoice4));
            assertTrue(!invoices.contains(invoice5));
            assertTrue(!invoices.contains(invoice6));

        }
    }

    @Test
    public void crud() throws Exception {
        Address address = new Address();
        address.setCountry("Country");
        address.setTown("Town");
        address.setPostalCode("9000");
        address.setStreetNumber("10T");
        address.setStreet("Street");

        Company company = new Company();
        company.setName("CC1");
        company.setAddress(address);

        Company company2 = new Company();
        company2.setName("CC2");
        company2.setAddress(address);

        Invoice invoice = new Invoice();
        invoice.setType(InvoiceType.BILLING);
        invoice.setPaid(false);
        invoice.setPayer(company);
        invoice.setStartDate(LocalDateTime.of(2000, 1, 1, 1, 1));
        invoice.setEndDate(LocalDateTime.of(2017, 1, 1, 1, 1));

        Invoice invoice2 = new Invoice();
        invoice2.setType(InvoiceType.BILLING);
        invoice2.setPaid(false);
        invoice2.setPayer(company2);
        invoice.setStartDate(LocalDateTime.of(2002, 1, 1, 1, 1));
        invoice.setEndDate(LocalDateTime.of(2015, 1, 1, 1, 1));

        Invoice invoice3 = new Invoice();
        invoice3.setType(InvoiceType.STATEMENT);
        invoice3.setPaid(true);
        invoice3.setPayer(company);
        invoice3.setStartDate(LocalDateTime.of(2004, 1, 1, 1, 1));
        invoice3.setEndDate(LocalDateTime.of(2013, 1, 1, 1, 1));

        Invoice invoice4 = new Invoice();
        invoice4.setType(InvoiceType.STATEMENT);
        invoice4.setPaid(true);
        invoice4.setPayer(company);
        invoice4.setStartDate(LocalDateTime.of(2008, 1, 1, 1, 1));
        invoice4.setEndDate(LocalDateTime.of(2017, 1, 1, 1, 1));

        Invoice invoice5 = new Invoice();
        invoice5.setType(InvoiceType.CORRECTION);
        invoice5.setPaid(true);
        invoice5.setPayer(company);
        invoice5.setStartDate(LocalDateTime.of(2007, 1, 1, 1, 1));
        invoice5.setEndDate(LocalDateTime.of(2020, 1, 1, 1, 1));

        Invoice invoice6 = new Invoice();
        invoice6.setType(InvoiceType.CORRECTION);
        invoice6.setPaid(true);
        invoice6.setPayer(company);
        invoice6.setStartDate(LocalDateTime.of(2010, 1, 1, 1, 1));
        invoice6.setEndDate(LocalDateTime.of(2011, 1, 1, 1, 1));

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getCompanyDAO().create(company);
            manager.getCompanyDAO().create(company2);
            manager.getInvoiceDao().create(invoice);
            manager.getInvoiceDao().create(invoice2);
            manager.getInvoiceDao().create(invoice3);
            manager.getInvoiceDao().create(invoice4);
            manager.getInvoiceDao().create(invoice5);
            manager.getInvoiceDao().create(invoice6);
        }
        invoice.setPaid(true);
        VehicleInvoice vehicleInvoice = new VehicleInvoice();
        vehicleInvoice.setFranchise(1);
        vehicleInvoice.setTotalCost(1);
        vehicleInvoice.setTotalTax(1);
        vehicleInvoice.setVin("AAAAAAAAAAAAAAAAA");
        vehicleInvoice.setInsuredValue(5);
        vehicleInvoice.setLicensePlate("123123");
        vehicleInvoice.setSuretyType(null);
        vehicleInvoice.setVehicleInsuranceID(null);
        invoice2.setVehicleInvoices(new ArrayList<>(Arrays.asList(new VehicleInvoice[]{vehicleInvoice})));
        invoice3.setEndDate(LocalDateTime.of(2223, 1, 1, 1, 1));
        invoice4.setPayer(company2);
        invoice5.setType(InvoiceType.BILLING);
        invoice6.setStartDate(LocalDateTime.of(1999, 1, 1, 1, 1, 1));
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInvoiceDao().update(invoice);
            manager.getInvoiceDao().update(invoice2);
            manager.getInvoiceDao().update(invoice3);
            manager.getInvoiceDao().update(invoice4);
            manager.getInvoiceDao().update(invoice5);
            manager.getInvoiceDao().update(invoice6);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            assertTrue(manager.getInvoiceDao().get(invoice.getUuid()).isPaid());
            Invoice tmp = manager.getInvoiceDao().get(invoice2.getUuid());
            assertTrue(tmp.getVehicleInvoices().size() == 1);
            VehicleInvoice vehicleInvoice1 = tmp.getVehicleInvoices().iterator().next();
            assertTrue(vehicleInvoice.getFranchise() == 1);
            assertTrue(vehicleInvoice.getTotalCost() == 1);
            assertTrue(vehicleInvoice.getTotalTax() == 1);
            assertTrue(vehicleInvoice.getInsuredValue() == 5);
            assertTrue(vehicleInvoice.getSuretyType() == null);
            assertTrue(vehicleInvoice.getVehicleInsuranceID() == null);
            assertTrue(vehicleInvoice.getVin().equals("AAAAAAAAAAAAAAAAA"));
            assertTrue(vehicleInvoice.getLicensePlate().equals("123123"));
            assertTrue(manager.getInvoiceDao().get(invoice3.getUuid()).getEndDate().equals(LocalDateTime.of(2223, 1, 1, 1, 1)));
            assertTrue(manager.getInvoiceDao().get(invoice4.getUuid()).getPayer().equals(company2));
            assertTrue(manager.getInvoiceDao().get(invoice5.getUuid()).getType().equals(InvoiceType.BILLING));
            assertTrue(manager.getInvoiceDao().get(invoice6.getUuid()).getStartDate().equals(LocalDateTime.of(1999, 1, 1, 1, 1, 1)));
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInvoiceDao().remove(invoice.getUuid());
            manager.getInvoiceDao().remove(invoice2.getUuid());
            manager.getInvoiceDao().remove(invoice3.getUuid());
            manager.getInvoiceDao().remove(invoice4.getUuid());
            manager.getInvoiceDao().remove(invoice5.getUuid());
            manager.getInvoiceDao().remove(invoice6.getUuid());
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            try {
                manager.getInvoiceDao().get(invoice.getUuid());
                fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getInvoiceDao().get(invoice2.getUuid());
                fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getInvoiceDao().get(invoice3.getUuid());
                fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getInvoiceDao().get(invoice4.getUuid());
                fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getInvoiceDao().get(invoice5.getUuid());
                fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getInvoiceDao().get(invoice6.getUuid());
                fail();
            } catch (ObjectNotFoundException e) {

            }
        }
    }

}