package spring.controller;

import com.opencsv.exceptions.CsvException;
import controller.AbstractController;
import controller.ControllerManager;
import controller.FleetController;
import controller.VehicleController;
import controller.exceptions.UnAuthorizedException;
import csv.CSVtoVehicleParser;
import csv.InvalidCSVHeaderException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.identity.Customer;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.files.FileSystemStorageService;
import spring.files.StorageService;
import spring.model.AuthenticationToken;
import spring.model.RESTFleet;
import spring.model.RESTSchema;
import spring.model.RESTVehicle;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * This controller is responsible for handling the HTTP requests of the URLs /fleets and /companies/{companyId}/fleets.
 * Currently, the following HTTP requests are supported:
 * 1)  GET /fleet
 * 2)  GET /fleets/{id}
 * 3)  POST /fleets
 * 4)  PUT /fleets/{id}
 * 5)  DELETE /fleets/{id}
 * 6)  GET /companies/{companyId}/fleet
 * 7)  GET /companies/{companyId}/fleets/{id}
 * 8)  POST /companies/{companyId}/fleets
 * 9)  PUT /companies/{companyId}/fleets/{id}
 * 10) DELETE /companies/{companyId}/fleets/{id}
 * 11) POST /fleets/{id}/vehicles/import
 * 12) GET /fleets/{id}/vehicles/import/example
 * <p>
 * This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 * of the spring independent controllers,  located in the controller package.
 * It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 * <p>
 * For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping(value = {"/${path.fleets}", "/${path.companies}/{companyId}/${path.fleets}"})
public class RESTFleetController extends RESTAbstractController<RESTFleet, Fleet> {

    private StorageService storageService = new FileSystemStorageService();

    public RESTFleetController() {
        super(RESTFleet::new);
    }

    @Override
    public AbstractController<Fleet> getController(ControllerManager manager) {
        return manager.getFleetController();
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTFleet> get(HttpServletRequest request,
                                     @PathVariable Optional<String> companyId,
                                     @RequestParam(required = false) String company,
                                     @RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer limit,
                                     @RequestHeader(value = "Authorization") String token,
                                     @RequestHeader(value = "Function") String function) throws UnAuthorizedException, DataAccessException {
        if (companyId.isPresent()) {
            company = companyId.get();
        }

        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            FleetController controller = manager.getFleetController();

            Customer owner = company != null ? new Customer(toUUID(company)) : null;

            Collection<RESTFleet> result = controller.getFiltered(owner)
                    .stream()
                    .map(RESTFleet::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(result, page, limit, request);
        }
    }

    /**
     * Import a CVS file of fleets
     *
     * @throws ConstraintViolationException The fields of a vehicle are not correct
     * @throws DataAccessException          Something went wrong with the database
     * @throws ObjectNotFoundException      There is no fleet with that id
     * @throws InvalidCSVHeaderException    The headers of the csv file are invalid
     * @throws CsvException                 a vehicle in the csv file does have invalid data
     */
    @PostMapping("/{id}/vehicles/${path.import}")
    public Collection<RESTVehicle> importCSV(@RequestHeader(value = "Authorization") String token,
                                             @RequestHeader(value = "Function") String function,
                                             @PathVariable String id, @RequestParam("file") MultipartFile file)
            throws ConstraintViolationException, DataAccessException, UnAuthorizedException, ObjectNotFoundException, InvalidCSVHeaderException, CsvException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            VehicleController controller = manager.getVehicleController();

            Collection<Vehicle> vehicles = CSVtoVehicleParser.parse(file.getInputStream());
            Fleet fleet = manager.getFleetController().get(toUUID(id));

            Collection<RESTVehicle> restVehicles = new ArrayList<>();
            for (Vehicle vehicle : vehicles) {
                vehicle.setFleet(fleet);
                restVehicles.add(new RESTVehicle(controller.create(vehicle)));
            }
            return restVehicles;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return a template for importing csv files
     * @param id doesn't matter
     */
    @GetMapping("/{id}/vehicles/${path.import}/${path.example}")
    @ResponseBody
    public ResponseEntity<Resource> serveExample(@PathVariable String id) {
        Resource file = storageService.loadAsResource("example.csv");
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "example.csv" + "\"")
                .body(file);
    }
}
