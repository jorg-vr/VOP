package model.insurance;

import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Billie Devolder on 18/04/2017.
 */
public class VehicleInsuranceTest {

    private static final SuretyType suretyType = SuretyType.OMNIUM_FULL;

    private VehicleInsurance vehicleInsurance;

    @Before
    public void init() {
        vehicleInsurance = new VehicleInsurance();
        vehicleInsurance.setInsuredValue(100);

        Vehicle vehicle = new Vehicle();
        VehicleType vehicleType = new VehicleType();
        vehicleType.setCommission(suretyType, 0.1);
        vehicleType.setTax(suretyType, 0.5);
        vehicle.setType(vehicleType);
        Fleet fleet = new Fleet();
        fleet.setOwner(new Customer());
        vehicle.setFleet(fleet);
        vehicleInsurance.setVehicle(vehicle);

        FlatSurety surety = new FlatSurety();
        surety.setPremium(1000);
        surety.setSuretyType(suretyType);
        vehicleInsurance.setSurety(surety);
    }

    @Test
    public void calculateCost() throws Exception {
        assertEquals(1100, vehicleInsurance.calculateCost());
    }

    @Test
    public void calculateTax() throws Exception {
        assertEquals(500, vehicleInsurance.calculateTax());
    }
}