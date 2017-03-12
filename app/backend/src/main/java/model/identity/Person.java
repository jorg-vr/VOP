package model.identity;

import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;


public class Person extends Identity implements java.io.Serializable {

    private String firstName;

    private String lastName;

    public Person() {
    }

    public Person(UUID id, Address address, String email, String phoneNumber, String firstName, String lastName) {
        super(id, address, email, phoneNumber);
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
