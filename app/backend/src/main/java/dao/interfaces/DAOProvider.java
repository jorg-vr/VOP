package dao.interfaces;

import model.insurance.Insurance;
import model.fleet.Subfleet;
import model.fleet.Vehicle;

public interface DAOProvider extends AutoCloseable {


    InsuranceDAO getInsuranceDao();

    VehicleDAO getVehicleDao();

    HistoryDAO<Vehicle> getVehicleHistoryDAO();

    HistoryDAO<Insurance> getInsuranceHistoryDAO();

    HistoryDAO<Subfleet> getSubfleetHistoryDAO();

    @Override
    void close();
}
