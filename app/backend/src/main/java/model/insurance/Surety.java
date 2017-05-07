package model.insurance;


import model.history.EditableObject;
import model.history.LogResource;

import java.util.Collection;
import java.util.UUID;

/**
 * Surety class representing an surety linked to a (group of) vehicle(s).
 */
public  abstract class Surety implements EditableObject {

    private UUID uuid;
    private Collection<SpecialCondition> specialConditions;

    // Type of surety included
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

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public EditableObject copy() {
        return null;
    }

    public SuretyType getSuretyType() {
        return suretyType;
    }

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

        return getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }

}
