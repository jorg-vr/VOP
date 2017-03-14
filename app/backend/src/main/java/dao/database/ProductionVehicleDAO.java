package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by sam on 3/8/17.
 */
public class ProductionVehicleDAO implements VehicleDAO {

    private final SessionFactory factory;
    private CriteriaQuery<Vehicle> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;
    private Root<Vehicle> root;
    private Collection<Predicate> predicates = new ArrayList<>();

    public ProductionVehicleDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Vehicle create(String brand, String model, String chassisNumber, String licenseplate, int value, int mileage, VehicleType type, LocalDate productionDate) throws DataAccessException {

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setLicensePlate(licenseplate);
        vehicle.setModel(model);
        vehicle.setProductionDate(productionDate);
        vehicle.setValue(value);
        vehicle.setMileage(mileage);
        vehicle.setType(type);
        vehicle.setChassisNumber(chassisNumber);
        HibernateUtil.create(factory,vehicle);
        return vehicle;
    }

    @Override
    public Vehicle get(UUID id) throws DataAccessException {
        try (Session session = factory.openSession()) {
            return session.get(Vehicle.class, id);
        }
    }

    @Override
    public Vehicle update(UUID uuid, String brand, String model, String chassisNumber, String licenseplate, int value, int mileage, VehicleType type, LocalDate productionDate) throws DataAccessException {

        Vehicle vehicle = new Vehicle();
        vehicle.setUuid(uuid);
        vehicle.setBrand(brand);
        vehicle.setLicensePlate(licenseplate);
        vehicle.setModel(model);
        vehicle.setProductionDate(productionDate);
        vehicle.setValue(value);
        vehicle.setMileage(mileage);
        vehicle.setType(type);
        vehicle.setChassisNumber(chassisNumber);
        HibernateUtil.update(factory,vehicle);
        return vehicle;
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(factory,get(id));
    }

    @Override
    public Collection<Vehicle> listFiltered(Filter<Vehicle>[] filters) throws DataAccessException {
        Transaction tx = null;
        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(Vehicle.class);
            this.root = this.criteriaQuery.from(Vehicle.class);
            for (Filter<Vehicle> filter : filters) {
                filter.filter();
            }
            Collection<Vehicle> vehicles = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
            predicates.clear();
            return vehicles;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {

    }



    @Override
    public Filter<Vehicle> byBrand(String brandName) {
        return () ->
            predicates.add(criteriaBuilder.equal(root.get("brand"), brandName));
    }

    @Override
    public Filter<Vehicle> byModel(String model) {
        return () ->
            predicates.add(criteriaBuilder.equal(root.get("model"), model));
    }

    @Override
    public Filter<Vehicle> byLicensePlate(String licensePlate) {
        return () ->
            predicates.add(criteriaBuilder.equal(root.get("licensePlate"), licensePlate));
    }

    @Override
    public Filter<Vehicle> atProductionDate(LocalDate productionDate) {
        return () ->
            predicates.add(criteriaBuilder.equal(root.<LocalDate>get("productionDate"), productionDate));
    }

    @Override
    public Filter<Vehicle> beforeProductionDate(LocalDate productionDate) {
        return () ->
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<LocalDate>get("productionDate"), productionDate));
    }

    @Override
    public Filter<Vehicle> afterProductionDate(LocalDate productionDate) {
        return () ->
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<LocalDate>get("productionDate"), productionDate));
    }

    @Override
    public Filter<Vehicle> atLeastMileage(int mileage) {
        return () ->
            predicates.add(criteriaBuilder.ge(root.get("mileage"), mileage));
    }

    @Override
    public Filter<Vehicle> maxMileage(int mileage) {
        return () ->
            predicates.add(criteriaBuilder.le(root.get("mileage"), mileage));

    }

    @Override
    public Filter<Vehicle> byType(VehicleType type) {
        return () ->
            predicates.add(criteriaBuilder.equal(root.get("type"), type));
    }


}
