package dao.database.util;

import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import dao.database.ProductionProvider;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.DAOProvider;
import dao.interfaces.VehicleDAO;
import dao.interfaces.VehicleTypeDAO;
import model.account.*;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.*;
import model.insurance.Surety;
import model.insurance.SuretyType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static model.insurance.SuretyType.*;
import static model.insurance.SuretyType.SAFETY;
import static model.insurance.SuretyType.TRAVEL_AID;

/**
 * Created by sam on 5/12/17.
 */
public class RealDataDatabaseFiller {
    private static final String VEHICLETYPE_1 = "Personenwagen";
    private static final String VEHICLETYPE_2 = "Vrachauto";
    private static final String VEHICLETYPE_3 = "Vrachtauto (+12)";
    private static final String VEHICLETYPE_4 = "Lichte vrachtwagen";



    public static void main(String[] args) throws DataAccessException {
        ProductionProvider.initializeProvider("localtest");
        try (DAOProvider provider = ProductionProvider.getInstance()) {
            RealDataDatabaseFiller filler = new RealDataDatabaseFiller();
            filler.initVehicleTypes(provider);
            filler.initUsers(provider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initVehicleTypes(DAOProvider provider) {
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
        try(DAOManager manager = provider.getDaoManager()) {
            VehicleTypeDAO dao = manager.getVehicleTypeDAO();
            VehicleType vehicleType = new VehicleType();
            vehicleType.setType(VEHICLETYPE_1);
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setType(VEHICLETYPE_2);
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setType(VEHICLETYPE_3);
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setType(VEHICLETYPE_4);
            vehicleType.setCommissions(commissions);
            vehicleType.setTaxes(taxes);
            dao.create(vehicleType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUsers(DAOProvider provider) {
        try(DAOManager manager = provider.getDaoManager()){
            //Create possible roles
            Role adminRole = adminRole();
            Role productionRole = productionRole();
            Role customerRole = customerRole();
            Role insuranceRole = insuranceRole();

            //Create admin Account
            Address address = createAddress("Kerkstraat","1","Zomergem","9930","België");
            Company company = createCompany(CompanyType.CUSTOMER,"093725663","Solvas", address );

            User user = createUser("Patrick","Oostvogels", "patrick.oostvogels@solvas.be","1h8xE660mn");
            Function adminFunction = createFunction(company,user,LocalDateTime.now().minusMonths(8),LocalDateTime.now().plusMonths(8),"Admin",
                    adminRole);
            Function productionFunction = createFunction(company,user,LocalDateTime.now().minusMonths(8),LocalDateTime.now().plusMonths(8),"Productiebeheerder",
                    productionRole);

            manager.getUserDAO().create(user);
            manager.getRoleDAO().create(adminRole);
            manager.getRoleDAO().create(productionRole);
            manager.getCompanyDAO().create(company);
            manager.getFunctionDAO().create(adminFunction);
            manager.getFunctionDAO().create(productionFunction);

            //Create InsuranceAgent Account
            User userInsurance = createUser("Patrick","Eastbirds", "patrick.eastbirds@solvas.be","1h8xE660mn");
            Function insuranceFunction = createFunction(company,userInsurance,LocalDateTime.now().minusMonths(8),LocalDateTime.now().plusMonths(8),"Verzekeringsmakelaar",
                    insuranceRole);

            manager.getUserDAO().create(userInsurance);
            manager.getRoleDAO().create(insuranceRole);
            manager.getFunctionDAO().create(insuranceFunction);


            //Create User and Customer Sam
            Address addressSam = createAddress("Linde","10", "Sint-Jansteen","4564GG","Nederland");
            User userSam = createUser("Sam","Persoon","persoonsam@gmail.com","sapersoo5");
            Customer customerSam = createCustomer(addressSam,"Transport De Doncker","00318877066","NL778802024");
            Function functionSam = createFunction(customerSam,userSam,LocalDateTime.now().minusMonths(20),LocalDateTime.now().plusMonths(4),
                    "Sam",customerRole);

            manager.getRoleDAO().create(customerRole);
            manager.getAddressDao().create(addressSam);
            manager.getCustomerDAO().create(customerSam);
            manager.getUserDAO().create(userSam);
            manager.getFunctionDAO().create(functionSam);



            try(ControllerManager controllerManager = new ControllerManager(user.getUuid(),adminFunction.getUuid())){
                Fleet fleetSam = createFleet("Antwerpen",customerSam,addressSam);
                controllerManager.getFleetController().create(fleetSam);
                createVehiclesSam(fleetSam,manager,controllerManager);


            } catch (UnAuthorizedException e) {
                e.printStackTrace();
            }
        } catch (DataAccessException | ConstraintViolationException e) {
            e.printStackTrace();
        }

    }

    private void createVehiclesSam(Fleet fleetSam,DAOManager manager, ControllerManager controllerManager){

        createVehicleToDatabase(fleetSam,"AAAAKAAA78ACAAA9P",17000,"Audi","A1","BE-12-CK",manager,controllerManager,VEHICLETYPE_1);
        createVehicleToDatabase(fleetSam,"WYKMZ4KD7YD0KSJDY",85000,"Mercedes","Benz 2","18-PP-OI",manager,controllerManager,VEHICLETYPE_1);
        createVehicleToDatabase(fleetSam,"9WPM9X0KK3SUMD0T9",140000,"Fiat","Punto","1-842-PMT",manager,controllerManager,VEHICLETYPE_1);
        createVehicleToDatabase(fleetSam,"YH6U5TNZKVLKEGVBJ",62500,"Opel","Astra","9-785-PT",manager,controllerManager,VEHICLETYPE_1);

        createVehicleToDatabase(fleetSam,"UC0RBDZYZF6JCFHY0",62500,"MAN","TGX","KPD-129",manager,controllerManager,VEHICLETYPE_2);
        createVehicleToDatabase(fleetSam,"JSZ63HAZS8LW8F61E",62500,"CAT","V2","9-804-UID",manager,controllerManager,VEHICLETYPE_2);
        createVehicleToDatabase(fleetSam,"BMCRJRJX9XSPYUMES",62500,"DAF","XF 510","HUS-090",manager,controllerManager,VEHICLETYPE_2);
        createVehicleToDatabase(fleetSam,"HEVUCMEZB5TXJKH48",62500,"DAF","CF Euro 6","KAW-481",manager,controllerManager,VEHICLETYPE_2);

        createVehicleToDatabase(fleetSam,"0MRJ7BJPUW58USS19",62500,"Mercedes","P2","560-KIN",manager,controllerManager,VEHICLETYPE_3);
        createVehicleToDatabase(fleetSam,"ZFU2534S79KATB41S",62500,"MAN","TGS","23-81-MT",manager,controllerManager,VEHICLETYPE_3);
        createVehicleToDatabase(fleetSam,"BY1WRA6CBEZVC50V6",62500,"Scania","S","92-HD-RX",manager,controllerManager,VEHICLETYPE_3);
        createVehicleToDatabase(fleetSam,"ZFWS3N69STTBFZZCD",62500,"Scania","R","95-LA-AS",manager,controllerManager,VEHICLETYPE_3);

        createVehicleToDatabase(fleetSam,"W5P111LHZXW6UGUV5",62500,"Range Rover","C7","121- DKK",manager,controllerManager,VEHICLETYPE_4);
        createVehicleToDatabase(fleetSam,"0ALGKKT4A5BB1Z8TZ",62500,"MAN","TGM","UTA-789",manager,controllerManager,VEHICLETYPE_4);
        createVehicleToDatabase(fleetSam,"1U20YYDM0DHU9JM7E",62500,"Opel","Astra","LKO-186",manager,controllerManager,VEHICLETYPE_4);
        createVehicleToDatabase(fleetSam,"AZXN1V8VDL0CF9WD3",62500,"Fiat","Panda","1-POL-553",manager,controllerManager,VEHICLETYPE_4);
    }

    private Customer createCustomer(Address address, String name, String phoneNumber, String vatNumber) {
        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setName(name);
        customer.setPhoneNumber(phoneNumber);
        customer.setBtwNumber(vatNumber);
        customer.setStatementPeriodicity(Periodicity.MONTHLY);
        customer.setInvoicePeriodicity(Periodicity.YEARLY);
        return customer;
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

    private void createVehicleToDatabase(Fleet fleet, String vin, int mileage, String brand, String model, String license, DAOManager manager, ControllerManager controllerManager, String type) {
        VehicleTypeDAO vehicleTypeDAO = manager.getVehicleTypeDAO();

        try {
            for(VehicleType vehicleType: vehicleTypeDAO.listFiltered()){
                if(vehicleType.getType().equals(type)){
                    Vehicle vehicle = new Vehicle();
                    vehicle.setFleet(fleet);
                    vehicle.setVin(vin);
                    vehicle.setMileage(mileage);
                    vehicle.setBrand(brand);
                    vehicle.setLicensePlate(license);
                    vehicle.setType(vehicleType);
                    vehicle.setModel(model);
                    vehicle.setYear(LocalDate.now());
                    controllerManager.getVehicleController().create(vehicle);
                    return;
                }
            }
        } catch (DataAccessException | ConstraintViolationException | UnAuthorizedException e) {
            e.printStackTrace();
        }

    }

    private Role adminRole() {
        Role role = new Role();
        role.setName("Admin");
        for (Resource resource : Resource.values()) {
            role.setAccess(resource, Action.CREATE_ALL);
            role.setAccess(resource, Action.READ_ALL);
            role.setAccess(resource, Action.REMOVE_ALL);
            role.setAccess(resource, Action.UPDATE_ALL);
        }
        return role;
    }

    private Role productionRole(){
        Role role = new Role();
        role.setName("Productiebeheerder");
        for(Resource resource : Resource.values()){
            if(resource.equals(Resource.USER)||resource.equals(Resource.VEHICLETYPE)){
                role.setAccess(resource, Action.CREATE_ALL);
                role.setAccess(resource, Action.READ_ALL);
                role.setAccess(resource, Action.REMOVE_ALL);
                role.setAccess(resource, Action.UPDATE_ALL);
            }
        }
        role.setAccess(Resource.VEHICLETYPE,Action.READ_ALL);
        role.setAccess(Resource.USER,Action.READ_MINE);
        return role;
    }

    private Role customerRole(){
        Role role = new Role();
        role.setName("Klant");
        for (Resource resource: Resource.values()){
            if(!(resource.equals(Resource.LOG)||resource.equals(Resource.USER)||
                    resource.equals(Resource.COMMISSION)||resource.equals(Resource.FUNCTION)||
                    resource.equals(Resource.ROLE))){
                role.setAccess(resource,Action.READ_MINE);
            }
        }
        role.setAccess(Resource.VEHICLETYPE,Action.READ_ALL);
        role.setAccess(Resource.USER,Action.READ_MINE);
        role.setAccess(Resource.ROLE,Action.READ_MINE);
        role.setAccess(Resource.FUNCTION,Action.READ_MINE);
        return role;
    }

    private Role insuranceRole(){
        Role role = new Role();
        role.setName("Verzekeringsmakelaar");
        for (Resource resource: Resource.values()){
            if(!(resource.equals(Resource.LOG)||resource.equals(Resource.USER))){
                role.setAccess(resource,Action.READ_ALL);
                role.setAccess(resource,Action.CREATE_ALL);
                role.setAccess(resource,Action.REMOVE_ALL);
                role.setAccess(resource,Action.UPDATE_ALL);
            }
        }
        role.setAccess(Resource.USER,Action.READ_MINE);
        return role;
    }

}
