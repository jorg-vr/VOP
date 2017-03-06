package src.main.java.model.identity;


public class Address implements java.io.Serializable {

    private String street;

    private int streetNumber;

    private String town;

    private int postalCode;

    private String country;

    public Address() {
    }

    public Address(String street, int streetNumber, String town, int postalCode, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.town = town;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
