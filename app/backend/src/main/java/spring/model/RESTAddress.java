package spring.model;

/**
 * Created by jorg on 3/6/17.
 */
public class RESTAddress {
    private String country;
    private String city;
    private String street;
    private String house_number;
    private int postal_code;

    public RESTAddress(String country, String city, String street, String house_number, int postal_code) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.house_number = house_number;
        this.postal_code = postal_code;
    }

    public RESTAddress() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }
}
