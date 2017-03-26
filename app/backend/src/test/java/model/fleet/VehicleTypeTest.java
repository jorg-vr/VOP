package model.fleet;

import org.junit.Test;
import spring.exceptions.InvalidInputException;


public class VehicleTypeTest {
    @Test(expected = InvalidInputException.class)
    public void setTax() throws Exception {
        VehicleType vehicleType = new VehicleType();
        vehicleType.setTax(-3.4);
    }

}