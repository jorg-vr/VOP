package spring.controller;

import controller.AbstractController;
import controller.ControllerManager;
import controller.VehicleTypeController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import model.fleet.VehicleType;
import org.springframework.web.bind.annotation.*;
import spring.model.AuthenticationToken;
import spring.model.RESTSchema;
import spring.model.RESTVehicleType;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * Requests that are implemented in this class:
 * 1)  GET /vehicles/types
 * 2)  GET /vehicles/types/{id}
 * 3)  POST /vehicles/types
 * 4)  PUT /vehicles/types/{id}
 * 5)  DELETE /vehicles/types/{id}
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
    public RESTSchema<RESTVehicleType> getAllVehicleTypes(HttpServletRequest request,
                                                          @RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer limit,
                                                          @RequestHeader(value = "Authorization") String token,
                                                          @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleTypeController controller = manager.getVehicleTypeController();
            Collection<RESTVehicleType> types = controller.getAll()
                    .stream()
                    .map(RESTVehicleType::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(types, page, limit, request);
        }
    }

}
