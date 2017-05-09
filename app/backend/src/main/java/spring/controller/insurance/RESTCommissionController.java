package spring.controller.insurance;

import controller.ControllerManager;
import controller.CustomerController;
import controller.VehicleController;
import controller.VehicleTypeController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Customer;
import model.insurance.Surety;
import model.insurance.SuretyType;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.NotFoundException;
import spring.model.AuthenticationToken;
import spring.model.insurance.RESTCommission;

import java.util.*;

import static util.UUIDUtil.toUUID;

/**
 * Paths implemented in this controller:
 * 1) GET /vehicles/types/{id}/commissions
 * 2) PUT /vehicles/types/{id}/commissions
 *
 * 3) GET /vehicles/{id}/commissions
 * 4) PUT /vehicles/{id}/commissions
 * 5) DELETE /vehicles/{id}/commissions
 *
 * 6) GET /companies/{id}/commissions
 * 7) PUT /companies/{id}/commissions
 * 8) DELETE /companies/{id}/commissions
 */
@RestController
public class RESTCommissionController {
    private Collection<RESTCommission> translateMap(Map<SuretyType,Double> map){
        Collection<RESTCommission> commissions=new ArrayList<>();
        for(SuretyType suretyType: map.keySet()){
            commissions.add(new RESTCommission(map.get(suretyType),suretyType));
        }
        return commissions;
    }

    private Map<SuretyType,Double> translateCommissions(Collection<RESTCommission> commissions){
        Map<SuretyType,Double> map=new HashMap<>();
        for(RESTCommission commission:commissions){
            map.put(commission.getSuretyType(),commission.getCommission());
        }
        return map;
    }

    @RequestMapping(value = "${path.vehicles}/${path.types}/{id}/${path.commissions}", method = RequestMethod.GET)
    public Collection<RESTCommission> getVehicleTypeCommission(@PathVariable String id,
                                                   @RequestHeader(value = "Authorization") String token,
                                                   @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException, ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleTypeController controller = manager.getVehicleTypeController();
            VehicleType vehicleType1 = controller.get(toUUID(id));
            return translateMap(vehicleType1.getCommissions());
        }
    }

    @RequestMapping(value = "${path.vehicles}/${path.types}/{vehicleType}/${path.commissions}", method = RequestMethod.PUT)
    public Collection<RESTCommission> putVehicleTypeCommission(@PathVariable String vehicleType,
                                                   @RequestHeader(value = "Authorization") String token,
                                                   @RequestHeader(value = "Function") String function,
                                                   @RequestBody Collection<RESTCommission> commissions) throws UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleTypeController controller = manager.getVehicleTypeController();
            VehicleType type = controller.get(toUUID(vehicleType));

            type.setCommissions(translateCommissions(commissions));
            controller.update(type);

            return commissions;
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "${path.vehicles}/{id}/${path.commissions}/{contractType}", method = RequestMethod.GET)
    public Collection<RESTCommission> getVehicleCommission(@PathVariable String id, @PathVariable String contractType,
                                                @RequestHeader(value = "Authorization") String token,
                                                @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleController controller = manager.getVehicleController();
            Vehicle vehicle = controller.get(toUUID(id));
            return translateMap(vehicle.getCommissions());
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "${path.vehicles}/{id}/${path.commissions}", method = RequestMethod.PUT)
    public Collection<RESTCommission> putVehicleCommission(@PathVariable String id,
                                               @RequestHeader(value = "Authorization") String token,
                                               @RequestHeader(value = "Function") String function,
                                               @RequestBody Collection<RESTCommission> commissions) throws UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleController controller = manager.getVehicleController();
            Vehicle vehicle = controller.get(toUUID(id));

            vehicle.setCommissions(translateCommissions(commissions));
            controller.update(vehicle);

            return commissions;
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "${path.vehicles}/{id}/${path.commissions}", method = RequestMethod.DELETE)
    public void deleteVehicleCommission(@PathVariable String id,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ConstraintViolationException, DataAccessException, ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleController controller = manager.getVehicleController();
            Vehicle vehicle = controller.get(toUUID(id));

            vehicle.setCommissions(null);
            controller.update(vehicle);
        }
    }

    @RequestMapping(value = "${path.companies}/{id}/${path.commissions}", method = RequestMethod.GET)
    public Collection<RESTCommission> getCustomerCommission(@PathVariable String id,
                                               @RequestHeader(value = "Authorization") String token,
                                               @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException, ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            CustomerController controller = manager.getCustomerController();
            Customer customer = controller.get(toUUID(id));
            return translateMap(customer.getCommissions());
        }
    }

    @RequestMapping(value = "${path.companies}/{id}/${path.commissions}", method = RequestMethod.PUT)
    public Collection<RESTCommission> putCustomerCommission(@PathVariable String id,
                                               @RequestHeader(value = "Authorization") String token,
                                               @RequestHeader(value = "Function") String function,
                                               @RequestBody Collection<RESTCommission> commissions) throws UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException, DataAccessException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            CustomerController controller = manager.getCustomerController();
            Customer customer = controller.get(toUUID(id));

            customer.setCommissions(translateCommissions(commissions));
            controller.update(customer);

            return commissions;
        }
    }

    @RequestMapping(value = "${path.companies}/{id}/${path.commissions}", method = RequestMethod.DELETE)
    public void deleteCustomerCommission(@PathVariable String id,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException, ObjectNotFoundException, ConstraintViolationException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            CustomerController controller = manager.getCustomerController();
            Customer customer = controller.get(toUUID(id));

            customer.setCommissions(null);
            controller.update(customer);
        }
    }
}
