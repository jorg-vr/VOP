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
    private String license_plate;
    private String chassis_number;
    private String brand;
    private String model;
    private String type;
    private int kilometer_count;
    private String year;
    private String leasing_company; //id of leasing company
    private String created_at;
    private String updated_at;
    private String url;

    public RESTVehicle() {
    }

    public RESTVehicle(String id, String license_plate, String chassis_number, String brand, String model, String type, int kilometer_count, String year, String leasing_company, String created_at, String updated_at, String url) {
        this.id = id;
        this.license_plate = license_plate;
        this.chassis_number = chassis_number;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.kilometer_count = kilometer_count;
        this.year = year;
        this.leasing_company = leasing_company;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public String getChassis_number() {
        return chassis_number;
    }

    public void setChassis_number(String chassis_number) {
        this.chassis_number = chassis_number;
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

    public int getKilometer_count() {
        return kilometer_count;
    }

    public void setKilometer_count(int kilometer_count) {
        this.kilometer_count = kilometer_count;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLeasing_company() {
        return leasing_company;
    }

    public void setLeasing_company(String leasing_company) {
        this.leasing_company = leasing_company;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
