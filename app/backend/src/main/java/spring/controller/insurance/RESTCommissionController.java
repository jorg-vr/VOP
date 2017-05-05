package spring.controller.insurance;

import controller.*;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Company;
import model.identity.Customer;
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
 * Paths implemented in this controller:
 * 1) GET /vehicles/types/{id}/commissions/{contractType}
 * 2) PUT /vehicles/types/{id}/commissions/{contractType}
 *
 * 3) GET /vehicles/{id}/commissions/{contractType}
 * 4) PUT /vehicles/{id}/commissions/{contractType}
 * 5) DELETE /vehicles/{id}/commissions/{contractType}
 *
 * 6) GET /companies/{id}/commissions/{contractType}
 * 7) PUT /companies/{id}/commissions/{contractType}
 * 8) DELETE /companies/{id}/commissions/{contractType}
 */
@RestController
public class RESTCommissionController {

    @RequestMapping(value = "${path.vehicles}/${path.types}/{id}/${path.commissions}/{contractType}", method = RequestMethod.GET)
    public RESTCommission getVehicleTypeCommission(@PathVariable String id, @PathVariable String contractType,
                                                   @RequestHeader(value = "Authorization") String token,
                                                   @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleTypeController controller = manager.getVehicleTypeController();
            VehicleType vehicleType1 = controller.get(toUUID(id));
            return new RESTCommission(vehicleType1.getCommission(SuretyType.valueOf(contractType)));
        } catch (DataAccessException e) {
            throw new NotFoundException();

        } catch (ObjectNotFoundException e) {
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
    public RESTCommission getVehicleCommission(@PathVariable String id, @PathVariable String contractType,
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

    @RequestMapping(value = "${path.vehicles}/{id}/${path.commissions}/{contractType}", method = RequestMethod.DELETE)
    public void deleteVehicleCommission(@PathVariable String id, @PathVariable String contractType,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleController controller = manager.getVehicleController();
            Vehicle vehicle = controller.get(toUUID(id));

            SuretyType suretyType = SuretyType.valueOf(contractType);
            vehicle.removeSpecificCommission(suretyType);
            controller.update(vehicle);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "${path.companies}/{id}/${path.commissions}/{contractType}", method = RequestMethod.GET)
    public RESTCommission getCustomerCommission(@PathVariable String id, @PathVariable String contractType,
                                               @RequestHeader(value = "Authorization") String token,
                                               @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            CustomerController controller = manager.getCustomerController();
            Customer customer = controller.get(toUUID(id));
            return new RESTCommission(customer.getSpecificCommission(SuretyType.valueOf(contractType)));
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "${path.companies}/{id}/${path.commissions}/{contractType}", method = RequestMethod.PUT)
    public RESTCommission putCustomerCommission(@PathVariable String id, @PathVariable String contractType,
                                               @RequestHeader(value = "Authorization") String token,
                                               @RequestHeader(value = "Function") String function,
                                               @RequestBody RESTCommission commission) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            CustomerController controller = manager.getCustomerController();
            Customer customer = controller.get(toUUID(id));

            SuretyType suretyType = SuretyType.valueOf(contractType);
            customer.setSpecificCommission(suretyType, commission.getCommission());
            controller.update(customer);

            return commission;
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "${path.companies}/{id}/${path.commissions}/{contractType}", method = RequestMethod.DELETE)
    public void deleteCustomerCommission(@PathVariable String id, @PathVariable String contractType,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            CustomerController controller = manager.getCustomerController();
            Customer customer = controller.get(toUUID(id));

            SuretyType suretyType = SuretyType.valueOf(contractType);
            customer.removeSpecificCommission(suretyType);
            controller.update(customer);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }
}
