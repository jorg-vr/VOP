package dao.test;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestVehicleDAO implements VehicleDAO {

    private static Map<UUID, Vehicle> vehicles = new HashMap<>();
    private static DateTimeFormatter yearFormat=DateTimeFormatter.ofPattern("yyyyMMdd").withLocale(Locale.forLanguageTag("NL"));
    static {
        UUID one = UUID.randomUUID();
        UUID two = UUID.randomUUID();
        UUID three = UUID.randomUUID();
        UUID four = UUID.randomUUID();
        UUID five = UUID.randomUUID();
        VehicleType type1 = new VehicleType(three, "AE - Cabriolet", 210);
        VehicleType type2 = new VehicleType(four, "AA - Sedan", 530);
        Fleet fleet=new Fleet();//owner=null
        fleet.setUuid(five);
        vehicles.put(one, new Vehicle(one, "Volkswagen", "Beetle", "ABC-123", LocalDate.parse("20000101", yearFormat), "abcdefhijk", 1000, 123, type1,fleet));
        vehicles.put(two, new Vehicle(two, "Lamborghini", "Diablo GT", "IAM-007", LocalDate.parse("19920101", yearFormat), "abcdefhijk", 3, 123, type2,fleet));
        fleet.addVehicle(vehicles.get(one));
        fleet.addVehicle(vehicles.get(two));
    }

    private int counter = 2;

    @Override
    public Vehicle get(UUID id) throws DataAccessException {
        if (!vehicles.containsKey(id)) {
            throw new DataAccessException();
        }
        return vehicles.get(id);
    }

    @Override
    public void remove(UUID id) throws DataAccessException {

    }

    @Override
    public Collection<Vehicle> listFiltered(Filter... filters) {
        // TODO actually use filters
        return new HashSet<>(vehicles.values());
    }

    @Override
    public void close() {

    }

    @Override
    public Vehicle create(String brand, String model, String chassisNumber, String licenseplate, int value, int mileage, VehicleType type, LocalDate productionDate) throws DataAccessException {
        return null;
    }

    @Override
    public Vehicle update(UUID uuid, String brand, String model, String chassisNumber, String licenseplate, int value, int mileage, VehicleType type, LocalDate productionDate) throws DataAccessException {
        return null;
    }

    @Override
    public Vehicle create(String brand, String model, String chassisNumber, String licenseplate, int value, int mileage, VehicleType type, LocalDate productionDate, Fleet fleet) throws DataAccessException {
        return null;
    }

    @Override
    public Vehicle update(UUID uuid, String brand, String model, String chassisNumber, String licenseplate, int value, int mileage, VehicleType type, LocalDate productionDate, Fleet fleet) throws DataAccessException {
        return null;
    }

    @Override
    public Filter<Vehicle> byBrand(String brandName) {
        return null;
    }

    @Override
    public Filter<Vehicle> byModel(String model) {
        return null;
    }

    @Override
    public Filter<Vehicle> byLicensePlate(String licensePlate) {
        return null;
    }

    @Override
    public Filter<Vehicle> atProductionDate(LocalDate productionDate) {
        return null;
    }

    @Override
    public Filter<Vehicle> beforeProductionDate(LocalDate productionDate) {
        return null;
    }

    @Override
    public Filter<Vehicle> afterProductionDate(LocalDate productionDate) {
        return null;
    }

    @Override
    public Filter<Vehicle> atLeastMileage(int mileage) {
        return null;
    }

    @Override
    public Filter<Vehicle> maxMileage(int mileage) {
        return null;
    }

    @Override
    public Filter<Vehicle> byType(VehicleType type) {
        return null;
    }

    @Override
    public Filter<Vehicle> byFleet(Fleet fleet) {
        return null;
    }
}
