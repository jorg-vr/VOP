package model.fleet;

import org.junit.Ignore;
import org.junit.Test;
import spring.Exceptions.InvalidInputException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class VehicleTest {

    @Ignore
    @Test
    public void setLicensePlate() throws Exception {
        Vehicle vehicle = new Vehicle();
        String[] plates = {"ABC 960", "", "AZR "};
        for (int i = 0; i < 3; i++) {
            try {
                vehicle.setLicensePlate(plates[i]);
                fail("Managed to set an invalid License Plate");
            } catch (InvalidInputException e) {

            }
        }
        try {
            vehicle.setLicensePlate("ABC1230F");
        } catch (InvalidInputException e) {
            fail("Could not set a valid License Plate");
        }
    }

    @Test
    public void setChassisNumber() throws Exception {
        Vehicle vehicle = new Vehicle();
        String[] chassisNumbers = {"0123456789123456", "0123456789123456I", "0123456789123456O", "0123456789123456Q", "AAAAAAAAAUAAAAAAA", "AAAAAAAAAZAAAAAAA", "AAAAAAAAA0AAAAAAA", "012345678912345678"};
        for (int i = 0; i < 8; i++) {
            try {
                vehicle.setChassisNumber(chassisNumbers[i]);
                fail();
            } catch (InvalidInputException e) {
            }
        }
        try {
            vehicle.setChassisNumber("UZ0uzAbCukz12345l");
            assertEquals("UZ0UZABCUKZ12345L", vehicle.getChassisNumber());
        } catch (InvalidInputException e) {
            fail();
        }
    }

    @Test(expected = InvalidInputException.class)
    public void setValue() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setValue(-1);
    }

    @Test(expected = InvalidInputException.class)
    public void setMileage() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setValue(-1);
    }

}