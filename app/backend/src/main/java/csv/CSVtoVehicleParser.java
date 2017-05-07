package csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import dao.database.ProductionProvider;
import model.fleet.Vehicle;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam on 5/7/17.
 */
public class CSVtoVehicleParser {

    public static Collection<Vehicle> parse(InputStream stream){
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

        try(CSVReader reader = new CSVReader(new InputStreamReader(new BufferedInputStream(stream)))){
            return csvToBean.parse(strategy, reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        ProductionProvider.initializeProvider("localtest");

        InputStream inputStream = CSVtoVehicleParser.class.getClassLoader().getResourceAsStream("csv/example.csv");
        for(Vehicle vehicle: parse(inputStream)){
            System.out.println(vehicle);
        }
        ProductionProvider.getInstance().close();
    }
}
