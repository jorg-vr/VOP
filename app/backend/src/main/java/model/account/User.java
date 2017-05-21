package model.account;

import model.history.EditableObject;
import model.history.LogResource;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static util.UUIDUtil.UUIDToNumberString;


/**
 * This class represents a user of the application
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


    /**
     * Default constructor
     */
    public User() {
        functions = new ArrayList<>();
    }

    /**
     * Constructor
     *
     * @param uuid the uuid of the user
     */
    public User(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Constructor
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @param notHashedPassword  the not hashed password
     */
    public User(String firstName, String lastName, String email, String notHashedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        setNotHashedPassword(notHashedPassword);
        functions = new ArrayList<>();
    }

    /**
     * Gets the functions of the user
     *
     * @return the functions
     */
    public Collection<Function> getFunctions() {
        return new ArrayList<>(functions);
    }

    /**
     * Sets the functions of the user
     *
     * @param functions the functions
     */
    public void setFunctions(Collection<Function> functions) {
        this.functions = new ArrayList<>(functions);
    }

    /**
     * Gets the id
     *
     * @return the id
     */
    @Override
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
     * Gets the first name
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the hashed password
     *
     * @return the hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the hashed password
     * @param password the hashed password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the not hashed password.
     * The password will get hashed in this method
     * @param password the not hashed password
     */
    public void setNotHashedPassword(String password) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        this.password = passwordEncryptor.encryptPassword(password);
    }


    /**
     * Copies the object and sets all its fields
     *
     * @return the copied object
     */
    @Override
    public User copy() {
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
    public LogResource getLogResource() {
        return LogResource.USER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof User)) return false;

        User user = (User) o;

        return this.uuid != null && getUuid().equals(user.getUuid());

    }

    @Override
    public int hashCode() {
        if (uuid != null) {
            return getUuid().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public String toString() {
        return UUIDToNumberString(uuid);
    }
}
