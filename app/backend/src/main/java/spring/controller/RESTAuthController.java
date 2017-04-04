package spring.controller;

import controller.AuthController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.AuthenticationToken;

/**
 * Created by jorg on 4/3/17.
 */
public class RESTAuthController {

    @RequestMapping(method = RequestMethod.POST)
    public String post(@RequestBody String rest){
        try(AuthController authController =new AuthController()) {
            AuthenticationToken token=new AuthenticationToken(rest);
            return factory.create(model);
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
