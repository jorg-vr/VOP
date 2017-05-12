package spring.controller.insurance;

import controller.AbstractController;
import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.VehicleInsuranceController;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.fleet.Vehicle;
import model.insurance.VehicleInsurance;
import org.springframework.web.bind.annotation.*;
import spring.controller.RESTAbstractController;
import spring.model.AuthenticationToken;
import spring.model.RESTSchema;
import spring.model.insurance.RESTVehicleInsurance;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

@RestController
@RequestMapping("${path.contracts}/{id}/${path.vehicle_insurances}")
public class RESTVehicleInsuranceController extends RESTAbstractController<RESTVehicleInsurance, VehicleInsurance> {

    public RESTVehicleInsuranceController() {
        super(RESTVehicleInsurance::new);
    }

    @Override
    public AbstractController<VehicleInsurance> getController(ControllerManager manager) {
        return manager.getVehicleInsuranceController();
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTVehicleInsurance> get(HttpServletRequest request,
                                                Integer page, Integer limit,
                                                String vehicleId,
                                                @RequestHeader(value = "Authorization") String token,
                                                @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleInsuranceController controller = manager.getVehicleInsuranceController();
            Vehicle vehicle=null;
            try {
                vehicle=manager.getVehicleController().get(toUUID(vehicleId));
            } catch (ObjectNotFoundException e) {}
            Collection<RESTVehicleInsurance> restModels = controller.getBy(vehicle)
                    .stream()
                    .map(RESTVehicleInsurance::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(restModels, page, limit, request);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
