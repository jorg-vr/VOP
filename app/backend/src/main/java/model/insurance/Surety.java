package model.insurance;


import model.history.EditableObject;

import java.util.Collection;
import java.util.UUID;

/**
 * Surety class representing an surety linked to a (group of) vehicle(s).
 */
public  abstract class Surety implements EditableObject {

    private UUID uuid;
    private Collection<SpecialCondition> specialConditions;


    /**
     * Type of surety included
     */

    private SuretyType suretyType;

    /**
     * Constructor
     */
    public Surety() {
    }

    @Override
    public UUID getUuid() {
        return null;
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
