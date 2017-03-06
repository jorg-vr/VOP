package src.main.java.model.account;
import src.main.java.model.identity.Identity;
import src.main.java.model.history.EditableObject;

import java.util.Random;
import java.util.UUID;


public class Account implements EditableObject, java.io.Serializable {

    private String login;

    private String hashedPassword;
    private Role role;
    private Identity identity;
    private UUID uuid;

    public Account() {
    }

    public Account(String login, String hashedPassword, Role role, Identity identity) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.identity = identity;
        uuid= UUID.randomUUID();
    }

    private Account(String login, String hashedPassword, Role role, Identity identity, UUID uuid) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.identity = identity;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
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
        return new Account(login,hashedPassword,role,identity,uuid);
    }
}
