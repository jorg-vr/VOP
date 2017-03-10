package model.identity;

import model.fleet.Fleet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ponti on 10/03/2017.
 */
public class CustomerTest {

    @Test
    public void addFleet() throws Exception {
        Fleet fleet = new Fleet();
        Customer customer = new Customer();

        assertTrue(customer.addFleet(fleet));
        assertEquals(customer, fleet.getOwner());
    }

}