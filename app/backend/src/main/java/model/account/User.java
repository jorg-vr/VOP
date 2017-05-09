package model.account;

import model.history.EditableObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


/**
 * This class represents an user of the application
 */
public class User implements EditableObject, java.io.Serializable {

    /**
     * Unique key to represent this object.
     */
    private UUID uuid;

    /**
     * First name of user, should not be null
     */
    private String firstName;

    /**
     * Last name of user, should not be null
     */
    private String lastName;

    /**
     * Email of the user, should be unique
     */
    private String email;

    /**
     * Collection of the functions this user has (e.g. admin and customer)
     */
    private Collection<Function> functions;

    /**
     * Hashed password
     */
    private String password;


    public User() {
        functions = new ArrayList<>();
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        functions = new ArrayList<>();
    }


    public Collection<Function> getFunctions() {
        return new ArrayList<>(functions);
    }

    public void setFunctions(Collection<Function> functions) {
        this.functions = new ArrayList<>(functions);
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public EditableObject copy() {
        User user = new User();
        user.setUuid(uuid);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setFunctions(new ArrayList<>(functions));
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof User)) return false;

        User user = (User) o;

        return getUuid().equals(user.getUuid());

    }

    @Override
    public int hashCode() {
        if (uuid != null) {
            return getUuid().hashCode();
        }
        return super.hashCode();
    }
}
