package model.identity;

import model.history.EditableObject;

import java.util.UUID;

public abstract class Identity implements EditableObject, java.io.Serializable {

    private UUID uuid;

    private Address address;

    private String phoneNumber;

    public Identity() {
    }

    public Identity(UUID uuid) {
        this.uuid = uuid;
    }

    public Identity(Address address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Identity(UUID id, Address address, String phoneNumber) {
        this.uuid = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

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
        if (o == null || ! (o instanceof Identity)) return false;
        Identity identity = (Identity) o;
        return uuid.equals(identity.getUuid());
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
