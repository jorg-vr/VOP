package model.insurance;

import model.history.EditableObject;
import model.identity.InsuranceCompany;

import java.util.ArrayList;

/**
 * Class representing a variable Surety depending on the insured value
 * Created by jorg on 4/12/17.
 */
public class NonFlatSurety extends Surety {
    /**
     * Percentage of value that has to be paid yearly
     */
    private double premiumPercentage;

    /**
     * If the calculated price is below the minium premium, the minimum premium has to be paid
     * Minimum premium in cents
     */
    private int minPremium;

    /**
     * Default Constructor
     */
    public NonFlatSurety() {
    }

    /**
     * Constructor
     *
     * @param premiumPercentage the percentage
     * @param minPremium        the minimum premium
     */
    public NonFlatSurety(double premiumPercentage, int minPremium) {
        this.premiumPercentage = premiumPercentage;
        this.minPremium = minPremium;
    }

    /**
     * Constructor
     *
     * @param insuranceCompany
     * @param suretyType
     * @param premiumPercentage
     * @param minPremium
     */
    public NonFlatSurety(InsuranceCompany insuranceCompany, SuretyType suretyType, double premiumPercentage, int minPremium) {
        super(insuranceCompany, suretyType);
        this.premiumPercentage = premiumPercentage;
        this.minPremium = minPremium;
    }

    /**
     * Calculates the premium, if the percentage of the given value is below the minimum, the minimum will be returned,
     * otherwise calculated value.
     *
     * @param value value of which the premium has to be calculated. This is e.g the insuredValue of a car
     * @return
     */
    @Override
    public int calculatePremium(int value) {
        int premium = (int) Math.round(value * premiumPercentage);
        if (premium < minPremium) {
            return minPremium;
        }
        return premium;
    }

    /**
     * Gets the premium percentage
     *
     * @return the premium percentage
     */
    public double getPremiumPercentage() {
        return premiumPercentage;
    }

    /**
     * Sets the premium percentage
     *
     * @param premiumPercentage the premium percentage
     */
    public void setPremiumPercentage(double premiumPercentage) {
        this.premiumPercentage = premiumPercentage;
    }

    /**
     * Gets the minimum premium
     *
     * @return the minimum premium
     */
    public int getMinPremium() {
        return minPremium;
    }

    /**
     * Sets the minimum premium
     *
     * @param minPremium the mimium premium
     */
    public void setMinPremium(int minPremium) {
        this.minPremium = minPremium;
    }

    /**
     * Copies the object
     *
     * @return the copy
     */
    @Override
    public EditableObject copy() {
        NonFlatSurety nonFlatSurety = new NonFlatSurety();
        nonFlatSurety.setSuretyType(getSuretyType());
        nonFlatSurety.setUuid(getUuid());
        nonFlatSurety.setMinPremium(getMinPremium());
        nonFlatSurety.setPremiumPercentage(getPremiumPercentage());
        nonFlatSurety.setSpecialConditions(new ArrayList<>(getSpecialConditions()));
        nonFlatSurety.setInsuranceCompany(getInsuranceCompany());
        return nonFlatSurety;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof NonFlatSurety)) return false;

        NonFlatSurety that = (NonFlatSurety) o;

        return getUuid() != null && getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        if (getUuid() != null) {
            return getUuid().hashCode();
        }
        return super.hashCode();
    }
}
