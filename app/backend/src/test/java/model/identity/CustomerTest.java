package model.identity;

import model.fleet.Fleet;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
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
        assertTrue(customer.getFleets().contains(fleet));
        assertTrue(customer.removeFleet(fleet));
        assertFalse(customer.getFleets().contains(fleet));
    }

}