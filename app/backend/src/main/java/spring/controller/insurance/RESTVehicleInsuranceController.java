package spring.controller.insurance;

import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import controller.insurance.VehicleInsuranceController;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.fleet.Vehicle;
import model.insurance.Contract;
import model.insurance.VehicleInsurance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pdf.GreenCard;
import pdf.PdfException;
import spring.model.AuthenticationToken;
import spring.model.RESTSchema;
import spring.model.insurance.RESTVehicleInsurance;
import spring.model.insurance.RESTVehicleInsuranceContainer;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

@RestController
@RequestMapping("${path.contracts}/{id}/${path.vehicle_insurances}")
public class RESTVehicleInsuranceController {

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

            Vehicle vehicle = null;
            try {
                vehicle = vehicleId == null || vehicleId.equals("undefined") ? null : manager.getVehicleController().get(toUUID(vehicleId));
            } catch (ObjectNotFoundException e) {
            }
            Contract contract = null;
            if (id != null && !id.equals("undefined")) {
                contract = new Contract();
                contract.setUuid(toUUID(id));
            }

            Collection<RESTVehicleInsurance> restModels = controller.getFiltered(contract, vehicle)
                    .stream()
                    .map(RESTVehicleInsurance::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(restModels, page, limit, request);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a correction for a new vehicle insurance
     *
     * @param insurance date = date when the insurance should have been added
     *                  vehicleInsurance = insurance object that should have been added
     */
    @RequestMapping(method = RequestMethod.POST)
    public VehicleInsurance createCorrection(@RequestBody RESTVehicleInsurance insurance, @RequestHeader(value = "Authorization") String token,
                                             @RequestHeader(value = "Function") String function) throws DataAccessException, UnAuthorizedException, ConstraintViolationException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleInsuranceController controller = manager.getVehicleInsuranceController();
            return controller.create(insurance.translate(manager));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public VehicleInsurance putCorrection(@PathVariable String id, @RequestBody RESTVehicleInsuranceContainer container,
                                          @RequestHeader(value = "Authorization") String token,
                                          @RequestHeader(value = "Function") String function) throws DataAccessException, UnAuthorizedException, ConstraintViolationException, ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            RESTVehicleInsurance insurance = container.getVehicleInsurance();
            insurance.setId(id);
            VehicleInsuranceController controller = manager.getVehicleInsuranceController();
            return controller.update(container.getVehicleInsurance().translate(manager), container.getDate());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCorrection(@PathVariable String id, @RequestBody LocalDate localDate,
                                 @RequestHeader(value = "Authorization") String token,
                                 @RequestHeader(value = "Function") String function) throws DataAccessException, UnAuthorizedException, ConstraintViolationException, ObjectNotFoundException {
        UUID uuid = toUUID(id);
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleInsuranceController controller = manager.getVehicleInsuranceController();
            controller.archive(uuid, localDate);
        }
    }


    @GetMapping("/{id}/${path.green_card}")
    @ResponseBody
    public HttpEntity<byte[]> getGreenCard(@PathVariable("id") String id, @RequestHeader(value = "Authorization") String token,
                                           @RequestHeader(value = "Function") String function) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, PdfException {
        UUID uuid = toUUID(id);
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleInsuranceController controller = manager.getVehicleInsuranceController();
            VehicleInsurance insurance = controller.get(uuid);
            byte[] documentBody = new GreenCard(insurance).getAsByteArray();

            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_PDF);
            header.set(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=" + "groene_kaart.pdf");
            header.setContentLength(documentBody.length);
            return new HttpEntity<>(documentBody, header);
        }

    }
}
