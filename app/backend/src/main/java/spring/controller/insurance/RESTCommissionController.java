package spring.controller.insurance;

import controller.ControllerManager;
import controller.VehicleController;
import controller.VehicleTypeController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.insurance.SuretyType;
import org.springframework.web.bind.annotation.*;
import spring.controller.RESTAbstractController;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotFoundException;
import spring.model.AuthenticationToken;
import spring.model.RESTVehicleType;
import spring.model.insurance.RESTCommission;

import java.util.UUID;

import static util.UUIDUtil.toUUID;

/**
 * Created by Billie Devolder on 2/05/2017.
 */
@RestController
public class RESTCommissionController {

    @RequestMapping(value = "${path.vehicles}/${path.types}/{vehicleType}/${path.commissions}/{contractType}", method = RequestMethod.GET)
    public RESTCommission getVehicleTypeCommission(@PathVariable String vehicleType, @PathVariable String contractType,
                             @RequestHeader(value = "Authorization") String token,
                             @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleTypeController controller = manager.getVehicleTypeController();
            VehicleType vehicleType1 = controller.get(toUUID(vehicleType));
            return new RESTCommission(vehicleType1.getCommission(SuretyType.valueOf(contractType)));
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "${path.vehicles}/${path.types}/{vehicleType}/${path.commissions}/{contractType}", method = RequestMethod.PUT)
    public RESTCommission putVehicleTypeCommission(@PathVariable String vehicleType, @PathVariable String contractType,
                                                   @RequestHeader(value = "Authorization") String token,
                                                   @RequestHeader(value = "Function") String function,
                                                   @RequestBody RESTCommission commission) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleTypeController controller = manager.getVehicleTypeController();
            VehicleType type = controller.get(toUUID(vehicleType));

            SuretyType suretyType = SuretyType.valueOf(contractType);
            type.setCommission(suretyType, commission.getCommission());
            controller.update(type);

            return commission;
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "${path.vehicles}/{id}/${path.commissions}/{contractType}", method = RequestMethod.GET)
    public RESTCommission getVehicleeCommission(@PathVariable String id, @PathVariable String contractType,
                                                   @RequestHeader(value = "Authorization") String token,
                                                   @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleController controller = manager.getVehicleController();
            Vehicle vehicle = controller.get(toUUID(id));
            return new RESTCommission(vehicle.getSpecificCommission(SuretyType.valueOf(contractType)));
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "${path.vehicles}/{id}/${path.commissions}/{contractType}", method = RequestMethod.PUT)
    public RESTCommission putVehicleCommission(@PathVariable String id, @PathVariable String contractType,
                                                   @RequestHeader(value = "Authorization") String token,
                                                   @RequestHeader(value = "Function") String function,
                                                   @RequestBody RESTCommission commission) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleController controller = manager.getVehicleController();
            Vehicle vehicle = controller.get(toUUID(id));

            SuretyType suretyType = SuretyType.valueOf(contractType);
            vehicle.setSpecificCommission(suretyType, commission.getCommission());
            controller.update(vehicle);

            return commission;
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }
}
