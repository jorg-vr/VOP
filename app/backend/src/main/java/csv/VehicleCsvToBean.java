package csv;

import com.opencsv.bean.CsvToBean;
import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.DAOProvider;
import dao.interfaces.VehicleTypeDAO;
import model.fleet.Vehicle;
import model.fleet.VehicleType;

import java.beans.PropertyDescriptor;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by sam on 5/7/17.
 */
class VehicleCsvToBean extends CsvToBean<Vehicle> {
    @Override
    protected Object convertValue(String value, PropertyDescriptor prop) throws InstantiationException, IllegalAccessException {
        if (prop.getName().equals("productionDate")) {
            // return an custom object based on the incoming value
            return LocalDate.of(Integer.parseInt(value), 1, 1);
        }
        if (prop.getName().equals("type")) {
            try(DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                VehicleTypeDAO vehicleTypeDAO = manager.getVehicleTypeDAO();
                try {
                    Collection<VehicleType> types = vehicleTypeDAO.listFiltered(vehicleTypeDAO.byName(value));
                    if (types.size() != 1) {
                        throw new InstantiationException("Not a valid vehicletype");
                    }
                    return types.iterator().next();
                } catch (DataAccessException e) {
                    throw new InstantiationException("Not a valid vehicletype");
                }
            }
        }
        return super.convertValue(value, prop);
    }
}
