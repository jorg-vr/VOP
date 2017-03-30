package spring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import model.account.Account;
import model.account.Function;
import model.identity.Person;
import spring.controller.UUIDUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This is a bean class as specified in the API specification
 */
public class RESTUser extends RESTAbstractModel<Account> {

    private static final String PATH_USERS = "/users";

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public RESTUser() {
    }

    /**
     * Created a RESTUser based on the fields of account and pereson
     * @param account should not not be null
     * @param person should not be null
     */
    public RESTUser(Account account, Person person) {
        super(account.getUuid(), PATH_USERS);
        firstName = person.getFirstName();
        lastName = person.getLastName();
        email = person.getEmail();
        password = account.getHashedPassword();
    }

    public RESTUser(Account account) {
        super(account.getUuid(), PATH_USERS);
        Person person=account.getPerson();
        firstName = person.getFirstName();
        lastName = person.getLastName();
        email = person.getEmail();
        password = account.getHashedPassword();
    }

    public Account translate(Function f){
        Person person=new Person();
        person.setEmail(getEmail());
        person.setFirstName(getFirstName());
        person.setLastName(getLastName());
        Account account=new Account();
        account.setPerson(person);
        account.setLogin(getEmail());
        account.setUuid(UUIDUtil.toUUID(getId()));
        return  account;
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
