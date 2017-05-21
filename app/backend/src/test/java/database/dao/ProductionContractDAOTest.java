package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.ContractDAO;
import dao.interfaces.DAOManager;
import model.identity.Address;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.Contract;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
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

            contracts = contractDAO.listFiltered(contractDAO.endsAfter(LocalDateTime.now().plusMonths(4)));
            assertTrue(contracts.contains(contract1));
            assertTrue(!contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.endsAfter(LocalDateTime.now().plusMonths(6)));
            assertTrue(contracts.contains(contract1));
            assertTrue(!contracts.contains(contract2));
            assertTrue(!contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.endsAfter(LocalDateTime.now().plusMonths(11)));
            assertTrue(!contracts.contains(contract1));
            assertTrue(!contracts.contains(contract2));
            assertTrue(!contracts.contains(contract3));

            //endsBefore
            contracts = contractDAO.listFiltered(contractDAO.endsBefore(LocalDateTime.now().plusMonths(4)));
            assertTrue(!contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(!contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.endsBefore(LocalDateTime.now().plusMonths(6)));
            assertTrue(!contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));

            contracts = contractDAO.listFiltered(contractDAO.endsBefore(LocalDateTime.now().plusMonths(11)));
            assertTrue(contracts.contains(contract1));
            assertTrue(contracts.contains(contract2));
            assertTrue(contracts.contains(contract3));
        }
    }


    @Test
    public void update() throws Exception {
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
        }

        Contract contract4;
        Contract contract5;
        Contract contract6;
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            contract4 = manager.getContractDao().get(contract1.getUuid());
            contract5 = manager.getContractDao().get(contract2.getUuid());
            contract6 = manager.getContractDao().get(contract3.getUuid());
            contract4.setCompany(company2);
            contract4.setCustomer(customer2);

            contract5.setCustomer(customer2);
            contract5.setCompany(company1);

            contract6.setStartDate(LocalDateTime.of(2017, 1, 1, 0, 0));
            contract6.setEndDate(LocalDateTime.of(2017, 1, 1, 0, 0));
        }

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getContractDao().update(contract4);
            manager.getContractDao().update(contract5);
            manager.getContractDao().update(contract6);
        }

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Contract updated1 = manager.getContractDao().get(contract1.getUuid());
            Contract updated2 = manager.getContractDao().get(contract2.getUuid());
            Contract updated3 = manager.getContractDao().get(contract3.getUuid());

            assertTrue(updated1.getCompany().equals(company2));
            assertTrue(updated1.getCustomer().equals(customer2));

            assertTrue(updated2.getCustomer().equals(customer2));
            assertTrue(updated2.getCompany().equals(company1));

            assertTrue(updated3.getStartDate().equals(LocalDateTime.of(2017, 1, 1, 0, 0)));
            assertTrue(updated3.getEndDate().equals(LocalDateTime.of(2017, 1, 1, 0, 0)));
        }
    }

    @Test
    public void remove() throws Exception {
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

        Address address1 = new Address();

        address.setCountry("Testland");
        address.setPostalCode("9876");
        address.setStreet("Teststraat");
        address.setTown("Testdorp");
        address.setStreetNumber("4T");

        Address address2 = new Address();

        address.setCountry("Testland");
        address.setPostalCode("9876");
        address.setStreet("Teststraat");
        address.setTown("Testdorp");
        address.setStreetNumber("4T");

        Address address3 = new Address();

        address.setCountry("Testland");
        address.setPostalCode("9876");
        address.setStreet("Teststraat");
        address.setTown("Testdorp");
        address.setStreetNumber("4T");

        customer1.setName("Klant 1");
        customer1.setAddress(address);
        customer2.setName("Klant 2");
        customer2.setAddress(address1);

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
        company1.setAddress(address2);
        company2.setName("Company 2");
        company2.setAddress(address3);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getCustomerDAO().create(customer1);
            manager.getCustomerDAO().create(customer2);
            manager.getInsuranceCompanyDao().create(company1);
            manager.getInsuranceCompanyDao().create(company2);
            ContractDAO contractDAO = manager.getContractDao();
            contractDAO.create(contract1);
            contractDAO.create(contract2);
            contractDAO.create(contract3);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getContractDao().remove(contract3.getUuid());
            manager.getContractDao().remove(contract2.getUuid());
            manager.getContractDao().remove(contract1.getUuid());
            manager.getInsuranceCompanyDao().remove(company2.getUuid());
            manager.getInsuranceCompanyDao().remove(company1.getUuid());
            manager.getCustomerDAO().remove(customer2.getUuid());
            manager.getCustomerDAO().remove(customer1.getUuid());
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            try {
                manager.getAddressDao().get(address.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getAddressDao().get(address1.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getAddressDao().get(address2.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getAddressDao().get(address3.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }


            try {
                manager.getCustomerDAO().get(customer1.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }

            try {
                manager.getCustomerDAO().get(customer2.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }

            try {
                manager.getInsuranceCompanyDao().get(company1.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }

            try {
                manager.getInsuranceCompanyDao().get(company2.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }

            try {
                manager.getContractDao().get(contract1.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }

            try {
                manager.getContractDao().get(contract2.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }

            try {
                manager.getContractDao().get(contract3.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
        }
    }


}