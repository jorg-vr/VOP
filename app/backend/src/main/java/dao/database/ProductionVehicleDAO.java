package dao.database;

import dao.exceptions.ConstraintViolationException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Customer;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by sam on 3/8/17.
 */
public class ProductionVehicleDAO extends ProductionDAO<Vehicle> implements VehicleDAO {

    public ProductionVehicleDAO(Session session) {
        super(session,Vehicle.class);
    }

    @Override
    public void validateVehicles(Collection<Vehicle> vehicles) throws ConstraintViolationException {
        for(Vehicle vehicle: vehicles){
            HibernateUtil.validate(getSession(),vehicle);
        }
    }

    @Override
    public Filter<Vehicle> byBrand(String brandName) {
        return filterContains("brand",brandName);
    }

    @Override
    public Filter<Vehicle> byModel(String model) {
        return filterContains("model",model);
    }

    @Override
    public Filter<Vehicle> byLicensePlate(String licensePlate) {
        return filterContains("licensePlate",licensePlate);
    }

    @Override
    public Filter<Vehicle> atProductionDate(LocalDate productionDate) {
        return filterEqual("year",productionDate);
    }

    @Override
    public Filter<Vehicle> beforeProductionDate(LocalDate productionDate) {
        if(productionDate==null){
            return ()->{};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().lessThanOrEqualTo(getRoot().<LocalDate>get("year"), productionDate));
    }

    @Override
    public Filter<Vehicle> afterProductionDate(LocalDate productionDate) {
        if(productionDate==null){
            return ()->{};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().greaterThanOrEqualTo(getRoot().<LocalDate>get("year"), productionDate));
    }

    @Override
    public Filter<Vehicle> atLeastMileage(int mileage) {
        return () ->
                getPredicates().add(getCriteriaBuilder().ge(getRoot().get("mileage"), mileage));
    }

    @Override
    public Filter<Vehicle> maxMileage(int mileage) {
        return () ->
                getPredicates().add(getCriteriaBuilder().le(getRoot().get("mileage"), mileage));

    }

    @Override
    public Filter<Vehicle> byType(VehicleType type) {
        return filterEqual("type",type);
    }

    @Override
    public Filter<Vehicle> byFleet(Fleet fleet) {
        return filterEqual("fleet",fleet);
    }

    @Override
    public Filter<Vehicle> byCustomer(Customer customer) {
        if(customer==null){
            return () -> {};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().equal(getRoot().get("fleet").get("owner"), customer));
    }
}
