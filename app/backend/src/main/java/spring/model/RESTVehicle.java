package spring.model;

import controller.FleetController;
import controller.VehicleTypeController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;
import model.fleet.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import spring.controller.UUIDUtil;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static util.MyProperties.PATH_VEHICLES;
import static util.MyProperties.getProperty;

/**
 * This is a bean class as specified in the API specification
 */
@ResponseStatus(value = HttpStatus.OK, reason = "OK")
public class RESTVehicle extends RESTAbstractModel<Vehicle> {

    private static DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyyMMdd").withLocale(Locale.forLanguageTag("NL"));

    private String licensePlate;
    private String vin; //chassisnumber
    private String brand;
    private String model;
    private String type;
    private int value;
    private int mileage;
    private String year;
    private String leasingCompany; //id of leasing company
    private String fleet;

    public RESTVehicle() {
    }

    /**
     * Create a new RESTVehicle based on the fields of the vehicle object
     * @param vehicle the vehicle that this RESTVehicle is based on
     */
    public RESTVehicle(Vehicle vehicle) {
        super(vehicle.getUuid(), getProperty(PATH_VEHICLES));
        licensePlate = vehicle.getLicensePlate();
        vin = vehicle.getChassisNumber();
        brand = vehicle.getBrand();
        model = vehicle.getModel();
        type = vehicle.getType() != null ? UUIDUtil.UUIDToNumberString(vehicle.getType().getUuid()) : null;
        value = vehicle.getValue();
        mileage = vehicle.getMileage();
        year = vehicle.getProductionDate().getYear() + "";
        leasingCompany = vehicle.getLeasingCompany() != null ? UUIDUtil.UUIDToNumberString(vehicle.getLeasingCompany().getUuid()) : null;
        fleet = vehicle.getFleet() != null ? UUIDUtil.UUIDToNumberString(vehicle.getFleet().getUuid()) : null;
    }

    /**
     * @return a new Vehicle object that has fields that are based on this object
     */
    public Vehicle translate(Function function) {
        Vehicle vehicle = new Vehicle();
        vehicle.setUuid(UUIDUtil.toUUID(getId()));
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setLicensePlate(licensePlate);
        LocalDate year = LocalDate.parse(this.year + "0101", yearFormat);//Fix conversion bug
        vehicle.setProductionDate(year);
        vehicle.setChassisNumber(vin);
        vehicle.setValue(value);
        vehicle.setMileage(mileage);
        try(VehicleTypeController vehicleTypeController=new VehicleTypeController(function)) {
            vehicle.setType( vehicleTypeController.get(UUIDUtil.toUUID(getType())));
        } catch (DataAccessException e) {
            throw new InvalidInputException("type");
        }  catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
        try(FleetController fleetController=new FleetController(function)) {
            vehicle.setFleet(fleetController.get(UUIDUtil.toUUID(getFleet())));
        }catch (DataAccessException e) {
            throw new InvalidInputException("fleet");
        }  catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
        return vehicle;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLeasingCompany() {
        return leasingCompany;
    }

    public void setLeasingCompany(String leasingCompany) {
        this.leasingCompany = leasingCompany;
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

}
