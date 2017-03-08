package dao.interfaces;

import model.insurance.Insurance;
import model.fleet.Vehicle;

public interface DAOProvider extends AutoCloseable {

    // Only one allowed
    DAOProvider getInstance();

    InsuranceDAO getInsuranceDao();

    VehicleDAO getVehicleDao();

    HistoryDAO<Vehicle> getVehicleHistoryDAO();

    HistoryDAO<Insurance> getInsuranceHistoryDAO();

    @Override
    void close();
}
