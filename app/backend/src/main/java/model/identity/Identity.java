package model.identity;


import java.util.UUID;

public class Identity implements java.io.Serializable {

    private UUID uuid;

    private Address address;

    private String email;

    private String phoneNumber;

    public Identity() {
    }

    public Identity(UUID id, Address address, String email, String phoneNumber){
        this.uuid = id;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UUID getUuid(){
        return uuid;
    }

    public void setUuid(UUID id) {
        this.uuid = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return uuid == ((Identity)o).uuid;

    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
