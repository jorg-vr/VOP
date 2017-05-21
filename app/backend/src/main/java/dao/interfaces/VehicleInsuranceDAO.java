package dao.interfaces;

import model.fleet.Vehicle;
import model.insurance.Contract;
import model.insurance.VehicleInsurance;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DAO for bean VehicleInsurance
 * Created by sam on 4/12/17.
 */
public interface VehicleInsuranceDAO extends DAO<VehicleInsurance>{

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all {@link VehicleInsurance} having the given vehicle.
     * @param vehicle The vehicle to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<VehicleInsurance> byVehicle(Vehicle vehicle);

    Filter<VehicleInsurance> byContract(Contract contract);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all {@link VehicleInsurance} starting before the given date.
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<VehicleInsurance> startsBefore(LocalDateTime date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all {@link VehicleInsurance} ending after the given date.
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<VehicleInsurance> endsAfter(LocalDateTime date);
}
