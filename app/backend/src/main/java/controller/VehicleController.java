package controller;

import controller.exceptions.UnAuthorizedException;
import controller.insurance.CommissionContainerController;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import dao.interfaces.VehicleDAO;
import model.account.Function;
import model.account.Resource;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Customer;
import model.insurance.VehicleInsurance;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * For more information of what this class does, see AbstractController
 */
public class VehicleController extends CommissionContainerController<Vehicle> {

    private VehicleDAO dao;
    private ControllerManager controllerManager;

    public VehicleController(Function function, DAOManager manager, ControllerManager controllerManager) {
        super(manager, manager.getVehicleDAO(), Resource.VEHICLE, function);
        this.dao = manager.getVehicleDAO();
        this.controllerManager = controllerManager;
    }


    @Override
    public boolean isOwner(Vehicle vehicle, Function function) {
        return vehicle.getFleet().getOwner().equals(function.getCompany());
    }

    /**
     * Retrieve all vehicles, filtered on the arguments. If an argument is null, it will be ignored
     *
     * @param licensePlate licensePlate of vehicle contains the pattern licensePlate
     * @param vin          vin of vehicle equals the pattern vin
     * @param year         production year of vehicle equals the pattern year
     * @param fleet        only return vehicles of this fleet, if the fleet does not exist in the database, an empty list will be returned
     * @param customer     only return vehicles of this customer, if the fleet does not exist in the database, an empty list will be returned
     * @param type         vehicleType of vehicle equals the pattern vehicleType
     * @return All Vehicles, filtered on arguments
     * @throws DataAccessException   Something went horribly wrong with the database
     * @throws UnAuthorizedException Function is not authorized to get all the objects.
     */
    public Collection<Vehicle> getFiltered(String licensePlate, String vin,
                                           Integer year, Fleet fleet, Customer customer, VehicleType type, String brand, String model) throws DataAccessException, UnAuthorizedException {
        // Filter vehicles on criteria that are supported by the database
        Collection<Vehicle> result = getAll(
                dao.byCustomer(customer),
                dao.byLicensePlate(licensePlate),
                dao.byFleet(fleet),
                dao.byType(type),
                dao.byBrand(brand),
                dao.byModel(model));

        // Filter vehicles on criteria that are not supported by the database
        return result.stream()
                .filter(c -> vin == null || c.getVin().toLowerCase().equals(vin.toLowerCase()))
                .filter(c -> year == null || c.getYear().getYear() == year)
                .collect(Collectors.toList());
    }

    @Override
    public void archive(UUID uuid) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException {
        for (VehicleInsurance vehicleInsurance : controllerManager.getVehicleInsuranceController().getBy(get(uuid))) {
            try {
                controllerManager.getVehicleInsuranceController().archive(vehicleInsurance.getUuid(), LocalDate.now());
            } catch (ConstraintViolationException e) {
                throw new DataAccessException(e);
            }
        }
        super.archive(uuid);
    }
}
