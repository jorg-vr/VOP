package dao;

import model.insurance.Insurance;
import model.fleet.Subfleet;
import model.fleet.Vehicle;

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
