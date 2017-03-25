package spring.model;

import model.identity.Address;

/**
 * This is a bean class as specified in the API specification
 */
public class RESTAddress {
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String postalCode;

    public RESTAddress(String country, String city, String street, String houseNumber, String postalCode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
    }

    public RESTAddress() {
    }

    public RESTAddress(Address address) {
        country = address.getCountry();
        city  = address.getTown();
        street = address.getStreet();
        houseNumber = address.getStreetNumber();
        postalCode = address.getPostalCode();
    }

    public Address translate() {
        Address address = new Address();
        address.setCountry(country);
        address.setTown(city);
        address.setStreet(street);
        address.setStreetNumber(houseNumber);
        address.setPostalCode(postalCode);
        return address;
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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
