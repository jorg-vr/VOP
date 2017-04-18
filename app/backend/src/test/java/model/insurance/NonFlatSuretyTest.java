package model.insurance;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Billie Devolder on 18/04/2017.
 */
public class NonFlatSuretyTest {


    @Test
    public void calculateCost() throws Exception {

        // Percentage based premium
        NonFlatSurety surety = new NonFlatSurety(0.15, 100);
        assertEquals(150, surety.calculatePremium(1000));

        // Premium is lower than minimum premium -> should return minPremium
        assertEquals(100, surety.calculatePremium(1));
    }

}