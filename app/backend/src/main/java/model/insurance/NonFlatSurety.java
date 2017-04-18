package model.insurance;

/**
 * Created by jorg on 4/12/17.
 */
public class NonFlatSurety extends Surety {

    /**
     * percentage of value that has to be paid yearly
     */

    private double premiumPercentage;

    private int minPremium;


    public double getPremiumPercentage() {
        return premiumPercentage;
    }

    public void setPremiumPercentage(double premiumPercentage) {
        this.premiumPercentage = premiumPercentage;
    }

    public int getMinPremium() {
        return minPremium;
    }

    public void setMinPremium(int minPremium) {
        this.minPremium = minPremium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof NonFlatSurety)) return false;

        NonFlatSurety that = (NonFlatSurety) o;

        return getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }
}
