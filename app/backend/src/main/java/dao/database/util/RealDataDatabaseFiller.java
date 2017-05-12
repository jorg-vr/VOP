package dao.database.util;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.DAOProvider;
import model.account.Function;
import model.account.Role;
import model.account.User;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Company;
import model.identity.CompanyType;
import model.identity.Customer;
import model.insurance.Surety;
import model.insurance.SuretyType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

/**
 * Created by sam on 5/12/17.
 */
public class RealDataDatabaseFiller {
    public static void main(String[] args) throws DataAccessException {

        ProductionProvider.initializeProvider("localtest");
        try (DAOProvider provider = ProductionProvider.getInstance();
             ) {
            RealDataDatabaseFiller filler = new RealDataDatabaseFiller();
            filler.initUsers(provider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUsers(DAOProvider provider) {
        try(DAOManager manager = provider.getDaoManager()){

            Address address = createAddress("Kerkstraat","1","Zomergem","9930","België");
            Company company = createCompany(CompanyType.CUSTOMER,"093725663","Solvas", address );

            Function function = new Function();
            function.setCompany(company);

            User user = createUser("Patrick","Eastbirds", "patrick.eastbirds@solvas.be","1h8xE660mn");

        }

    }

    private Function createFunction(Company company, User user, LocalDateTime startDate, LocalDateTime endDate, String name, Role role){
        Function function = new Function();
        function.setCompany(company);
        function.setUser(user);
        function.setStartDate(startDate);
        function.setEndDate(endDate);
        function.setName(name);
        function.setRole(role);
        return function;
    }

    private VehicleType createVehicleType(String type, Map<SuretyType,Double> commissions, Map<SuretyType,Double> taxes){
        VehicleType vehicleType = new VehicleType();
        vehicleType.setType(type);
        vehicleType.setCommissions(commissions);
        vehicleType.setTaxes(taxes);
        return vehicleType;
    }

    private Fleet createFleet(String name, Customer owner, Address address){
        Fleet fleet = new Fleet();
        fleet.setName(name);
        fleet.setOwner(owner);
        fleet.setAddress(address);
        return fleet;
    }

    private User createUser(String firstName, String lastName, String email, String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        return user;
    }

    private Address createAddress(String street, String number, String town, String postalcode, String country){
        Address address = new Address();
        address.setStreet(street);
        address.setStreetNumber(number);
        address.setTown(town);
        address.setCountry(country);
        address.setPostalCode(postalcode);
        return address;
    }

    private Company createCompany(CompanyType type, String phone, String name, Address address) {
        Company company = new Company();
        company.setCompanyType(type);
        company.setPhoneNumber(phone);
        company.setName(name);
        company.setAddress(address);
        return company;
    }

    private Vehicle createVehicle(Fleet fleet, String vin, int mileage, String brand, String model, String license, VehicleType type) {

        Vehicle vehicle = new Vehicle();
        vehicle.setFleet(fleet);
        vehicle.setVin(vin);
        vehicle.setMileage(mileage);
        vehicle.setBrand(brand);
        vehicle.setLicensePlate(license);
        vehicle.setType(type);
        vehicle.setModel(model);
        vehicle.setYear(LocalDate.now());

    }
}
