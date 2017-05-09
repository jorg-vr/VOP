package model.insurance;


import model.history.EditableObject;
import model.history.LogResource;
import model.identity.InsuranceCompany;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Surety class representing an surety linked to a (group of) vehicle(s).
 */
public  abstract class Surety implements EditableObject {

    /**
     * The unique id
     */
    private UUID uuid;

    /**
     * The specialconditions applying for the surety
     */
    private Collection<SpecialCondition> specialConditions;

    /**
     * The insurance company
     */
    private InsuranceCompany insuranceCompany;

    /**
     * Type of surety included
     */
    private SuretyType suretyType;

    /**
     * Constructor
     */
    public Surety() {
    }

    /**
     * Calculate the premium that has to be paid based on the given value
     * @param value value of which the premium has to be calculated. This is e.g the insuredValue of a car
     * @return premium based on value
     */
    public abstract int calculatePremium(int value);

    /**
     * Gets the uuid
     * @return the uuid
     */
    @Override
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the uuid
     * @param uuid the uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Copies the object
     * @return the object
     */
    @Override
    public abstract EditableObject copy();

    /**
     * Gets the surety type
     * @return the surety type
     */
    public SuretyType getSuretyType() {
        return suretyType;
    }

    /**
     * Sets the suretyType
     * @param suretyType the surety type
     */
    public void setSuretyType(SuretyType suretyType) {
        this.suretyType = suretyType;
    }

    @Override
    public LogResource getLogResource() {
        return LogResource.SURETY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Surety)) return false;

        Surety that = (Surety) o;

        return getUuid()!=null && getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        if(getUuid()!=null){return getUuid().hashCode();}
        return getUuid().hashCode();
    }

    /**
     * Gets the special conditions
     * @return the special conditions
     */
    public Collection<SpecialCondition> getSpecialConditions() {
        return specialConditions;
    }

    /**
     * Sets the special conditions
     * @param specialConditions the special conditions
     */
    public void setSpecialConditions(Collection<SpecialCondition> specialConditions) {
        this.specialConditions = specialConditions;
    }

    /**
     * Adds a single special conditions to the collection
     * @param specialCondition a special condition
     */
    public void addSpecialCondition(SpecialCondition specialCondition) {
        if (specialConditions == null) {
            specialConditions = new ArrayList<>();
        }
        specialConditions.add(specialCondition);
    }

    /**
     * Gets the insurance company
     * @return the insurance company
     */
    public InsuranceCompany getInsuranceCompany() {
        return insuranceCompany;
    }

    /**
     * Sets the insurance company
     * @param insuranceCompany the insurance company
     */
    public void setInsuranceCompany(InsuranceCompany insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

}
