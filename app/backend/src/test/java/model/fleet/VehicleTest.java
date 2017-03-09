package model.fleet;

import org.junit.Test;
import spring.Exceptions.InvalidInputException;

import static org.junit.Assert.fail;

/**
 * Created by Ponti on 10/03/2017.
 */
public class VehicleTest {

    @Test
    public void setLicensePlate() throws Exception {
        Vehicle vehicle = new Vehicle();
        String[] plates = {"ABC 960", "", "AZR "};
        for(int i =0;i<3;i++){
            try{
                vehicle.setLicensePlate(plates[i]);
                fail();
            }
            catch (InvalidInputException e){

            }
        }
        try{
            vehicle.setLicensePlate("ABC1230F");
        }
        catch (InvalidInputException e){
            fail();
        }
    }

    @Test
    public void setChassisNumber() throws Exception {
        Vehicle vehicle = new Vehicle();
        String[] chassisNumbers = {"0123456789123456",""}
    }

    @Test
    public void setValue() throws Exception {

    }

    @Test
    public void setMileage() throws Exception {

    }

    @Test
    public void setType() throws Exception {

    }

    @Test
    public void setLeasingCompany() throws Exception {

    }

    @Test
    public void equals() throws Exception {

    }

}