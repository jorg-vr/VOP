package src.main.java.dao;

import src.main.java.model.insurance.Insurance;
import src.main.java.model.fleet.Subfleet;
import src.main.java.model.fleet.Vehicle;

public interface DAOProvider extends AutoCloseable {

    // Only one allowed
    DAOProvider getInstance();

    InsuranceDAO getInsuranceDao();

    VehicleDAO getVehicleDao();

    SubfleetDAO getSubfleetDao();

    HistoryDAO<Vehicle> getVehicleHistoryDAO();

    HistoryDAO<Insurance> getInsuranceHistoryDAO();

    HistoryDAO<Subfleet> getSubfleetHistoryDAO();

    @Override
    void close();
}
