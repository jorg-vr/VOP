package spring.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jorg on 3/6/17.
 * bean class implementing swagger api for vehicle
 */
@ResponseStatus(value= HttpStatus.OK, reason="OK")
public class RESTVehicle {
    private String id;
    private String licensePlate;
    private String chassisNumber;
    private String brand;
    private String model;
    private String type;
    private int kilometerCount;
    private String year;
    private String leasingCompany; //id of leasing company
    private String createdAt;
    private String updatedAt;
    private String url;

    public RESTVehicle() {
    }

    public RESTVehicle(String id, String licensePlate, String chassisNumber, String brand, String model, String type, int kilometerCount, String year, String leasingCompany, String createdAt, String updatedAt, String url) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.chassisNumber = chassisNumber;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.kilometerCount = kilometerCount;
        this.year = year;
        this.leasingCompany = leasingCompany;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
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

    public int getKilometerCount() {
        return kilometerCount;
    }

    public void setKilometerCount(int kilometerCount) {
        this.kilometerCount = kilometerCount;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
