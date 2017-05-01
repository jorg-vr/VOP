package spring.controller;

import controller.AuthController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import model.account.Function;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotFoundException;
import spring.exceptions.ServerErrorException;
import spring.model.AuthenticationToken;
import spring.model.RESTFunction;
import spring.model.RESTSchema;
import spring.model.RESTUser;
import util.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/${path.users}/me")
public class RESTUserMeController {

    @RequestMapping(method = RequestMethod.GET)
    public RESTUser getMe(@RequestHeader(value = "Authorization") String token) {
        try (AuthController authController = new AuthController()) {
            return new RESTUser(authController.getUser(new AuthenticationToken(token)));
        } catch (DataAccessException e) {
            throw new ServerErrorException("No user associated with the token");
        }
    }

    @RequestMapping(value = "/functions", method = RequestMethod.GET)
    public RESTSchema<RESTFunction> getAll(HttpServletRequest request,
                                           Integer page,
                                           Integer limit,
                                           @RequestHeader(value = "Authorization") String token) {
        try (AuthController authController = new AuthController()) {
            Collection<RESTFunction> functions = authController.getUser(new AuthenticationToken(token))
                    .getFunctions()
                    .stream()
                    .map(RESTFunction::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(functions, page, limit, request);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/${path.functions}/{id}", method = RequestMethod.GET)
    public RESTFunction getFunction(@PathVariable String id, @RequestHeader(value = "Authorization") String token) {
        UUID uuid = UUIDUtil.toUUID(id);
        try (AuthController controller = new AuthController()) {
            for (Function function : controller.getUser(new AuthenticationToken(token)).getFunctions()) {
                if (function.getUuid().equals(uuid)) {
                    return new RESTFunction(function);
                }
            }
            throw new InvalidInputException("user has no function with that id");
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RESTUser putId(@RequestBody RESTUser rest, @RequestHeader(value = "Authorization") String token,
                          @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        String id = UUIDUtil.UUIDToNumberString(new AuthenticationToken(token).getAccountId());
        return new RESTUserController().putId(id, rest, token, function);
    }
}
