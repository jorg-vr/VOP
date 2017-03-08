package model.identity;

import model.history.EditableObject;

import java.util.UUID;

public class Identity implements EditableObject, java.io.Serializable {

    private UUID uuid;

    private Address address;

    private String email;

    private int phoneNumber;

    public Identity() {
    }

    public Identity(UUID uuid, Address address, String email, int phoneNumber){
        this.uuid = uuid;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UUID getUuid(){
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return uuid == ((Identity)o).uuid;

    }

    @Override
    public EditableObject copy() {
        return new Identity(uuid, address, email, phoneNumber);
    }
}
