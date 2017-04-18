package model.fleet;

import org.junit.Ignore;
import org.junit.Test;
import spring.exceptions.InvalidInputException;

@Ignore
public class VehicleTypeTest {
    @Test(expected = InvalidInputException.class)
    public void setTax() throws Exception {
        VehicleType vehicleType = new VehicleType();
    }

}