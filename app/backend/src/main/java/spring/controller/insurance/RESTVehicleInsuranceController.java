package spring.controller.insurance;

import controller.AbstractController;
import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.VehicleInsuranceController;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.fleet.Vehicle;
import model.insurance.Contract;
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
                                                @PathVariable String id,
                                                Integer page, Integer limit,
                                                String vehicleId,
                                                @RequestHeader(value = "Authorization") String token,
                                                @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleInsuranceController controller = manager.getVehicleInsuranceController();

            Vehicle vehicle=null;
            try {
                vehicle=vehicleId==null||vehicleId.equals("undefined")?null:manager.getVehicleController().get(toUUID(vehicleId));
            } catch (ObjectNotFoundException e) {}
            Contract contract=null;
            if(id!=null&&!id.equals("undefined")) {
                contract = new Contract();
                contract.setUuid(toUUID(id));
            }

            Collection<RESTVehicleInsurance> restModels = controller.getFiltered(contract,vehicle)
                    .stream()
                    .map(RESTVehicleInsurance::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(restModels, page, limit, request);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
