package dao.interfaces;

import model.insurance.Insurance;
import model.fleet.Vehicle;

public interface DAOProvider extends AutoCloseable {


    InsuranceDAO getInsuranceDao();

    VehicleDAO getVehicleDao();

    HistoryDAO<Vehicle> getVehicleHistoryDAO();

    HistoryDAO<Insurance> getInsuranceHistoryDAO();

    @Override
    void close();
}
