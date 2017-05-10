package spring.controller.insurance;

import controller.*;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.CommissionContainerController;
import controller.insurance.CommissionContainerControllerFactory;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.CommissionContainer;
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

    private  Collection<RESTCommission> getCommissions(String id,
                                                       String token,
                                                       String function,
                                                       CommissionContainerControllerFactory controllerFactory)
            throws DataAccessException, UnAuthorizedException, ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            CommissionContainerController controller = controllerFactory.create(manager);
            return translateMap(controller.getCommissions(toUUID(id)));
        }
    }

    private  Collection<RESTCommission> putCommissions(String id,
                                                    String token,
                                                    String function,
                                                    Collection<RESTCommission> commissions,
                                                    CommissionContainerControllerFactory controllerFactory)
            throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            CommissionContainerController controller = controllerFactory.create(manager);
            return translateMap(controller.setCommissions(toUUID(id),translateCommissions(commissions)));
        }
    }

    private  void deleteCommissions(String id,
                                    String token,
                                    String function,
                                    CommissionContainerControllerFactory controllerFactory)
            throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            CommissionContainerController controller = controllerFactory.create(manager);
            controller.setCommissions(toUUID(id),null);
        }
    }

    @RequestMapping(value = "${path.vehicles}/${path.types}/{id}/${path.commissions}", method = RequestMethod.GET)
    public Collection<RESTCommission> getVehicleTypeCommission(@PathVariable String id,
                                                   @RequestHeader(value = "Authorization") String token,
                                                   @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException, ObjectNotFoundException {
        return getCommissions(id,token,function, ControllerManager::getVehicleTypeController);
    }

    @RequestMapping(value = "${path.vehicles}/${path.types}/{id}/${path.commissions}", method = RequestMethod.PUT)
    public Collection<RESTCommission> putVehicleTypeCommission(@PathVariable String id,
                                                   @RequestHeader(value = "Authorization") String token,
                                                   @RequestHeader(value = "Function") String function,
                                                   @RequestBody Collection<RESTCommission> commissions) throws UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException, DataAccessException {
        return putCommissions(id,token,function,commissions,ControllerManager::getVehicleTypeController);
    }

    @RequestMapping(value = "${path.vehicles}/{id}/${path.commissions}", method = RequestMethod.GET)
    public Collection<RESTCommission> getVehicleCommission(@PathVariable String id,
                                                @RequestHeader(value = "Authorization") String token,
                                                @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ObjectNotFoundException, DataAccessException {
        return getCommissions(id,token,function, ControllerManager::getVehicleController);
    }

    @RequestMapping(value = "${path.vehicles}/{id}/${path.commissions}", method = RequestMethod.PUT)
    public Collection<RESTCommission> putVehicleCommission(@PathVariable String id,
                                               @RequestHeader(value = "Authorization") String token,
                                               @RequestHeader(value = "Function") String function,
                                               @RequestBody Collection<RESTCommission> commissions) throws UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException, DataAccessException {
        return putCommissions(id,token,function,commissions,ControllerManager::getVehicleController);
    }

    @RequestMapping(value = "${path.vehicles}/{id}/${path.commissions}", method = RequestMethod.DELETE)
    public void deleteVehicleCommission(@PathVariable String id,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ConstraintViolationException, DataAccessException, ObjectNotFoundException {
        deleteCommissions(id,token,function,ControllerManager::getVehicleController);
    }

    @RequestMapping(value = "${path.companies}/{id}/${path.commissions}", method = RequestMethod.GET)
    public Collection<RESTCommission> getCustomerCommission(@PathVariable String id,
                                               @RequestHeader(value = "Authorization") String token,
                                               @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException, ObjectNotFoundException {
        return getCommissions(id,token,function, ControllerManager::getCustomerController);
    }

    @RequestMapping(value = "${path.companies}/{id}/${path.commissions}", method = RequestMethod.PUT)
    public Collection<RESTCommission> putCustomerCommission(@PathVariable String id,
                                               @RequestHeader(value = "Authorization") String token,
                                               @RequestHeader(value = "Function") String function,
                                               @RequestBody Collection<RESTCommission> commissions) throws UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException, DataAccessException {
        return putCommissions(id,token,function,commissions, ControllerManager::getCustomerController);
    }

    @RequestMapping(value = "${path.companies}/{id}/${path.commissions}", method = RequestMethod.DELETE)
    public void deleteCustomerCommission(@PathVariable String id,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException, ObjectNotFoundException, ConstraintViolationException {
        deleteCommissions(id,token,function, ControllerManager::getCustomerController);
    }
}
