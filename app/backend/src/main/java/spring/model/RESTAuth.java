package spring.model;

/**
 * Created by jorg on 4/5/17.
 */
public class RESTAuth {

    private String login;
    private String password;

    public RESTAuth() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
