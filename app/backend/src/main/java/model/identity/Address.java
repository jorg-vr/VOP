package model.identity;


import java.util.UUID;

public class Address implements java.io.Serializable {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return getUuid().equals(((Address) o).getUuid());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
