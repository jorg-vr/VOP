package spring.controller.insurance;

import controller.ControllerFactory;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.ContractController;
import controller.insurance.VehicleInsuranceController;
import dao.interfaces.DataAccessException;
import model.insurance.VehicleInsurance;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.controller.RESTAbstractController;
import spring.model.RESTModelFactory;
import spring.model.RESTSchema;
import spring.model.insurance.RESTContract;
import spring.model.insurance.RESTVehicleInsurance;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${path.contracts}/{id}/${path.vehicle_insurances}")
public class RESTVehicleInsuranceController extends RESTAbstractController<RESTVehicleInsurance, VehicleInsurance> {

    public RESTVehicleInsuranceController() {
        super(VehicleInsuranceController::new, RESTVehicleInsurance::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTVehicleInsurance> get(HttpServletRequest request,
                                        Integer page, Integer limit,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        try (VehicleInsuranceController controller = new VehicleInsuranceController(verifyToken(token, function))) {
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
