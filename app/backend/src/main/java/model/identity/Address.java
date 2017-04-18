package model.identity;


import model.history.EditableObject;

import java.io.Serializable;
import java.util.UUID;

public class Address implements Serializable, EditableObject {

    private UUID uuid;

    private String street;

    private String streetNumber;

    private String town;

    private String postalCode;

    private String country;

    public Address() {
    }

    public Address(String street, String streetNumber, String town, String postalCode, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.town = town;
        this.postalCode = postalCode;
        this.country = country;
    }

    public UUID getUuid() {
        return uuid;
    }


    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public EditableObject copy() {
        Address address = new Address();
        address.setUuid(getUuid());
        address.setCountry(getCountry());
        address.setPostalCode(getPostalCode());
        address.setTown(getTown());
        address.setStreet(getStreet());
        address.setStreetNumber(getStreetNumber());
        return address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Address)) return false;

        Address address = (Address) o;

        return getUuid().equals(((Address) o).getUuid());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    /*@Override
    public String toString() {
        return "Address{" +
                "uuid= " + getUuid() +
                ", street= " + street +
                ", streetNumber= " + streetNumber +
                ", town= " + town +
                ", postalCode= " + postalCode +
                ", country= " + country +
                '}';
    }*/
}
