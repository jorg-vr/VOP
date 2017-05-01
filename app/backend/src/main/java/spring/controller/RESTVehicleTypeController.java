package spring.controller;

import controller.AbstractController;
import controller.CompanyController;
import controller.ControllerManager;
import controller.VehicleTypeController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.fleet.VehicleType;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.AuthenticationToken;
import spring.model.RESTSchema;
import spring.model.RESTVehicleType;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * Created by jorg on 3/14/17.
 * Although the api doesn't offer this, this is an improvement to the program
 * It offers the frontend applications a way to view wich vehicletypes are correct inputs
 * may be enlarged when admin gets rights to edit vehicletypes
 */
@RestController
@RequestMapping("/${path.vehicles}/${path.types}")
public class RESTVehicleTypeController extends RESTAbstractController<RESTVehicleType, VehicleType> {

    public RESTVehicleTypeController() {
        super(RESTVehicleType::new);
    }

    @Override
    public AbstractController<VehicleType> getController(ControllerManager manager) {
        return manager.getVehicleTypeController();
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTVehicleType> getAllVehileTypes(HttpServletRequest request,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer limit,
                                                         @RequestHeader(value = "Authorization") String token,
                                                         @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleTypeController controller = manager.getVehicleTypeController();
            Collection<RESTVehicleType> types = controller.getAll()
                    .stream()
                    .map(RESTVehicleType::new)
                    .collect(Collectors.toList());

            return new RESTSchema<>(types, page, limit, request);
        } catch (DataAccessException e) {
            throw new InvalidInputException("Some parameters where invalid");
        }
    }

}
