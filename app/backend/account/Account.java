package account;

import history.EditableObject;
import identity.Identity;

import java.util.Random;
import java.util.UUID;

/**
 * Created by jorg on 3/2/17.
 */
public class Account implements EditableObject {
    private String login;
    private String hashedPassword;
    private Role role;
    private Identity identity;
    private UUID uuid;

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

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public Role getRole() {
        return role;
    }

    public Identity getIdentity() {
        return identity;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public EditableObject copy() {
        return new Account(login,hashedPassword,role,identity,uuid);
    }
}
