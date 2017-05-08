package spring.controller.insurance;

import controller.ControllerManager;
import controller.VehicleTypeController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.fleet.VehicleType;
import model.insurance.SuretyType;
import org.springframework.web.bind.annotation.*;
import spring.model.AuthenticationToken;

import java.util.UUID;

import static util.UUIDUtil.toUUID;

/**
 * Requests that are implemented in this class:
 * 1) GET vehicles/types/{typeName}/taxes/{contractType}
 * 2) PUT vehicles/types/{typeName}/taxes/{contractType}
 */
@RestController
@RequestMapping("${path.vehicles}/${path.types}/{typeName}/${path.taxes}/{contractType}")
public class RESTTaxController {

    @RequestMapping(method = RequestMethod.GET)
    public double get(@PathVariable String typeName, @PathVariable String contractType,
                      @RequestHeader(value = "Authorization") String token,
                      @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ObjectNotFoundException, DataAccessException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleTypeController controller = manager.getVehicleTypeController();
            VehicleType type = controller.get(toUUID(typeName));
            return type.getTax(SuretyType.valueOf(contractType));
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public double put(@PathVariable String typeName, @PathVariable String contractType,
                      double tax,
                      @RequestHeader(value = "Authorization") String token,
                      @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException, ConstraintViolationException, ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleTypeController controller = manager.getVehicleTypeController();
            VehicleType type = controller.get(toUUID(typeName));

            SuretyType suretyType = SuretyType.valueOf(contractType);
            type.setTax(suretyType, tax);
            type = controller.update(type);
            return type.getTax(suretyType);
        }
    }
}
