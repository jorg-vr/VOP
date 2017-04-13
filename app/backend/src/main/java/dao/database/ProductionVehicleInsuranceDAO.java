package dao.database;

import dao.interfaces.DAO;
import dao.interfaces.Filter;
import dao.interfaces.VehicleInsuranceDAO;
import model.fleet.Vehicle;
import model.insurance.VehicleInsurance;
import org.hibernate.Session;

import java.time.LocalDate;

/**
 * Created by sam on 4/13/17.
 */
public class ProductionVehicleInsuranceDAO extends ProductionDAO<VehicleInsurance> implements VehicleInsuranceDAO {

    public ProductionVehicleInsuranceDAO(Session session) {
        super(session, VehicleInsurance.class);
    }

    @Override
    public Filter<VehicleInsurance> byVehicle(Vehicle vehicle) {
         return filterEqual("vehicle",vehicle);
    }

    @Override
    public Filter<VehicleInsurance> startsBefore(LocalDate date) {
        return () ->
                getPredicates().add(getCriteriaBuilder().lessThanOrEqualTo(getRoot().<LocalDate>get("startDate"), date));
    }

    @Override
    public Filter<VehicleInsurance> endsAfter(LocalDate date) {
        return () ->
                getPredicates().add(getCriteriaBuilder().lessThanOrEqualTo(getRoot().<LocalDate>get("endDate"), date));
    }
}
