package model.identity;

import model.history.EditableObject;

import java.util.UUID;

import static util.UUIDUtil.UUIDToNumberString;

/**
 * Class representing a company or person
 */
public abstract class Identity implements EditableObject, java.io.Serializable {

    /**
     * The unique id
     */
    private UUID uuid;

    /**
     * The address
     */
    private Address address;

    /**
     * The phone number
     */
    private String phoneNumber;

    /**
     * Default constructor
     */
    public Identity() {
    }

    /**
     * Constructor
     *
     * @param uuid the uuid
     */
    public Identity(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Constructor
     *
     * @param address     the address
     * @param phoneNumber the phone number
     */
    public Identity(Address address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructor
     *
     * @param id          the id
     * @param address     the address
     * @param phoneNumber the phone number
     */
    public Identity(UUID id, Address address, String phoneNumber) {
        this.uuid = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the id
     *
     * @return the id
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the id
     *
     * @param uuid the id
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the address
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address
     *
     * @param address the address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets the phone number
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Checks if 2 objects are equal to the same identity, based on the UUID.
     *
     * @param o Identity to compare to
     * @return true if 2 objects have the same UUID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Identity)) return false;
        Identity identity = (Identity) o;
        return uuid != null && uuid.equals(identity.getUuid());
    }

    @Override
    public int hashCode() {
        if (uuid != null) {
            return uuid.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public String toString() {
        return UUIDToNumberString(uuid);
    }
}

