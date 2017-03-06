package model.identity;


public class Identity {

    private int id;

    private Address address;

    private String email;

    private int phoneNumber;

    public Identity(int id, Address address, String email, int phoneNumber){
        this.id = id;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getId(){
        return id;
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

        return id == ((Identity)o).id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
