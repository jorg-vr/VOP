package dao;

import model.Insurance;
import model.Subfleet;
import model.Vehicle;

/**
 * Created by sam on 2/26/17.
 */
public interface DAOProvider extends AutoCloseable {

    //Only one allowed
    public DAOProvider getInstance();

    public InsuranceDAO getInsuranceDao();

    public VehicleDAO getVehicleDao();

    public SubfleetDAO getSubfleetDao();

    public HistoryDAO<Vehicle> getVehicleHistoryDAO();

    public HistoryDAO<Insurance> getInsuranceHistoryDAO();

    public HistoryDAO<Subfleet> getSubfleetHistoryDAO();

    @Override
    public void close();
}
