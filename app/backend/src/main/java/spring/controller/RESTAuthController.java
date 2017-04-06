package spring.controller;

import controller.AuthController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.AuthenticationToken;
import spring.model.RESTAuth;
import spring.model.RESTRole;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by jorg on 4/3/17.
 */
@RestController
@RequestMapping("/auth")
public class RESTAuthController {

    @RequestMapping(method = RequestMethod.POST)
    public String post(@RequestBody RESTAuth restAuth){
        try(AuthController authController =new AuthController()) {
            return authController.getToken(restAuth.getLogin(),restAuth.getPassword()).toString();
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }

    /*
    @RequestMapping(method = RequestMethod.GET)
    public Collection<RESTRole> getAll(@RequestHeader(value="AuthToken") String token){
        try(AuthController authController =new AuthController()) {
            Collection<RESTRole> restRoles=new ArrayList<>();
            for(Function function:authController.getFunctions(new AuthenticationToken(token))){
                restRoles.add(new RESTRole(function));
            }
            return restRoles;
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }
    */
    @RequestMapping(method = RequestMethod.PUT)
    public String put(@RequestHeader(value="AuthToken") String token){
        try(AuthController authController =new AuthController()) {
            return authController.refreshToken(new AuthenticationToken(token)).toString();
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }

}
