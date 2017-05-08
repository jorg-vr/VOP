package spring.model;

import controller.ControllerManager;
import model.account.User;
import util.UUIDUtil;

import static util.MyProperties.PATH_USERS;
import static util.MyProperties.getProperty;

/**
 * This is a bean class as specified in the API specification
 */
public class RESTUser extends RESTAbstractModel<User> {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public RESTUser() {
    }

    /**
     * Created a RESTUser based on the fields of account and pereson
     *
     * @param user should not be null
     */
    public RESTUser(User user) {
        super(user.getUuid(), getProperty(PATH_USERS));
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
    }

    public User translate(ControllerManager manager) {
        User user = new User();
        user.setUuid(UUIDUtil.toUUID(getId()));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
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
}
