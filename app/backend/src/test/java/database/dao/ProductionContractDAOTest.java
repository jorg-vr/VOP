package database.dao;

import dao.database.ProductionProvider;
import dao.interfaces.ContractDAO;
import dao.interfaces.DAOManager;
import model.identity.Address;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.Contract;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by sam on 5/18/17.
 */
public class ProductionContractDAOTest {

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
    public void byCustomer() throws Exception {
        Contract contract1 = new Contract();
        Contract contract2 = new Contract();
        Contract contract3 = new Contract();
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Address address = new Address();

        address.setCountry("Testland");
        address.setPostalCode("9876");
        address.setStreet("Teststraat");
        address.setTown("Testdorp");
        address.setStreetNumber("4T");

        customer1.setName("Klant 1");
        customer1.setAddress(address);
        customer2.setName("Klant 2");
        customer2.setAddress(address);

        contract1.setStartDate(LocalDateTime.now().minusMonths(8));
        contract1.setEndDate(LocalDateTime.now().plusMonths(8));
        contract1.setCustomer(customer1);

        contract2.setStartDate(LocalDateTime.now().minusMonths(8));
        contract2.setEndDate(LocalDateTime.now().plusMonths(8));
        contract2.setCustomer(customer1);

        contract3.setStartDate(LocalDateTime.now().minusMonths(8));
        contract3.setEndDate(LocalDateTime.now().plusMonths(8));
        contract3.setCustomer(customer2);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getAddressDao().create(address);
            manager.getCustomerDAO().create(customer1);
            manager.getCustomerDAO().create(customer2);
            ContractDAO contractDAO = manager.getContractDao();
            contractDAO.create(contract1);
            contractDAO.create(contract2);
            contractDAO.create(contract3);

            Collection<Contract> contracts = contractDAO.listFiltered(contractDAO.byCustomer(customer1));
            assertTrue(contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(!contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.byCustomer(customer2));
            assertTrue(!contracts.contains(contract1));
            assertTrue(!contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));
        }
    }

    @Test
    public void byInsuranceCompany() throws Exception {
        Contract contract1 = new Contract();
        Contract contract2 = new Contract();
        Contract contract3 = new Contract();
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        InsuranceCompany company1 = new InsuranceCompany();
        InsuranceCompany company2 = new InsuranceCompany();
        Address address = new Address();

        address.setCountry("Testland");
        address.setPostalCode("9876");
        address.setStreet("Teststraat");
        address.setTown("Testdorp");
        address.setStreetNumber("4T");

        customer1.setName("Klant 1");
        customer1.setAddress(address);
        customer2.setName("Klant 2");
        customer2.setAddress(address);

        contract1.setStartDate(LocalDateTime.now().minusMonths(8));
        contract1.setEndDate(LocalDateTime.now().plusMonths(8));
        contract1.setCustomer(customer1);
        contract1.setCompany(company1);

        contract2.setStartDate(LocalDateTime.now().minusMonths(8));
        contract2.setEndDate(LocalDateTime.now().plusMonths(8));
        contract2.setCustomer(customer1);
        contract2.setCompany(company2);

        contract3.setStartDate(LocalDateTime.now().minusMonths(8));
        contract3.setEndDate(LocalDateTime.now().plusMonths(8));
        contract3.setCustomer(customer2);
        contract3.setCompany(company2);

        company1.setName("Company 1");
        company1.setAddress(address);
        company2.setName("Company 2");
        company2.setAddress(address);

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getAddressDao().create(address);
            manager.getCustomerDAO().create(customer1);
            manager.getCustomerDAO().create(customer2);
            manager.getInsuranceCompanyDao().create(company1);
            manager.getInsuranceCompanyDao().create(company2);
            ContractDAO contractDAO = manager.getContractDao();
            contractDAO.create(contract1);
            contractDAO.create(contract2);
            contractDAO.create(contract3);

            Collection<Contract> contracts = contractDAO.listFiltered(contractDAO.byInsuranceCompany(company1));
            assertTrue(contracts.contains(contract1));
            assertTrue(!contracts.contains(contract2));
            assertTrue(!contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.byInsuranceCompany(company2));
            assertTrue(!contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));
        }
    }

    @Test
    public void startAndEndFilters() throws Exception {
        Contract contract1 = new Contract();
        Contract contract2 = new Contract();
        Contract contract3 = new Contract();
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Address address = new Address();

        address.setCountry("Testland");
        address.setPostalCode("9876");
        address.setStreet("Teststraat");
        address.setTown("Testdorp");
        address.setStreetNumber("4T");

        customer1.setName("Klant 1");
        customer1.setAddress(address);
        customer2.setName("Klant 2");
        customer2.setAddress(address);

        contract1.setStartDate(LocalDateTime.now().minusMonths(10));
        contract1.setEndDate(LocalDateTime.now().plusMonths(10));
        contract1.setCustomer(customer1);

        contract2.setStartDate(LocalDateTime.now().minusMonths(3));
        contract2.setEndDate(LocalDateTime.now().plusMonths(3));
        contract2.setCustomer(customer1);

        contract3.setStartDate(LocalDateTime.now().minusMonths(5));
        contract3.setEndDate(LocalDateTime.now().plusMonths(5));
        contract3.setCustomer(customer2);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getAddressDao().create(address);
            manager.getCustomerDAO().create(customer1);
            manager.getCustomerDAO().create(customer2);
            ContractDAO contractDAO = manager.getContractDao();
            contractDAO.create(contract1);
            contractDAO.create(contract2);
            contractDAO.create(contract3);

            //StartsBefore
            Collection<Contract> contracts = contractDAO.listFiltered(contractDAO.startsBefore(LocalDateTime.now()));
            assertTrue(contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.startsBefore(LocalDateTime.now().minusMonths(4)));
            assertTrue(contracts.contains(contract1));
            assertTrue(!contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.startsBefore(LocalDateTime.now().minusMonths(6)));
            assertTrue(contracts.contains(contract1));
            assertTrue(!contracts.contains(contract2));
            assertTrue(!contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.startsBefore(LocalDateTime.now().minusMonths(11)));
            assertTrue(!contracts.contains(contract1));
            assertTrue(!contracts.contains(contract2));
            assertTrue(!contracts.contains(contract3));

            //StartAfter
            contracts = contractDAO.listFiltered(contractDAO.startsAfter(LocalDateTime.now().minusMonths(4)));
            assertTrue(!contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(!contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.startsAfter(LocalDateTime.now().minusMonths(6)));
            assertTrue(!contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.startsAfter(LocalDateTime.now().minusMonths(11)));
            assertTrue(contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));

            //EndsAfter
            contracts = contractDAO.listFiltered(contractDAO.endsAfter(LocalDateTime.now()));
            assertTrue(contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.endsAfter(LocalDateTime.now().minusMonths(4)));
            assertTrue(contracts.contains(contract1));
            assertTrue(!contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.endsAfter(LocalDateTime.now().minusMonths(6)));
            assertTrue(contracts.contains(contract1));
            assertTrue(!contracts.contains(contract2));
            assertTrue(!contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.endsAfter(LocalDateTime.now().minusMonths(11)));
            assertTrue(!contracts.contains(contract1));
            assertTrue(!contracts.contains(contract2));
            assertTrue(!contracts.contains(contract3));

            //endsBefore
            contracts = contractDAO.listFiltered(contractDAO.endsBefore(LocalDateTime.now().minusMonths(4)));
            assertTrue(!contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(!contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.endsBefore(LocalDateTime.now().minusMonths(6)));
            assertTrue(!contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.endsBefore(LocalDateTime.now().minusMonths(11)));
            assertTrue(contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));
        }
    }

    @Test
    public void create() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void remove() throws Exception {

    }

}