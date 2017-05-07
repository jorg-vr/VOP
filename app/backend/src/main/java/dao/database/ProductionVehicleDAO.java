package dao.database;

import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import org.hibernate.Session;

import java.time.LocalDate;

/**
 * Created by sam on 3/8/17.
 */
public class ProductionVehicleDAO extends ProductionDAO<Vehicle> implements VehicleDAO {

    public ProductionVehicleDAO(Session session) {
        super(session,Vehicle.class);
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
        return filterEqual("productionDate",productionDate);
    }

    @Override
    public Filter<Vehicle> beforeProductionDate(LocalDate productionDate) {
        if(productionDate==null){
            return ()->{};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().lessThanOrEqualTo(getRoot().<LocalDate>get("productionDate"), productionDate));
    }

    @Override
    public Filter<Vehicle> afterProductionDate(LocalDate productionDate) {
        if(productionDate==null){
            return ()->{};
        }
        return () ->
                getPredicates().add(getCriteriaBuilder().greaterThanOrEqualTo(getRoot().<LocalDate>get("productionDate"), productionDate));
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
}
