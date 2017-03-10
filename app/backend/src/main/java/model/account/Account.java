package model.account;
import model.identity.Identity;
import model.history.EditableObject;
import model.identity.Person;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;


public class Account implements EditableObject, java.io.Serializable {

    private String login;
    private String hashedPassword;
    private Person person;
    private UUID uuid;
    private Collection<Function> functions;

    public Account() {
    }

    public Account(String login, String hashedPassword, Person person, Collection<Function> functions) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.person = person;
        this.functions = functions;
        uuid= UUID.randomUUID();
    }

    private Account(String login, String hashedPassword,  Person person, Collection<Function> functions, UUID uuid) {
        this(login,hashedPassword,person,functions);
        this.uuid = uuid;
    }

    public void resetPassword(String oldPassword,String newPassword) {
        if(validatePassword(oldPassword)) {
            this.hashedPassword = newPassword; //TODO hash this extra
        }
    }

    public boolean validatePassword(String password){
        return hashedPassword.equals(password); // TODO also use hashfunction
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Collection<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(Collection<Function> functions) {
        this.functions = functions;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public EditableObject copy() {
        return new Account(login,hashedPassword,person,functions,uuid);
    }
}
