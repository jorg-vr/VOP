package model.identity;


import model.history.EditableObject;
import model.history.LogResource;

import java.io.Serializable;
import java.util.UUID;

/**
 * Class representing an Address
 */
public class Address implements Serializable, EditableObject {

    /**
     * The unique id
     */
    private UUID uuid;

    /**
     * The street
     */
    private String street;

    /**
     * The street number
     */
    private String streetNumber;

    /**
     * The town
     */
    private String town;

    /**
     * The postal code
     */
    private String postalCode;

    /**
     * The country
     */
    private String country;

    /**
     * Default Constructor
     */
    public Address() {
    }

    /**
     * Constructor
     *
     * @param street       the street
     * @param streetNumber the street number
     * @param town         the town
     * @param postalCode   the postal code
     * @param country      the country
     */
    public Address(String street, String streetNumber, String town, String postalCode, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.town = town;
        this.postalCode = postalCode;
        this.country = country;
    }

    /**
     * Gets the uuid
     *
     * @return the uuid;
     */
    public UUID getUuid() {
        return uuid;
    }


    /**
     * Sets the uuid
     *
     * @param uuid the uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the street
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street
     *
     * @param street the street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the street number
     *
     * @return the street number
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Sets the street number
     *
     * @param streetNumber the street number
     */
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Gets the town
     *
     * @return the town
     */
    public String getTown() {
        return town;
    }

    /**
     * Sets the town
     *
     * @param town the town
     */
    public void setTown(String town) {
        this.town = town;
    }

    /**
     * Gets the postal code
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the country
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Copies the object
     * @return the copy
     */
    @Override
    public Address copy() {
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
    public LogResource getLogResource() {
        return LogResource.ADDRESS;
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                !(o == null ||
                        !(o instanceof Address)) && uuid != null && getUuid().equals(((Address) o).getUuid());

    }

    @Override
    public int hashCode() {
        if (uuid != null) {
            return uuid.hashCode();
        }
        return super.hashCode();
    }
}
