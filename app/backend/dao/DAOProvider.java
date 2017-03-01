package dao;

import model.Insurance;
import model.Subfleet;
import model.Vehicle;

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
