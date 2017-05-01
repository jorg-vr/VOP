package dao.database.util;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.interfaces.*;
import model.account.*;
import model.billing.Invoice;
import model.billing.InvoiceType;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.*;
import model.insurance.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static model.insurance.SuretyType.*;

/**
 * Created by sam on 4/13/17.
 */
public class DatabaseFiller {
    public static void main(String[] args) throws DataAccessException {

        ProductionProvider.initializeProvider("localtest");
        try (DAOProvider provider = ProductionProvider.getInstance(); DAOManager manager = provider.getDaoManager()) {
            DatabaseFiller filler = new DatabaseFiller();
            filler.initVehicleTypes(manager);
            filler.initAdminRole(manager);
            filler.initCustomerRole(manager);
            filler.initMoreRoles(manager);
            filler.initCompanies(manager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCompanies(DAOManager provider) {
        try {
            CustomerDAO customerDAO = provider.getCustomerDAO();
            AddressDAO addressDAO = provider.getAddressDao();
            InsuranceCompanyDAO insuranceCompanyDAO = provider.getInsuranceCompanyDao();
            FleetDAO fleetDAO = provider.getFleetDAO();
            VehicleDAO vehicleDAO = provider.getVehicleDAO();
            VehicleTypeDAO typeDAO = provider.getVehicleTypeDAO();
            ContractDAO contractDAO = provider.getContractDao();
            SuretyDAO<Surety> suretyDAO = provider.getSuretyDao();
            VehicleInsuranceDAO vehicleInsuranceDAO = provider.getVehicleInsuranceDao();
            CompanyDAO<Company> companyDAO = provider.getCompanyDAO();
            InvoiceDAO invoiceDAO = provider.getInvoiceDao();

            Surety flatSurety = new FlatSurety(100);
            flatSurety.setSuretyType(SuretyType.OMNIUM_FULL);
            suretyDAO.create(flatSurety);

            Company solvas = new Company();
            solvas.setName("Solvas");
            solvas.setCompanyType(CompanyType.CUSTOMER);
            companyDAO.create(solvas);

            for (int i = 1; i < 6; i++) {
                Address address = new Address("Kerkstraat", Integer.toString(i), "Aalter", "9880", "Belgium");
                Customer customer = new Customer();
                customer.setAddress(address);
                customer.setName("Klant " + Integer.toString(i));

                Address address2 = new Address("Schoolstraat", Integer.toString(i) + Integer.toString(i), "Gent", "9000", "Belgium");
                InsuranceCompany insuranceCompany = new InsuranceCompany();
                insuranceCompany.setAddress(address);
                insuranceCompany.setName("Bedrijf " + Integer.toString(i));

                Contract contract = new Contract();
                contract.setCustomer(customer);
                contract.setCompany(insuranceCompany);
                contract.setStartDate(LocalDateTime.now());
                contract.setEndDate(LocalDateTime.now().plusMonths(10));


                addressDAO.create(address);
                customerDAO.create(customer);
                addressDAO.create(address2);
                insuranceCompanyDAO.create(insuranceCompany);
                contractDAO.create(contract);

                Invoice invoice = new Invoice();
                invoice.setContracts(new ArrayList<Contract>(Arrays.asList(new Contract[]{contract})));
                invoice.setBeneficiary(solvas);
                invoice.setPayer(customer);
                invoice.setPaid(false);
                invoice.setStartDate(LocalDateTime.now().minusMonths(1));
                invoice.setEndDate(LocalDateTime.now().plusMonths(1));
                invoice.setType(InvoiceType.BILLING);
                invoiceDAO.create(invoice);

                for (int j = 1; j < 3; j++) {
                    Fleet fleet = new Fleet();
                    fleet.setOwner(customer);
                    fleet.setName("Vloot " + Integer.toString(i) + Integer.toString(j));
                    fleetDAO.create(fleet);
                    for (VehicleType type : typeDAO.listFiltered()) {
                        for (int k = 1; k < 3; k++) {
                            Vehicle vehicle = new Vehicle();
                            vehicle.setFleet(fleet);
                            vehicle.setChassisNumber("AAAAAAAAAAAAAAAAA");
                            vehicle.setMileage(k * 10000);
                            vehicle.setBrand("Merk " + Integer.toString(k));
                            vehicle.setLicensePlate("ABC-00" + Integer.toString(k));
                            vehicle.setType(type);
                            vehicle.setModel("Model " + Integer.toString(k));
                            vehicle.setProductionDate(LocalDate.now());
                            vehicleDAO.create(vehicle);

                            VehicleInsurance insurance = new VehicleInsurance();
                            insurance.setContract(contract);
                            insurance.setSurety(flatSurety);
                            insurance.setFranchise(1000);
                            insurance.setInsuredValue(10000 * j * k);
                            insurance.setVehicle(vehicle);
                            insurance.setStartDate(LocalDateTime.now().minusMonths(10));
                            insurance.setStartDate(LocalDateTime.now().plusMonths(10));
                            vehicleInsuranceDAO.create(insurance);
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMoreRoles(DAOManager provider) {
        try {
            RoleDAO roleDAO = provider.getRoleDAO();
            UserDAO userDAO = provider.getUserDAO();
            FunctionDAO functionDAO = provider.getFunctionDAO();
            AddressDAO addressDAO = provider.getAddressDao();
            CustomerDAO customerDAO = provider.getCustomerDAO();

            User user = new User();
            user.setEmail("admin&insuranceagent@solvas.be");
            user.setPassword("123");
            user.setFirstName("Jan");
            user.setLastName("Janssens");
            user = userDAO.create(user);

            Role role1 = new Role();
            role1.setName("Admin");
            for (Resource resource : Resource.values()) {
                role1.setAccess(resource, Action.CREATE_ALL);
                role1.setAccess(resource, Action.READ_ALL);
                role1.setAccess(resource, Action.REMOVE_ALL);
                role1.setAccess(resource, Action.UPDATE_ALL);
            }

            Role role2 = new Role();
            role2.setName("Verzekeringsmakelaar");
            for (Resource resource : new Resource[]{Resource.VEHICLE, Resource.FLEET, Resource.INSURANCE, Resource.VEHICLETYPE, Resource.COMPANY, Resource.BILLING}) {
                role2.setAccess(resource, Action.CREATE_ALL);
                role2.setAccess(resource, Action.READ_ALL);
                role2.setAccess(resource, Action.REMOVE_ALL);
                role2.setAccess(resource, Action.UPDATE_ALL);
            }
            for (Resource resource : new Resource[]{Resource.USER, Resource.ROLE, Resource.FUNCTION}) {
                role2.setAccess(resource, Action.READ_MINE);
            }

            Function function1 = new Function();
            function1.setUser(user);
            function1.setRole(role1);
            function1.setName("Admin");

            Function function2 = new Function();
            function2.setUser(user);
            function2.setRole(role2);
            function2.setName("Insurance agent");

            Address address = new Address("Hoofdstraat", "13", "Hamme", "8500", "Belgium");
            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setName("Solvas");
            function1.setCompany(customer);
            function2.setCompany(customer);


            userDAO.create(user);
            addressDAO.create(address);
            customerDAO.create(customer);
            roleDAO.create(role1);
            roleDAO.create(role2);
            functionDAO.create(function1);
            functionDAO.create(function2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdminRole(DAOManager provider) {
        try {
            RoleDAO roleDAO = provider.getRoleDAO();
            UserDAO userDAO = provider.getUserDAO();
            FunctionDAO functionDAO = provider.getFunctionDAO();
            AddressDAO addressDAO = provider.getAddressDao();
            CustomerDAO customerDAO = provider.getCustomerDAO();

            User user = new User();
            user.setEmail("admin@solvas.be");
            user.setPassword("123");
            user.setFirstName("Stefaan");
            user.setLastName("Deconicnk");
            user = userDAO.create(user);

            Role role = new Role();
            role.setName("Admin");
            for (Resource resource : Resource.values()) {
                role.setAccess(resource, Action.CREATE_ALL);
                role.setAccess(resource, Action.READ_ALL);
                role.setAccess(resource, Action.REMOVE_ALL);
                role.setAccess(resource, Action.UPDATE_ALL);
            }

            Function function = new Function();
            function.setUser(user);
            function.setRole(role);
            function.setName("Admin");

            Address address = new Address("Steenstraat", "15", "Wachtebeke", "8600", "Belgium");
            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setName("Solvas");
            function.setCompany(customer);


            userDAO.create(user);
            addressDAO.create(address);
            customerDAO.create(customer);
            roleDAO.create(role);
            functionDAO.create(function);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCustomerRole(DAOManager provider) {
        try {
            RoleDAO roleDAO = provider.getRoleDAO();
            UserDAO userDAO = provider.getUserDAO();
            FunctionDAO functionDAO = provider.getFunctionDAO();
            AddressDAO addressDAO = provider.getAddressDao();
            CustomerDAO customerDAO = provider.getCustomerDAO();
            InsuranceCompanyDAO insuranceCompanyDAO = provider.getInsuranceCompanyDao();
            InvoiceDAO invoiceDAO = provider.getInvoiceDao();
            ContractDAO contractDAO = provider.getContractDao();
            VehicleDAO vehicleDAO = provider.getVehicleDAO();
            FleetDAO fleetDAO = provider.getFleetDAO();
            VehicleInsuranceDAO vehicleInsuranceDAO = provider.getVehicleInsuranceDao();
            VehicleTypeDAO vehicleTypeDAO = provider.getVehicleTypeDAO();
            SuretyDAO suretyDAO = provider.getSuretyDao();
            CompanyDAO companyDAO = provider.getCompanyDAO();


            Company solvas = new Company();
            solvas.setName("Solvas");
            solvas.setCompanyType(CompanyType.CUSTOMER);
            companyDAO.create(solvas);

            Surety flatSurety = new FlatSurety(100);
            flatSurety.setSuretyType(SuretyType.OMNIUM_FULL);
            suretyDAO.create(flatSurety);


            User user = new User();
            user.setEmail("klant@solvas.be");
            user.setPassword("123");
            user.setFirstName("Frederik");
            user.setLastName("Vandenvelde");
            user = userDAO.create(user);

            Role role = new Role();
            role.setName("Klant");
            for (Resource resource : new Resource[]{Resource.FLEET, Resource.BILLING, Resource.USER, Resource.ROLE, Resource.FUNCTION, Resource.INSURANCE, Resource.VEHICLE}) {
                role.setAccess(resource, Action.READ_MINE);
            }
            role.setAccess(Resource.VEHICLETYPE, Action.READ_ALL);
            Function function = new Function();
            function.setUser(user);
            function.setRole(role);
            function.setName("Klant");

            Address address = new Address("Beukenlaan", "12", "Oostende", "9850", "Belgium");
            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setName("Solvas-klant");
            function.setCompany(customer);


            userDAO.create(user);
            addressDAO.create(address);
            customerDAO.create(customer);
            roleDAO.create(role);
            functionDAO.create(function);
            Address address2 = new Address("Lindestraat", "54A", "Knokke", "9850", "Belgium");
            InsuranceCompany insuranceCompany = new InsuranceCompany();
            insuranceCompany.setAddress(address);
            insuranceCompany.setName("Bedrijf X");

            Contract contract = new Contract();
            contract.setCustomer(customer);
            contract.setCompany(insuranceCompany);
            contract.setStartDate(LocalDateTime.now());
            contract.setEndDate(LocalDateTime.now().plusMonths(10));


            addressDAO.create(address);
            customerDAO.create(customer);
            addressDAO.create(address2);
            insuranceCompanyDAO.create(insuranceCompany);
            contractDAO.create(contract);

            Invoice invoice = new Invoice();
            invoice.setContracts(new ArrayList<Contract>(Arrays.asList(new Contract[]{contract})));
            invoice.setBeneficiary(solvas);
            invoice.setPayer(customer);
            invoice.setPaid(false);
            invoice.setStartDate(LocalDateTime.now().minusMonths(1));
            invoice.setEndDate(LocalDateTime.now().plusMonths(1));
            invoice.setType(InvoiceType.BILLING);
            invoiceDAO.create(invoice);

            for (int j = 1; j < 3; j++) {
                Fleet fleet = new Fleet();
                fleet.setOwner(customer);
                fleet.setName("Vloot " + Integer.toString(j) + "." + Integer.toString(j));
                fleetDAO.create(fleet);
                for (VehicleType type : vehicleTypeDAO.listFiltered()) {
                    for (int k = 1; k < 3; k++) {
                        Vehicle vehicle = new Vehicle();
                        vehicle.setFleet(fleet);
                        vehicle.setChassisNumber("AAAAAAAAAAAAAAAAA");
                        vehicle.setMileage(k * 10000);
                        vehicle.setBrand("Merk " + Integer.toString(k));
                        vehicle.setLicensePlate("DEF-95" + Integer.toString(k));
                        vehicle.setType(type);
                        vehicle.setModel("Model " + Integer.toString(k));
                        vehicle.setProductionDate(LocalDate.now());
                        vehicleDAO.create(vehicle);

                        VehicleInsurance insurance = new VehicleInsurance();
                        insurance.setContract(contract);
                        insurance.setSurety(flatSurety);
                        insurance.setVehicle(vehicle);
                        insurance.setFranchise(600);
                        insurance.setInsuredValue(k * 20000);
                        insurance.setStartDate(LocalDateTime.now().minusMonths(10));
                        insurance.setStartDate(LocalDateTime.now().plusMonths(10));
                        vehicleInsuranceDAO.create(insurance);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initVehicleTypes(DAOManager provider) {
        Map<SuretyType, Double> taxes = new HashMap<>();
        taxes.put(CIVIL_LIABILITY, 0.2710);
        taxes.put(OMNIUM_FULL, 0.2675);
        taxes.put(OMNIUM_PARTIAL, 0.2675);
        taxes.put(LEGAL_AID, 0.1675);
        taxes.put(TRAVEL_AID, 0.1675);
        taxes.put(SAFETY, 0.1675);
        Map<SuretyType, Double> commissions = new HashMap<>();
        commissions.put(CIVIL_LIABILITY, 0.170);
        commissions.put(OMNIUM_FULL, 0.190);
        commissions.put(OMNIUM_PARTIAL, 0.190);
        commissions.put(LEGAL_AID, 0.250);
        commissions.put(TRAVEL_AID, 0.250);
        commissions.put(SAFETY, 0.190);
        try {
            VehicleTypeDAO dao = provider.getVehicleTypeDAO();
            VehicleType vehicleType = new VehicleType();
            vehicleType.setType("Personenwagen");
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setType("Vrachauto");
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setType("Vrachtauto (+12)");
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setType("Lichte vrachtwagen");
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
