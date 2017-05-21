package model.insurance;

import model.history.EditableObject;
import model.identity.InsuranceCompany;

import java.util.ArrayList;

/**
 * Class represents a FlatSurety: the premium is always the same and not depending on the insured value
 * Created by jorg on 4/12/17.
 */
public class FlatSurety extends Surety {

    /**
     * The premium in cents
     */
    private int premium;

    /**
     * Default constructor
     */
    public FlatSurety() {
    }

    /**
     * Constructor
     *
     * @param premium the premium
     */
    public FlatSurety(int premium) {
        this.premium = premium;
    }

    /**
     * Constructor
     *
     * @param insuranceCompany
     * @param suretyType
     * @param premium
     */
    public FlatSurety(InsuranceCompany insuranceCompany, SuretyType suretyType, int premium) {
        super(insuranceCompany, suretyType);
        this.premium = premium;
    }

    /**
     * Returns the premium
     *
     * @param value value of which the premium has to be calculated. This is e.g the insuredValue of a car
     * @return the calculated premium, in this case the premium given
     */
    @Override
    public int calculatePremium(int value) {
        return premium;
    }

    /**
     * Gets the premium
     *
     * @return the premium
     */
    public int getPremium() {
        return premium;
    }

    /**
     * Sets the premium
     *
     * @param premium the premium
     */
    public void setPremium(int premium) {
        this.premium = premium;
    }

    @Override
    public EditableObject copy() {
        FlatSurety flatSurety = new FlatSurety();
        flatSurety.setUuid(getUuid());
        flatSurety.setPremium(getPremium());
        flatSurety.setInsuranceCompany(getInsuranceCompany());
        flatSurety.setSpecialConditions(new ArrayList<>(getSpecialConditions()));
        flatSurety.setSuretyType(getSuretyType());
        return flatSurety;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof FlatSurety)) return false;

        FlatSurety that = (FlatSurety) o;

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
