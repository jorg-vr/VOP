package spring.controller.insurance;

import controller.AbstractController;
import controller.ControllerFactory;
import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.ContractController;
import controller.insurance.VehicleInsuranceController;
import dao.interfaces.DataAccessException;
import model.insurance.VehicleInsurance;
import org.springframework.web.bind.annotation.*;
import spring.controller.RESTAbstractController;
import spring.model.AuthenticationToken;
import spring.model.RESTModelFactory;
import spring.model.RESTSchema;
import spring.model.insurance.RESTContract;
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
                                                @RequestHeader(value = "Authorization") String token,
                                                @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleInsuranceController controller = manager.getVehicleInsuranceController();
            Collection<RESTVehicleInsurance> restModels = controller.getAll()
                    .stream()
                    .map(RESTVehicleInsurance::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(restModels, page, limit, request);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
