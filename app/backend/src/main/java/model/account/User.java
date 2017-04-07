package model.account;

import model.history.EditableObject;
import model.identity.Company;
import spring.exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public class User implements EditableObject, java.io.Serializable {

    private UUID uuid;

    private String firstName;

    private String lastName;

    private String email;

    private Collection<Function> functions;

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

    public void resetPassword(String oldPassword, String newPassword) {
        if (validatePassword(oldPassword)) {
            this.password = newPassword; //TODO hash this extra
        }
    }

    public boolean validatePassword(String password) {
        return password.equals(password); // TODO also use hashfunction
    }

    /**
     * Attempts to a add a new Function to the User
     * @param newFunction Function that should be added to the User. The User field of Function should be set to the User.
     * @throws InvalidInputException newFunction.user is not equal to the User or the User already has that Function.
     *                               If the company field of the User is not null, (role, company) should be unique.
     *                               If the company field of the User is null, (role) should be unique.
     */
    public void addFunction(Function newFunction) throws InvalidInputException {
        if (!newFunction.getUser().equals(this)) {
            throw new InvalidInputException("User field of function field should be set to this user");
        }

        for (Function function : functions) {
            Role role = newFunction.getRole();
            Company company = newFunction.getCompany();
            Role role2 = function.getRole();
            Company company2 = function.getCompany();

            if (role2.equals(role) && (company2 == null || company2.equals(company))) {
                throw new InvalidInputException("User already has that function");
            }
        }
        functions.add(newFunction);
    }

    /**
     * Attempts to remove the Function of the User
     * @param function Function to remove
     * @return true if removal was successful
     */
    public boolean removeFunction(Function function) {
        return functions.remove(function);
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
        return getUuid().hashCode();
    }
}
