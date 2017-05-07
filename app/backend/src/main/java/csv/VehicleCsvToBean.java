package csv;

import com.opencsv.bean.CsvToBean;
import model.fleet.Vehicle;

import java.beans.PropertyDescriptor;

/**
 * Created by sam on 5/7/17.
 */
public class VehicleCsvToBean extends CsvToBean<Vehicle> {
    @Override
    protected Object convertValue(String value, PropertyDescriptor prop) throws InstantiationException, IllegalAccessException {
        return super.convertValue(value, prop);
    }
}
