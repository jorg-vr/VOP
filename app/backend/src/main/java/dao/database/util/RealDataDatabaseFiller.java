package dao.database.util;

import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import dao.database.ProductionProvider;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import dao.interfaces.DAOProvider;
import dao.interfaces.VehicleTypeDAO;
import model.account.*;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.*;
import model.insurance.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static model.insurance.SuretyType.*;

/**
 * Created by sam on 5/12/17.
 */
public class RealDataDatabaseFiller {

    private static final String VEHICLETYPE_1 = "Personenwagen";
    private static final String VEHICLETYPE_2 = "Vrachtauto";
    private static final String VEHICLETYPE_3 = "Vrachtauto (+12)";
    private static final String VEHICLETYPE_4 = "Lichte vrachtwagen";


    public static void main(String[] args) throws DataAccessException {
        ProductionProvider.initializeProvider("production");
        try (DAOProvider provider = ProductionProvider.getInstance()) {
            RealDataDatabaseFiller filler = new RealDataDatabaseFiller();
            filler.initVehicleTypes(provider);
            UUID admin = filler.initUsers(provider);
            filler.initVehicleInsurances(provider, admin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initVehicleTypes(DAOProvider provider) {

        try (DAOManager manager = provider.getDaoManager()) {
            VehicleTypeDAO dao = manager.getVehicleTypeDAO();
            Map<SuretyType, Double> taxes1 = new HashMap<>();
            taxes1.put(CIVIL_LIABILITY, 0.2710);
            taxes1.put(OMNIUM_FULL, 0.2675);
            taxes1.put(OMNIUM_PARTIAL, 0.2675);
            taxes1.put(LEGAL_AID, 0.1675);
            taxes1.put(TRAVEL_AID, 0.1675);
            taxes1.put(SAFETY, 0.1675);
            Map<SuretyType, Double> commissions1 = new HashMap<>();
            commissions1.put(CIVIL_LIABILITY, 0.170);
            commissions1.put(OMNIUM_FULL, 0.190);
            commissions1.put(OMNIUM_PARTIAL, 0.190);
            commissions1.put(LEGAL_AID, 0.250);
            commissions1.put(TRAVEL_AID, 0.250);
            commissions1.put(SAFETY, 0.190);
            VehicleType vehicleType = new VehicleType();
            vehicleType.setType(VEHICLETYPE_1);
            vehicleType.setCommissions(commissions1);
            vehicleType.setTaxes(taxes1);
            dao.create(vehicleType);

            Map<SuretyType, Double> taxes2 = new HashMap<>();
            taxes2.put(CIVIL_LIABILITY, 0.1425);
            taxes2.put(OMNIUM_FULL, 0.139);
            taxes2.put(OMNIUM_PARTIAL, 0.139);
            taxes2.put(LEGAL_AID, 0.1675);
            taxes2.put(TRAVEL_AID, 0.1675);
            taxes2.put(SAFETY, 0.1675);
            Map<SuretyType, Double> commissions2 = new HashMap<>();
            commissions2.put(CIVIL_LIABILITY, 0.170);
            commissions2.put(OMNIUM_FULL, 0.190);
            commissions2.put(OMNIUM_PARTIAL, 0.190);
            commissions2.put(LEGAL_AID, 0.250);
            commissions2.put(TRAVEL_AID, 0.250);
            commissions2.put(SAFETY, 0.190);
            vehicleType = new VehicleType();
            vehicleType.setType(VEHICLETYPE_2);
            vehicleType.setCommissions(commissions2);
            vehicleType.setTaxes(taxes2);
            dao.create(vehicleType);

            Map<SuretyType, Double> taxes3 = new HashMap<>();
            taxes3.put(CIVIL_LIABILITY, 0.1285);
            taxes3.put(OMNIUM_FULL, 0.1285);
            taxes3.put(OMNIUM_PARTIAL, 0.1285);
            taxes3.put(LEGAL_AID, 0.1675);
            taxes3.put(TRAVEL_AID, 0.1675);
            taxes3.put(SAFETY, 0.1675);
            Map<SuretyType, Double> commissions3 = new HashMap<>();
            commissions3.put(CIVIL_LIABILITY, 0.170);
            commissions3.put(OMNIUM_FULL, 0.190);
            commissions3.put(OMNIUM_PARTIAL, 0.190);
            commissions3.put(LEGAL_AID, 0.250);
            commissions3.put(TRAVEL_AID, 0.250);
            commissions3.put(SAFETY, 0.190);
            vehicleType = new VehicleType();
            vehicleType.setType(VEHICLETYPE_3);
            vehicleType.setCommissions(commissions3);
            vehicleType.setTaxes(taxes3);
            dao.create(vehicleType);

            Map<SuretyType, Double> taxes4 = new HashMap<>();
            taxes4.put(CIVIL_LIABILITY, 0.221);
            taxes4.put(OMNIUM_FULL, 0.2175);
            taxes4.put(OMNIUM_PARTIAL, 0.2175);
            taxes4.put(LEGAL_AID, 0.1675);
            taxes4.put(TRAVEL_AID, 0.1675);
            taxes4.put(SAFETY, 0.1675);
            Map<SuretyType, Double> commissions4 = new HashMap<>();
            commissions4.put(CIVIL_LIABILITY, 0.170);
            commissions4.put(OMNIUM_FULL, 0.190);
            commissions4.put(OMNIUM_PARTIAL, 0.190);
            commissions4.put(LEGAL_AID, 0.250);
            commissions4.put(TRAVEL_AID, 0.250);
            commissions4.put(SAFETY, 0.190);
            vehicleType = new VehicleType();
            vehicleType.setType(VEHICLETYPE_4);
            vehicleType.setCommissions(commissions4);
            vehicleType.setTaxes(taxes4);
            dao.create(vehicleType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private UUID initUsers(DAOProvider provider) throws ObjectNotFoundException, DataAccessException, UnAuthorizedException, ConstraintViolationException {

        //Create possible roles
        Role adminRole = adminRole();
        Role productionRole = productionRole();
        Role customerRole = customerRole();
        Role insuranceRole = insuranceRole();

        //Create admin Account
        Address address = createAddress("Kerkstraat", "1", "Zomergem", "9930", "België");
        Company company = createCompany(CompanyType.SOLVAS, "093725663", "Solvas", address);

        User user = createUser("Patrick", "Oostvogels", "patrick.oostvogels@solvas.be", "1h8xE660mn");
        Function adminFunction = createFunction(company, user, LocalDateTime.now().minusMonths(8), LocalDateTime.now().plusMonths(8), "Admin",
                adminRole);
        Function productionFunction = createFunction(company, user, LocalDateTime.now().minusMonths(8), LocalDateTime.now().plusMonths(8), "Productiebeheerder",
                productionRole);
        Function customerFunction = createFunction(company, user, LocalDateTime.now().minusMonths(8), LocalDateTime.now().plusMonths(8), "Klant",
                customerRole);
        Function insuranceFunction = createFunction(company, user, LocalDateTime.now().minusMonths(8), LocalDateTime.now().plusMonths(8), "Verzekeringsmakelaar",
                insuranceRole);
        try (DAOManager manager = provider.getDaoManager()) {
            manager.getUserDAO().create(user);
            manager.getRoleDAO().create(adminRole);
            manager.getRoleDAO().create(productionRole);
            manager.getRoleDAO().create(customerRole);
            manager.getRoleDAO().create(insuranceRole);
            manager.getCompanyDAO().create(company);
            manager.getFunctionDAO().create(adminFunction);
            manager.getFunctionDAO().create(productionFunction);
            manager.getFunctionDAO().create(customerFunction);
            manager.getFunctionDAO().create(insuranceFunction);

            createInsuranceAccount1(company, insuranceRole, manager);
            createInsuranceAccount2(company, insuranceRole, manager);
            createInsuranceAccount3(company, insuranceRole, manager);
            createInsuranceAccount4(company, insuranceRole, manager);
        }
        InsuranceCompany axa = insuranceCompany1(user, adminFunction);
        InsuranceCompany ethias = insuranceCompany2(user, adminFunction);

        Collection<SpecialCondition> specialConditions = initSpecialConditions(user, adminFunction);

        Collection<Surety> suretiesAxa = initSuretiesAxa(user, adminFunction, specialConditions, axa);
        Collection<Surety> suretiesEthias = initSuretiesEthias(user, adminFunction, specialConditions, ethias);

        Customer sam = null;
        Customer jorg = null;
        Customer billie = null;
        try (DAOManager manager = provider.getDaoManager()) {
            sam = customerSam(customerRole, manager, user, adminFunction);
            jorg = customerJorg(customerRole, manager, user, adminFunction);
            billie = customerBillie(customerRole, manager, user, adminFunction);
        }
        Contract jorgContract = initContract(user, adminFunction, jorg, axa);
        Contract samContract = initContract(user, adminFunction, sam, axa);
        Contract billieContract = initContract(user, adminFunction, billie, ethias);

        return adminFunction.getUuid();

    }

    private void initVehicleInsurances(DAOProvider provider, UUID admin) throws DataAccessException, ObjectNotFoundException, ConstraintViolationException, UnAuthorizedException {
        try (DAOManager manager = provider.getDaoManager()) {
            User user = manager.getUserDAO().getUserByLogin("patrick.oostvogels@solvas.be");
            Function function = manager.getFunctionDAO().get(admin);
            for (Customer customer : manager.getCustomerDAO().listFiltered()) {
                for (Contract contract : customer.getContracts()) {
                    for (Fleet fleet : customer.getFleets()) {
                        for (Vehicle vehicle : fleet.getVehicles()) {
                            for(Surety surety: getRandomSureties(contract.getCompany().getSureties())) {
                                initVehicleInsurance(user, function, contract, surety, vehicle);
                            }
                        }
                    }
                }
            }
        }
        try(DAOManager manager = provider.getDaoManager()){
            User user = manager.getUserDAO().getUserByLogin("patrick.oostvogels@solvas.be");
            Function function = manager.getFunctionDAO().get(admin);
            try(ControllerManager controllerManager = new ControllerManager(user.getUuid(),function.getUuid())){
                for(Customer customer: manager.getCustomerDAO().listFiltered()) {
                    controllerManager.getInvoiceController().endStatement(customer);
                }
            }
        }
    }

    private Collection<Surety> getRandomSureties(Collection<Surety> collection) {
        Random rnd = new Random();
        Collection<Surety> sureties = new ArrayList<>();
        int number = 1+rnd.nextInt(collection.size());
        Surety[] array =  collection.toArray(new Surety[collection.size()]);
        for(int i =0;i<number;i++){
            int index = rnd.nextInt(collection.size());
            while(sureties.contains(array[index])){
                index = rnd.nextInt(collection.size());
            }
            sureties.add(array[index]);
        }
        return sureties;
    }

    private void initVehicleInsurance(User user, Function function, Contract contract, Surety surety, Vehicle vehicle) throws DataAccessException, UnAuthorizedException, ConstraintViolationException, ObjectNotFoundException {
        try (ControllerManager controllerManager = new ControllerManager(user.getUuid(), function.getUuid())) {
            int franchiseMinimum = 10000;
            int franchiseMaximum = 100000;
            int insuredMinimum = 200000;
            int insuredMaximum = 1000000;
            VehicleInsurance insurance = new VehicleInsurance();
            insurance.setContract(contract);
            insurance.setSurety(surety);
            insurance.setFranchise(new Random().nextInt(franchiseMaximum - franchiseMinimum) + franchiseMinimum);
            insurance.setInsuredValue(new Random().nextInt(insuredMaximum - insuredMinimum) + insuredMinimum);
            insurance.setVehicle(vehicle);
            insurance.setStartDate(LocalDateTime.now().minusMonths(10));
            insurance.setEndDate(LocalDateTime.now().plusMonths(10));
            controllerManager.getVehicleInsuranceController().create(insurance);
        }
    }

    private Contract initContract(User user, Function function, Customer customer, InsuranceCompany company) throws DataAccessException, UnAuthorizedException, ConstraintViolationException {
        try (ControllerManager controllerManager = new ControllerManager(user.getUuid(), function.getUuid())) {
            Contract contract = new Contract();
            contract.setCustomer(customer);
            contract.setCompany(company);
            contract.setStartDate(LocalDateTime.now());
            contract.setEndDate(LocalDateTime.now().plusMonths(10));
            controllerManager.getContractController().create(contract);
            return contract;
        }
    }


    private Collection<Surety> initSuretiesAxa(User user, Function function, Collection<SpecialCondition> specialConditions, InsuranceCompany insuranceCompany)
            throws DataAccessException, UnAuthorizedException, ConstraintViolationException {
        try (ControllerManager controllerManager = new ControllerManager(user.getUuid(), function.getUuid())) {
            List<Surety> sureties = new ArrayList<>();

            FlatSurety flatSurety1 = new FlatSurety();
            flatSurety1.setPremium(2500);
            flatSurety1.setSuretyType(SuretyType.OMNIUM_FULL);
            flatSurety1.setSpecialConditions(specialConditions);
            flatSurety1.setInsuranceCompany(insuranceCompany);
            sureties.add(controllerManager.getSuretyController().create(flatSurety1));

            FlatSurety flatSurety2 = new FlatSurety();
            flatSurety2.setPremium(1400);
            flatSurety2.setSuretyType(SuretyType.LEGAL_AID);
            flatSurety2.setSpecialConditions(new ArrayList<>(Arrays.asList(new SpecialCondition[]{specialConditions.iterator().next()})));
            flatSurety2.setInsuranceCompany(insuranceCompany);
            sureties.add(controllerManager.getSuretyController().create(flatSurety2));

            NonFlatSurety flatSurety3 = new NonFlatSurety();
            flatSurety3.setMinPremium(3000);
            flatSurety3.setPremiumPercentage(0.165);
            flatSurety3.setSuretyType(SuretyType.OMNIUM_PARTIAL);
            flatSurety3.setSpecialConditions(specialConditions);
            flatSurety3.setInsuranceCompany(insuranceCompany);
            sureties.add(controllerManager.getSuretyController().create(flatSurety3));
            return sureties;
        }
    }

    private Collection<Surety> initSuretiesEthias(User user, Function function, Collection<SpecialCondition> specialConditions, InsuranceCompany insuranceCompany)
            throws DataAccessException, UnAuthorizedException, ConstraintViolationException {
        try (ControllerManager controllerManager = new ControllerManager(user.getUuid(), function.getUuid())) {
            List<Surety> sureties = new ArrayList<>();

            FlatSurety flatSurety1 = new FlatSurety();
            flatSurety1.setPremium(14000);
            flatSurety1.setSuretyType(SuretyType.CIVIL_LIABILITY);
            flatSurety1.setSpecialConditions(specialConditions);
            flatSurety1.setInsuranceCompany(insuranceCompany);
            sureties.add(controllerManager.getSuretyController().create(flatSurety1));

            NonFlatSurety flatSurety2 = new NonFlatSurety();
            flatSurety2.setPremiumPercentage(0.25);
            flatSurety2.setMinPremium(1400);
            flatSurety2.setSuretyType(SuretyType.OMNIUM_PARTIAL);
            flatSurety2.setSpecialConditions(new ArrayList<>(Arrays.asList(new SpecialCondition[]{specialConditions.iterator().next()})));
            flatSurety2.setInsuranceCompany(insuranceCompany);
            sureties.add(controllerManager.getSuretyController().create(flatSurety2));

            NonFlatSurety flatSurety3 = new NonFlatSurety();
            flatSurety3.setMinPremium(3000);
            flatSurety3.setPremiumPercentage(0.165);
            flatSurety3.setSuretyType(SuretyType.TRAVEL_AID);
            flatSurety3.setSpecialConditions(specialConditions);
            flatSurety3.setInsuranceCompany(insuranceCompany);
            sureties.add(controllerManager.getSuretyController().create(flatSurety3));
            return sureties;
        }
    }

    private Collection<SpecialCondition> initSpecialConditions(User user, Function function) throws DataAccessException, UnAuthorizedException, ConstraintViolationException {
        String[] titles = {"Euromex polisnummer", "Mutatie termijn", "CTA verzekerde waarde"};
        String[] texts = {"Voor de dekking rechtsbijstand geldt het Euromes polisnummer 3020980",
                "Nader wordt aangetekend dat wijzigingen in het risico en/of samenstelling van het wagenpark eens per 01 maand(en) schriftelijk worden vastgelegd en verrekend." +
                        "De verzekeringnemer blijft echter gehouden om iedere wijzigingen in risico of wagenpark ten spoedigste aan de maatschappij te melden.",
        "In tegenstelling tot hetgeen in de Polis is bepaald, bedraagt het verzekerde bedrag voor de dekking CTA, niet 33.00 EUR maar 30.000 EUR."};
        String[] referenceCodes = {"024", "060", "951"};
        List<SpecialCondition> specialConditions = new ArrayList<>();


        try (ControllerManager controllerManager = new ControllerManager(user.getUuid(), function.getUuid())) {
            for (int j = 0; j < titles.length; j++) {
                SpecialCondition specialCondition = new SpecialCondition(titles[j], texts[j], referenceCodes[j]);
                specialConditions.add(specialCondition);
                controllerManager.getSpecialConditionController().create(specialCondition);
            }
        }
        return specialConditions;
    }

    private InsuranceCompany insuranceCompany1(User user, Function function) throws DataAccessException, UnAuthorizedException, ConstraintViolationException {
        try (ControllerManager manager = new ControllerManager(user.getUuid(), function.getUuid())) {
            Address address = new Address("Zwijnaardsesteenweg", "11", "Gent", "9000", "Belgium");
            InsuranceCompany insuranceCompany = new InsuranceCompany();
            insuranceCompany.setAddress(address);
            insuranceCompany.setName("Axa");
            insuranceCompany.setPhoneNumber("0909886543");
            insuranceCompany.setBtwNumber("BE99576430");
            manager.getInsuranceCompanyController().create(insuranceCompany);
            return insuranceCompany;
        }
    }

    private InsuranceCompany insuranceCompany2(User user, Function function) throws DataAccessException, UnAuthorizedException, ConstraintViolationException {
        try (ControllerManager manager = new ControllerManager(user.getUuid(), function.getUuid())) {
            Address address = new Address("Leo de Bethunelaan", "108", "Aalst", "9300", "Belgium");
            InsuranceCompany insuranceCompany = new InsuranceCompany();
            insuranceCompany.setAddress(address);
            insuranceCompany.setName("Ethias");
            insuranceCompany.setPhoneNumber("0486532102");
            insuranceCompany.setBtwNumber("BE1186543");
            manager.getInsuranceCompanyController().create(insuranceCompany);
            return insuranceCompany;
        }
    }

    private void createInsuranceAccount1(Company company, Role insuranceRole, DAOManager manager) throws DataAccessException, ConstraintViolationException {
        User userInsurance = createUser("Hans", "Termont", "hans.termont@solvas.be", "aox897OP");
        Function insuranceFunction = createFunction(company, userInsurance, LocalDateTime.now().minusMonths(8), LocalDateTime.now().plusMonths(8), "Verzekeringsmakelaar",
                insuranceRole);

        manager.getUserDAO().create(userInsurance);
        manager.getFunctionDAO().create(insuranceFunction);
    }

    private void createInsuranceAccount2(Company company, Role insuranceRole, DAOManager manager) throws DataAccessException, ConstraintViolationException {
        User userInsurance = createUser("Elisa", "Van Parys", "elisa.vanparys@solvas.be", "8awOB2M4E");
        Function insuranceFunction = createFunction(company, userInsurance, LocalDateTime.now().minusMonths(8), LocalDateTime.now().plusMonths(8), "Verzekeringsmakelaar",
                insuranceRole);

        manager.getUserDAO().create(userInsurance);
        manager.getFunctionDAO().create(insuranceFunction);
    }

    private void createInsuranceAccount3(Company company, Role insuranceRole, DAOManager manager) throws DataAccessException, ConstraintViolationException {
        User userInsurance = createUser("Nikolas", "Maenhout", "nikolas.maenhout@solvas.be", "5KFKF9QQ02c");
        Function insuranceFunction = createFunction(company, userInsurance, LocalDateTime.now().minusMonths(8), LocalDateTime.now().plusMonths(8), "Verzekeringsmakelaar",
                insuranceRole);

        manager.getUserDAO().create(userInsurance);
        manager.getFunctionDAO().create(insuranceFunction);
    }

    private void createInsuranceAccount4(Company company, Role insuranceRole, DAOManager manager) throws DataAccessException, ConstraintViolationException {
        User userInsurance = createUser("Kathleen", "Bekaert", "kathleen.bekaert@solvas.be", "PcU5Q6Ma");
        Function insuranceFunction = createFunction(company, userInsurance, LocalDateTime.now().minusMonths(8), LocalDateTime.now().plusMonths(8), "Verzekeringsmakelaar",
                insuranceRole);

        manager.getUserDAO().create(userInsurance);
        manager.getFunctionDAO().create(insuranceFunction);
    }

    private Customer customerSam(Role customerRole, DAOManager manager, User user, Function adminFunction) throws DataAccessException, ConstraintViolationException, UnAuthorizedException, ObjectNotFoundException {
        //Create User and Customer Sam
        try (ControllerManager controllerManager = new ControllerManager(user.getUuid(), adminFunction.getUuid())) {
            Address addressSam = createAddress("Linde", "10", "Sint-Jansteen", "4564GG", "Nederland");
            User userSam = createUser("Sam", "Persoon", "persoonsam@gmail.com", "sapersoo5");
            Customer customerSam = createCustomer(addressSam, "Transport De Doncker", "00318877066", "NL778802024");
            Function functionSam = createFunction(customerSam, userSam, LocalDateTime.now().minusMonths(20), LocalDateTime.now().plusMonths(4),
                    "Sam", customerRole);

            manager.getAddressDao().create(addressSam);
            manager.getCustomerDAO().create(customerSam);
            manager.getUserDAO().create(userSam);
            manager.getFunctionDAO().create(functionSam);
            Fleet fleetSam = createFleet("Antwerpen", customerSam, addressSam);
            controllerManager.getFleetController().create(fleetSam);
            customerSam.setFleets(new ArrayList<>(Arrays.asList(new Fleet[]{fleetSam})));
            createVehiclesSam(fleetSam, manager, controllerManager);
            return customerSam;
        }
    }

    private Customer customerJorg(Role customerRole, DAOManager manager, User user, Function adminFunction) throws DataAccessException, UnAuthorizedException, ConstraintViolationException, ObjectNotFoundException {
        //Create User and Customer Sam
        try (ControllerManager controllerManager = new ControllerManager(user.getUuid(), adminFunction.getUuid())) {
            Address addressJorg = createAddress("Hoofdstraat", "125A", "Hansbeke", "4564GG", "België");
            User userJorg = createUser("Jorg", "Van Renterghem", "jorg.vanrenterghem@ugent.be", "louise");
            Customer customerJorg = createCustomer(addressJorg, "Transport Van Renterghem", "09887653", "BE875623545");
            Function functionJorg = createFunction(customerJorg, userJorg, LocalDateTime.now().minusMonths(20), LocalDateTime.now().plusMonths(4),
                    "Jorg", customerRole);

            manager.getAddressDao().create(addressJorg);
            manager.getCustomerDAO().create(customerJorg);
            manager.getUserDAO().create(userJorg);
            manager.getFunctionDAO().create(functionJorg);
            Fleet fleetJorg = createFleet("West Vlaanderen", customerJorg, addressJorg);
            Fleet fleet2Jorg = createFleet("Meetjesland",customerJorg, addressJorg);
            Fleet fleet3Jorg = createFleet("Waasland",customerJorg,addressJorg);

            controllerManager.getFleetController().create(fleetJorg);
            controllerManager.getFleetController().create(fleet2Jorg);
            controllerManager.getFleetController().create(fleet3Jorg);
            customerJorg.setFleets(new ArrayList<>(Arrays.asList(new Fleet[]{fleetJorg,fleet2Jorg,fleet3Jorg})));
            createVehiclesJorg(fleetJorg, manager, controllerManager);
            createVehicles2Jorg(fleet2Jorg,manager,controllerManager);
            return customerJorg;
        }
    }

    private Customer customerBillie(Role customerRole, DAOManager manager, User user, Function adminFunction) throws DataAccessException, UnAuthorizedException, ConstraintViolationException, ObjectNotFoundException {
        //Create User and Customer Sam
        try (ControllerManager controllerManager = new ControllerManager(user.getUuid(), adminFunction.getUuid())) {
            Address addressBillie = createAddress("Gentsesteenweg", "4", "Kortrijk", "8000", "België");
            User userBillie = createUser("Billie", "Devolder", "billie.devolder@gmail.com", "informatica");
            Customer customerBillie = createCustomer(addressBillie, "Transport Devolder", "0032483558043", "BE82434846");
            Function functionBillie = createFunction(customerBillie, userBillie, LocalDateTime.now().minusMonths(20), LocalDateTime.now().plusMonths(4),
                    "Billie", customerRole);

            manager.getAddressDao().create(addressBillie);
            manager.getCustomerDAO().create(customerBillie);
            manager.getUserDAO().create(userBillie);
            manager.getFunctionDAO().create(functionBillie);
            Fleet fleetBillie = createFleet("West Vlaanderen", customerBillie, addressBillie);
            controllerManager.getFleetController().create(fleetBillie);
            customerBillie.setFleets(new ArrayList<>(Arrays.asList(new Fleet[]{fleetBillie})));
            createVehiclesBillie(fleetBillie, manager, controllerManager);
            return customerBillie;
        }
    }

    private void createVehiclesSam(Fleet fleetSam, DAOManager manager, ControllerManager controllerManager) {
        createVehicleToDatabase(fleetSam, "AAAAKAAA78ACAAA9P", 17000, "Audi", "A1", "BE-12-CK", manager, controllerManager, VEHICLETYPE_1);
        createVehicleToDatabase(fleetSam, "WYKMZ4KD7YD0KSJDY", 85000, "Mercedes", "Benz 2", "18-PP-OI", manager, controllerManager, VEHICLETYPE_1);
        createVehicleToDatabase(fleetSam, "9WPM9X0KK3SUMD0T9", 140000, "Fiat", "Punto", "1-842-PMT", manager, controllerManager, VEHICLETYPE_1);
        createVehicleToDatabase(fleetSam, "YH6U5TNZKVLKEGVBJ", 62500, "Opel", "Astra", "9-785-PT", manager, controllerManager, VEHICLETYPE_1);

        createVehicleToDatabase(fleetSam, "UC0RBDZYZF6JCFHY0", 62500, "MAN", "TGX", "KPD-129", manager, controllerManager, VEHICLETYPE_2);
        createVehicleToDatabase(fleetSam, "JSZ63HAZS8LW8F61E", 58200, "CAT", "V2", "9-804-UID", manager, controllerManager, VEHICLETYPE_2);
        createVehicleToDatabase(fleetSam, "BMCRJRJX9XSPYUMES", 110000, "DAF", "XF 510", "HUS-090", manager, controllerManager, VEHICLETYPE_2);
        createVehicleToDatabase(fleetSam, "HEVUCMEZB5TXJKH48", 6000, "DAF", "CF Euro 6", "KAW-481", manager, controllerManager, VEHICLETYPE_2);

        createVehicleToDatabase(fleetSam, "0MRJ7BJPUW58USS19", 14000, "Mercedes", "P2", "560-KIN", manager, controllerManager, VEHICLETYPE_3);
        createVehicleToDatabase(fleetSam, "ZFU2534S79KATB41S", 140000, "MAN", "TGS", "23-81-MT", manager, controllerManager, VEHICLETYPE_3);
        createVehicleToDatabase(fleetSam, "BY1WRA6CBEZVC50V6", 90000, "Scania", "S2", "92-HD-RX", manager, controllerManager, VEHICLETYPE_3);
        createVehicleToDatabase(fleetSam, "ZFWS3N69STTBFZZCD", 78000, "Scania", "R2", "95-LA-AS", manager, controllerManager, VEHICLETYPE_3);

        createVehicleToDatabase(fleetSam, "W5P111LHZXW6UGUV5", 6300, "Range Rover", "C7", "121- DKK", manager, controllerManager, VEHICLETYPE_4);
        createVehicleToDatabase(fleetSam, "0ALGKKT4A5BB1Z8TZ", 54000, "MAN", "TGM", "UTA-789", manager, controllerManager, VEHICLETYPE_4);
        createVehicleToDatabase(fleetSam, "1U20YYDM0DHU9JM7E", 73000, "Opel", "Astra", "LKO-186", manager, controllerManager, VEHICLETYPE_4);
        createVehicleToDatabase(fleetSam, "AZXN1V8VDL0CF9WD3", 31000, "Fiat", "Panda", "1-POL-553", manager, controllerManager, VEHICLETYPE_4);
    }

    private void createVehiclesJorg(Fleet fleetJorg, DAOManager manager, ControllerManager controllerManager) {
        createVehicleToDatabase(fleetJorg, "RDDRSG2USGRZHT3GK", 7400, "Mercedes", "Break C", "19-KK-OK", manager, controllerManager, VEHICLETYPE_1);
        createVehicleToDatabase(fleetJorg, "6N8K7YVMKPT9000NV", 87000, "Audi", "A5", "BEE-861", manager, controllerManager, VEHICLETYPE_1);

        createVehicleToDatabase(fleetJorg, "WHR3B86S8W934TZ7U", 96000, "MAN", "TGS", "LOP-090", manager, controllerManager, VEHICLETYPE_2);
        createVehicleToDatabase(fleetJorg, "5JPUNZCVSF62VFM6Z", 10000, "DAF", "XF 610", "HSA-444", manager, controllerManager, VEHICLETYPE_2);
        createVehicleToDatabase(fleetJorg, "DWBYG9KH8NRAZHGP3", 3300, "DAF", "CK Euro 6", "JUX-1-PP", manager, controllerManager, VEHICLETYPE_2);
    }

    private void createVehicles2Jorg(Fleet fleetJorg, DAOManager manager, ControllerManager controllerManager) {
        createVehicleToDatabase(fleetJorg, "WWDRSG2USGRZHT3GK", 64000, "Fiat", "Punto", "1-KKK-OK", manager, controllerManager, VEHICLETYPE_1);
        createVehicleToDatabase(fleetJorg, "6LPK7YVMKPT9000NV", 8700, "Audi", "A1", "1-BNA-861", manager, controllerManager, VEHICLETYPE_1);

        createVehicleToDatabase(fleetJorg, "WHR3B86S8W934MVZ7", 9600, "MAN", "TGS", "LKF-191", manager, controllerManager, VEHICLETYPE_2);

        createVehicleToDatabase(fleetJorg, "5JPUNZCVSF62LFM6Z", 14000, "DAF", "XF 610", "HBQ-010", manager, controllerManager, VEHICLETYPE_3);

        createVehicleToDatabase(fleetJorg, "DWBYG9KH66NRAZHG3", 59000, "DAF", "CK Euro 6", "JKK-1PP", manager, controllerManager, VEHICLETYPE_4);
    }


    private void createVehiclesBillie(Fleet fleetSam, DAOManager manager, ControllerManager controllerManager) {
        createVehicleToDatabase(fleetSam, "W6846L45MWJ60R99V", 17000, "Renault", "Clio", "18-12-C1", manager, controllerManager, VEHICLETYPE_1);
        createVehicleToDatabase(fleetSam, "EJE8E4771VL5GF2EU", 85000, "Citroën", "C6", "80-PP-OI", manager, controllerManager, VEHICLETYPE_1);

        createVehicleToDatabase(fleetSam, "CCJ7KTWGUKVK51S8L", 62500, "MAN", "TGX", "KPD-820", manager, controllerManager, VEHICLETYPE_2);
        createVehicleToDatabase(fleetSam, "8VTKLPKPM71P0N7GK", 58200, "CAT", "V2", "9-874-UID", manager, controllerManager, VEHICLETYPE_2);
        createVehicleToDatabase(fleetSam, "2AK3PXZ4WFGUUH97J", 110000, "DAF", "LG 710", "HUO-090", manager, controllerManager, VEHICLETYPE_2);
        createVehicleToDatabase(fleetSam, "JNT5XWVTT6JMU5VGC", 6000, "DAF", "CF Euro 8", "KKW-481", manager, controllerManager, VEHICLETYPE_2);

        createVehicleToDatabase(fleetSam, "GX1PJDHJGNMHKLTF7", 14000, "DAF", "LG 910", "560-KMN", manager, controllerManager, VEHICLETYPE_3);

        createVehicleToDatabase(fleetSam, "W7L5990YWTLHCBAE1", 54000, "MAN", "TGM", "UTA-729", manager, controllerManager, VEHICLETYPE_4);
    }


    private Customer createCustomer(Address address, String name, String phoneNumber, String vatNumber) {
        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setName(name);
        customer.setPhoneNumber(phoneNumber);
        customer.setBtwNumber(vatNumber);
        customer.setFacturationPeriod(Periodicity.MONTHLY);
        customer.setPaymentPeriod(Periodicity.YEARLY);
        return customer;
    }


    private Function createFunction(Company company, User user, LocalDateTime startDate, LocalDateTime endDate, String name, Role role) {
        Function function = new Function();
        function.setCompany(company);
        function.setUser(user);
        function.setStartDate(startDate);
        function.setEndDate(endDate);
        function.setName(name);
        function.setRole(role);
        return function;
    }

    private Fleet createFleet(String name, Customer owner, Address address) {
        Fleet fleet = new Fleet();
        fleet.setName(name);
        fleet.setOwner(owner);
        fleet.setAddress(address);
        return fleet;
    }

    private User createUser(String firstName, String lastName, String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setNotHashedPassword(password);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        return user;
    }

    private Address createAddress(String street, String number, String town, String postalcode, String country) {
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
            for (VehicleType vehicleType : vehicleTypeDAO.listFiltered()) {
                if (vehicleType.getType().equals(type)) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setFleet(fleet);
                    vehicle.setVin(vin);
                    vehicle.setMileage(mileage);
                    vehicle.setBrand(brand);
                    vehicle.setLicensePlate(license);
                    vehicle.setType(vehicleType);
                    vehicle.setModel(model);
                    vehicle.setYear(LocalDate.of(2017 - new Random().nextInt(10), 1, 1));
                    controllerManager.getVehicleController().create(vehicle);
                    fleet.getVehicles().add(vehicle);
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

    private Role productionRole() {
        Role role = new Role();
        role.setName("Productiebeheerder");
        for (Resource resource : Resource.values()) {
            if (!resource.equals(Resource.USER) && !resource.equals(Resource.VEHICLETYPE)&& !resource.equals(Resource.ROLE)) {
                role.setAccess(resource, Action.CREATE_ALL);
                role.setAccess(resource, Action.READ_ALL);
                role.setAccess(resource, Action.REMOVE_ALL);
                role.setAccess(resource, Action.UPDATE_ALL);
            }
        }
        role.setAccess(Resource.VEHICLETYPE, Action.READ_ALL);
        role.setAccess(Resource.USER, Action.READ_MINE);
        role.setAccess(Resource.ROLE,Action.READ_MINE);
        return role;
    }

    private Role customerRole() {
        Role role = new Role();
        role.setName("Klant");
        for (Resource resource : Resource.values()) {
            if (!(resource.equals(Resource.LOG) || resource.equals(Resource.USER) ||
                    resource.equals(Resource.COMMISSION) || resource.equals(Resource.FUNCTION))) {
                role.setAccess(resource, Action.READ_MINE);
            }
        }
        role.setAccess(Resource.VEHICLETYPE, Action.READ_ALL);
        role.setAccess(Resource.USER, Action.READ_MINE);
        role.setAccess(Resource.ROLE, Action.READ_MINE);
        role.setAccess(Resource.FUNCTION, Action.READ_MINE);
        return role;
    }

    private Role insuranceRole() {
        Role role = new Role();
        role.setName("Verzekeringsmakelaar");
        for (Resource resource : Resource.values()) {
            if (!(resource.equals(Resource.LOG) || resource.equals(Resource.USER)||resource.equals(Resource.ROLE))) {
                role.setAccess(resource, Action.READ_ALL);
                role.setAccess(resource, Action.CREATE_ALL);
                role.setAccess(resource, Action.REMOVE_ALL);
                role.setAccess(resource, Action.UPDATE_ALL);
            }
        }
        role.setAccess(Resource.USER, Action.READ_MINE);
        role.setAccess(Resource.ROLE, Action.READ_MINE);
        return role;
    }


}
