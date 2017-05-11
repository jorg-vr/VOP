package csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.exceptions.CsvException;
import dao.database.ProductionProvider;
import dao.database.util.unique.ConstraintValidatorFactoryImpl;
import dao.exceptions.*;
import dao.exceptions.ConstraintViolationException;
import dao.interfaces.DAOManager;
import dao.interfaces.VehicleDAO;
import model.fleet.Vehicle;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;

import javax.validation.*;
import java.io.*;
import java.util.*;

/**
 * Created by sam on 5/7/17.
 */
public class CSVtoVehicleParser {
    
    public static Collection<Vehicle> parse(InputStream stream) throws InvalidCSVHeaderException, CsvException, ConstraintViolationException {
        CsvToBean<Vehicle> csvToBean = new VehicleCsvToBean();

        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("Merk", "brand");
        columnMapping.put("Model", "model");
        columnMapping.put("Nummerplaat", "licensePlate");
        columnMapping.put("Productiejaar", "productionDate");
        columnMapping.put("Kilometerstand", "mileage");
        columnMapping.put("Voertuigtype","type");
        columnMapping.put("Chassisnummer","chassisNumber");

        HeaderColumnNameTranslateMappingStrategy<Vehicle> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
        strategy.setType(Vehicle.class);
        strategy.setColumnMapping(columnMapping);

        try {
            byte[] csv = IOUtils.toByteArray(stream);
            try(CSVReader reader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(csv)));
                CSVReader headerReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(csv)));
                DAOManager manager = ProductionProvider.getInstance().getDaoManager()){
                List<String> header = new ArrayList<>(Arrays.asList(headerReader.readNext()));
                for(String s: columnMapping.keySet()){
                    if(!header.contains(s)){
                        throw new InvalidCSVHeaderException("Wrong header: '" + s + "' is missing");
                    }
                }

                Collection<Vehicle> vehicles  = csvToBean.parse(strategy, reader);
                manager.getVehicleDAO().validateVehicles(vehicles);
                return vehicles;
            }
        } catch(ConstraintViolationException e){
            throw e;
        }catch (Exception e) {
            throw new CsvException();
        }
    }
}
