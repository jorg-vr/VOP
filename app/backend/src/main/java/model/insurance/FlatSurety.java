package model.insurance;

/**
 * Created by jorg on 4/12/17.
 */
public class FlatSurety extends Surety {

    /**
     * percentage of value that has to be paid yearly
     */

    private double premium;

    private int minPremium;


    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public int getMinPremium() {
        return minPremium;
    }

    public void setMinPremium(int minPremium) {
        this.minPremium = minPremium;
    }
}
