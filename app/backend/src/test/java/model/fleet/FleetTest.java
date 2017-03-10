package model.fleet;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ponti on 10/03/2017.
 */
public class FleetTest {
    @Test
    public void addAndRemoveVehicle() throws Exception {
        Fleet fleet = new Fleet();
        Vehicle vehicle = new Vehicle();
        assertTrue(fleet.addVehicle(vehicle));
        assertTrue(fleet.getVehicles().contains(vehicle));
        assertTrue(fleet.removeVehicle(vehicle));
        assertFalse(fleet.getVehicles().contains(vehicle));
    }

}