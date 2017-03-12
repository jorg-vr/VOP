package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

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
    public Vehicle create(Vehicle vehicle) throws DataAccessException {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(vehicle);
            transaction.commit();
            return vehicle;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataAccessException();
        }
    }

    @Override
    public Vehicle get(UUID id) throws DataAccessException {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            return session.get(Vehicle.class, id);
        }
    }

    @Override
    public void update(Vehicle vehicle) throws DataAccessException {
        Transaction tx = null;
        try (Session session = factory.openSession();) {
            tx = session.beginTransaction();
            session.update(vehicle);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Vehicle vehicle) throws DataAccessException {

        Transaction tx = null;
        try (Session session = factory.openSession();) {
            tx = session.beginTransaction();
            session.delete(vehicle);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
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
                filter.filter(null);
            }
            Collection<Vehicle> vehicles = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
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
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("brand"), brandName));
            return true;
        };
    }

    @Override
    public Filter<Vehicle> byModel(String model) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("model"), model));
            return true;
        };
    }

    @Override
    public Filter<Vehicle> byLicensePlate(String licensePlate) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("licensePlate"), licensePlate));
            return true;
        };
    }

    @Override
    public Filter<Vehicle> atProductionDate(LocalDate productionDate) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.<LocalDate>get("productionDate"), productionDate));
            return true;
        };
    }

    @Override
    public Filter<Vehicle> beforeProductionDate(LocalDate productionDate) {
        return (o1) -> {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.<LocalDate>get("productionDate"), productionDate));
            return true;
        };
    }

    @Override
    public Filter<Vehicle> afterProductionDate(LocalDate productionDate) {
        return (o1) -> {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.<LocalDate>get("productionDate"), productionDate));
            return true;
        };
    }

    @Override
    public Filter<Vehicle> atLeastMileage(int mileage) {
        return (o1) -> {
            predicates.add(criteriaBuilder.ge(root.get("mileage"), mileage));
            return true;
        };
    }

    @Override
    public Filter<Vehicle> maxMileage(int mileage) {
        return (o1) -> {
            predicates.add(criteriaBuilder.le(root.get("mileage"), mileage));
            return true;
        };
    }

    @Override
    public Filter<Vehicle> byType(VehicleType type) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("type"), type));
            return true;
        };
    }


}
