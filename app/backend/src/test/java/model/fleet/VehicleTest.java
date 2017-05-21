package model.fleet;

import org.junit.Ignore;
import org.junit.Test;
import spring.exceptions.InvalidInputException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class VehicleTest {


    @Test
    public void setChassisNumber() throws Exception {
        Vehicle vehicle = new Vehicle();
        String[] chassisNumbers = {"0123456789123456", "0123456789123456I", "0123456789123456O", "0123456789123456Q", "AAAAAAAAAUAAAAAAA", "AAAAAAAAAZAAAAAAA", "AAAAAAAAA0AAAAAAA", "012345678912345678"};
        for (int i = 0; i < 8; i++) {
            try {
                vehicle.setVin(chassisNumbers[i]);
            } catch (InvalidInputException e) {
            }
        }
        try {
            vehicle.setVin("UZ0uzAbCukz12345l");
            assertEquals("UZ0UZABCUKZ12345L", vehicle.getVin());
        } catch (InvalidInputException e) {
            fail();
        }
    }
}