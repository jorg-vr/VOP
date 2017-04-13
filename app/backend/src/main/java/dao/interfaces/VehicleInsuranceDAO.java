package dao.interfaces;

import model.fleet.Vehicle;
import model.insurance.Contract;
import model.insurance.VehicleInsurance;

import java.time.LocalDate;

/**
 * Created by sam on 4/12/17.
 */
public interface VehicleInsuranceDAO extends DAO<VehicleInsurance>{
    Filter<VehicleInsurance> byVehicle(Vehicle vehicle);

    Filter<VehicleInsurance> startsBefore(LocalDate date);

    Filter<VehicleInsurance> endsAfter(LocalDate date);
}
