package dao.database.util;

import dao.database.ProductionProvider;
import dao.interfaces.*;
import model.account.*;
import model.billing.Invoice;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.Contract;
import model.insurance.SuretyType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static model.insurance.SuretyType.*;

/**
 * Created by sam on 4/13/17.
 */
public class DatabaseFiller {
    public static void main(String[] args) throws DataAccessException {
        DAOProvider provider = null;
        try {

            ProductionProvider.initializeProvider("localtest");
            provider = ProductionProvider.getInstance();
            DatabaseFiller filler = new DatabaseFiller();
            filler.initVehicleTypes(provider);
            filler.initAdminRole(provider);
            filler.initCustomerRole(provider);
            filler.initMoreRoles(provider);
            filler.initCompanies(provider);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            provider.close();
        }
    }

    private void initCompanies(DAOProvider provider) {
        try (CustomerDAO customerDAO = provider.getCustomerDAO();
             AddressDAO addressDAO = provider.getAddressDao();
             InsuranceCompanyDAO insuranceCompanyDAO = provider.getInsuranceCompanyDao();
             FleetDAO fleetDAO = provider.getFleetDAO();
             VehicleDAO vehicleDAO = provider.getVehicleDAO();
             VehicleTypeDAO typeDAO = provider.getVehicleTypeDAO();
             ContractDAO contractDAO = provider.getContractDao()) {
            for (int i = 1; i < 6; i++) {
                Address address = new Address("mystreet", Integer.toString(i), "The town", "9850", "Belgium");
                Customer customer = new Customer();
                customer.setAddress(address);
                customer.setName("Klant " + Integer.toString(i));
                
                Address address2 = new Address("mystreet",Integer.toString(i)+ Integer.toString(i), "The town", "9850", "Belgium");
                InsuranceCompany insuranceCompany = new InsuranceCompany();
                insuranceCompany.setAddress(address);
                insuranceCompany.setName("Verzekeringsmaatschappij " + Integer.toString(i));

                Contract contract = new Contract();
                contract.setCustomer(customer);
                contract.setCompany(insuranceCompany);
                contract.setStartDate(LocalDateTime.now());


                addressDAO.create(address);
                customerDAO.create(customer);
                addressDAO.create(address2);
                insuranceCompanyDAO.create(insuranceCompany);
                contractDAO.create(contract);
                for (int j = 1; j < 3; j++) {
                    Fleet fleet = new Fleet();
                    fleet.setOwner(customer);
                    fleet.setName("Vloot " + Integer.toString(j));
                    fleetDAO.create(fleet);
                    for(VehicleType type : typeDAO.listFiltered()){
                        for (int k = 0; k < 8; k++) {
                            Vehicle vehicle = new Vehicle();
                            vehicle.setFleet(fleet);
                            vehicle.setChassisNumber("AAAAAAAAAAAAAAAAA");
                            vehicle.setMileage(k*10000);
                            vehicle.setBrand("Merk " + Integer.toString(k));
                            vehicle.setLicensePlate("ABC-00"+Integer.toString(k));
                            vehicle.setType(type);
                            vehicle.setModel("Model " + Integer.toString(k));
                            vehicle.setProductionDate(LocalDate.now());
                            vehicleDAO.create(vehicle);
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMoreRoles(DAOProvider provider) {
        try (RoleDAO roleDAO = provider.getRoleDAO();
             UserDAO userDAO = provider.getUserDAO();
             FunctionDAO functionDAO = provider.getFunctionDAO();
             AddressDAO addressDAO = provider.getAddressDao();
             CustomerDAO customerDAO = provider.getCustomerDAO();) {

            User user = new User();
            user.setEmail("admin&insuranceagent@solvas.be");
            user.setPassword("123");
            user.setFirstName("Bill");
            user.setLastName("kill");
            user = userDAO.create(user);

            Role role1 = new Role();
            role1.setName("adminrole");
            for (Resource resource : Resource.values()) {
                role1.setAccess(resource, Action.CREATE_ALL);
                role1.setAccess(resource, Action.READ_ALL);
                role1.setAccess(resource, Action.REMOVE_ALL);
                role1.setAccess(resource, Action.UPDATE_ALL);
            }

            Role role2 = new Role();
            role2.setName("insuranceagentrole");
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
            function1.setName("Adminfunction");

            Function function2 = new Function();
            function2.setUser(user);
            function2.setRole(role1);
            function2.setName("Insurance_agent_function");

            Address address = new Address("mystreet", "13", "The town", "9850", "Belgium");
            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setName("Solvas");
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

    private void initAdminRole(DAOProvider provider) {
        try (RoleDAO roleDAO = provider.getRoleDAO();
             UserDAO userDAO = provider.getUserDAO();
             FunctionDAO functionDAO = provider.getFunctionDAO();
             AddressDAO addressDAO = provider.getAddressDao();
             CustomerDAO customerDAO = provider.getCustomerDAO();) {

            User user = new User();
            user.setEmail("admin@solvas.be");
            user.setPassword("123");
            user.setFirstName("Bill");
            user.setLastName("kill");
            user = userDAO.create(user);

            Role role = new Role();
            role.setName("adminrole");
            for (Resource resource : Resource.values()) {
                role.setAccess(resource, Action.CREATE_ALL);
                role.setAccess(resource, Action.READ_ALL);
                role.setAccess(resource, Action.REMOVE_ALL);
                role.setAccess(resource, Action.UPDATE_ALL);
            }

            Function function = new Function();
            function.setUser(user);
            function.setRole(role);
            function.setName("Adminfunction");

            Address address = new Address("mystreet", "11", "The town", "9850", "Belgium");
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

    private void initCustomerRole(DAOProvider provider) {
        try (RoleDAO roleDAO = provider.getRoleDAO();
             UserDAO userDAO = provider.getUserDAO();
             FunctionDAO functionDAO = provider.getFunctionDAO();
             AddressDAO addressDAO = provider.getAddressDao();
             CustomerDAO customerDAO = provider.getCustomerDAO();) {

            User user = new User();
            user.setEmail("klant@solvas.be");
            user.setPassword("123");
            user.setFirstName("Bill");
            user.setLastName("kill");
            user = userDAO.create(user);

            Role role = new Role();
            role.setName("customerrole");
            for (Resource resource : new Resource[]{Resource.FLEET, Resource.BILLING, Resource.USER, Resource.ROLE, Resource.FUNCTION, Resource.INSURANCE, Resource.VEHICLE}) {
                role.setAccess(resource, Action.READ_MINE);
            }

            Function function = new Function();
            function.setUser(user);
            function.setRole(role);
            function.setName("Customerfunction");

            Address address = new Address("mystreet", "12", "The town", "9850", "Belgium");
            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setName("Solvas-klant");
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

    private void initVehicleTypes(DAOProvider provider) {
        Map<SuretyType,Double> taxes=new HashMap<>();
        taxes.put(CIVIL_LIABILITY,27.10);
        taxes.put(OMNIUM_FULL,26.75);
        taxes.put(OMNIUM_PARTIAL,26.75);
        taxes.put(LEGAL_AID,16.75);
        taxes.put(TRAVEL_AID,16.75);
        taxes.put(SAFETY,16.75);
        Map<SuretyType,Double> commissions=new HashMap<>();
        commissions.put(CIVIL_LIABILITY,17.0);
        commissions.put(OMNIUM_FULL,19.0);
        commissions.put(OMNIUM_PARTIAL,19.0);
        commissions.put(LEGAL_AID,25.0);
        commissions.put(TRAVEL_AID,25.0);
        commissions.put(SAFETY,19.0);
        try (VehicleTypeDAO dao = provider.getVehicleTypeDAO()) {
            VehicleType vehicleType=new VehicleType();
            vehicleType.setType("Personenwagen");
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
            vehicleType=new VehicleType();
            vehicleType.setType("Vrachauto");
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
            vehicleType=new VehicleType();
            vehicleType.setType("Vrachtauto (+12)");
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
            vehicleType=new VehicleType();
            vehicleType.setType("Lichte vrachtwagen");
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
