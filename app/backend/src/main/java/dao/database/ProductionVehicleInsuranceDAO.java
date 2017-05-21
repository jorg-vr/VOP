package dao.database;

import dao.interfaces.DAO;
import dao.interfaces.Filter;
import dao.interfaces.VehicleInsuranceDAO;
import model.fleet.Vehicle;
import model.insurance.VehicleInsurance;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public Filter<VehicleInsurance> startsBefore(LocalDateTime date) {
        if(date==null){
            return ()->{};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().lessThanOrEqualTo(getRoot().get("startDate"), date));
    }

    @Override
    public Filter<VehicleInsurance> endsAfter(LocalDateTime date) {
        if(date==null){
            return ()->{};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().lessThanOrEqualTo(getRoot().get("endDate"), date));
    }
}
