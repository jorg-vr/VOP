package model.fleet;

import org.junit.Test;
import spring.Exceptions.InvalidInputException;

/**
 * Created by Ponti on 10/03/2017.
 */
public class VehicleTypeTest {
    @Test(expected = InvalidInputException.class)
    public void setTax() throws Exception {
        VehicleType vehicleType = new VehicleType();
        vehicleType.setTax(-3.4);
    }

}