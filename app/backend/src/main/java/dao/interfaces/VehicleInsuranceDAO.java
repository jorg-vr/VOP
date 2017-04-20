package dao.interfaces;

import model.fleet.Vehicle;
import model.insurance.Contract;
import model.insurance.VehicleInsurance;

import java.time.LocalDate;

/**
 * Created by sam on 4/12/17.
 */
public interface VehicleInsuranceDAO extends DAO<VehicleInsurance>{

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all {@link VehicleInsurance} having the given vehicle.
     * @param vehicle The vehicle to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<VehicleInsurance> byVehicle(Vehicle vehicle);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all {@link VehicleInsurance} starting before the given date.
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<VehicleInsurance> startsBefore(LocalDate date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all {@link VehicleInsurance} ending after the given date.
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<VehicleInsurance> endsAfter(LocalDate date);
}
