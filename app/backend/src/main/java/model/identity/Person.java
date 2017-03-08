package model.identity;

import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;


public class Person extends Identity implements java.io.Serializable {

    private String firstName;

    private String lastName;

    private Image picture;

    private LocalDate dateOfBirth;

    private Function function;

    public Person() {
    }

    public Person(UUID id, Address address, String email, String phoneNumber, String firstName, String lastName, Image picture, LocalDate dateOfBirth, Function function) {
        super(id, address, email, phoneNumber);
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.dateOfBirth = dateOfBirth;
        this.function = function;
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

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
