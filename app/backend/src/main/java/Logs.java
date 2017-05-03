import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Customer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.UUID;

import static model.history.FieldsComparator.compareFields;

/**
 * Created by Billie Devolder on 3/05/2017.
 */
public class Logs {

    public static void main(String[] args) {

        /*
        UUID uuid = UUID.randomUUID();
        VehicleType vehicleType = new VehicleType();
        vehicleType.setUuid(UUID.randomUUID());
        vehicleType.setType("Bestelwagen");
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setUuid(uuid);
        vehicle1.setBrand("BMW");
        vehicle1.setLicensePlate("IAM-007");
        vehicle1.setType(vehicleType);

        vehicleType = new VehicleType();
        vehicleType.setUuid(UUID.randomUUID());
        vehicleType.setType("Koerier");
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setUuid(uuid);
        vehicle2.setBrand("BMW");
        vehicle2.setLicensePlate("IAM-008");
        vehicle2.setModel("Kever");
        vehicle2.setType(vehicleType);
        */
        Customer c1 = new Customer();
        c1.setName("Felicia");
        Collection<Fleet> fleets = new ArrayList<>();
        fleets.add(new Fleet(UUID.randomUUID()));
        //c1.setFleets(fleets);

        Customer c2 = new Customer();
        c2.setName("Sofie");
        fleets = new ArrayList<>();
        fleets.add(new Fleet(UUID.randomUUID()));
        c2.setFleets(fleets);

        System.out.println(compareFields(c1, c2));
    }
}
