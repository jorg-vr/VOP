package model.identity;

import model.fleet.Vehicle;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ponti on 10/03/2017.
 */
public class LeasingCompanyTest {
    @Test
    public void addVehicle() throws Exception {
        LeasingCompany leasingCompany = new LeasingCompany();
        Vehicle vehicle = new Vehicle();

        assertTrue(leasingCompany.addVehicle(vehicle));
        assertTrue(leasingCompany.getVehicles().contains(vehicle));
        assertTrue(leasingCompany.removeVehicle(vehicle));
        assertFalse(leasingCompany.getVehicles().contains(vehicle));
    }

}