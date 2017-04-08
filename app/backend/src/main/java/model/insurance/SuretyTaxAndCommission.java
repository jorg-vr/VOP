package model.insurance;


import model.fleet.VehicleType;
import model.history.EditableObject;

import java.util.UUID;

/**
 *  SuretyTaxAndCommission class representing an surety surety (verzekeringswaarborg). A surety is characterized by its suretyType.
 *  Each suretyType of an surety surety has different taxes/charges and commission costs. These costs have
 *  default values for each suretyType but can be adjusted on subfleet level by the administrator.
 */
public class SuretyTaxAndCommission implements EditableObject {

    private UUID uuid;

    /**
     *  Taxes and charges to federal government and other organisations.
     *  Expressed as a percentage.
     */
    private double taxes;
    /**
     *  Compensation to estate agent for the realization of the polis itself and the assistance
     *  in case of damage. Expressed as a percentage.
     */
    private double commission;
    /**
     * Type of the surety surety as defined in SuretyType.
     */
    private SuretyType suretyType;

    /**
     * Combined with suretytype this defines the commission and taxes.
     */
    private VehicleType vehicleType;

    /**
     * Constructor
     */
    public SuretyTaxAndCommission() {
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public SuretyType getSuretyType() {
        return suretyType;
    }

    public void setSuretyType(SuretyType suretyType) {
        this.suretyType = suretyType;
    }

    public void setUuid(UUID uuid){
        this.uuid=uuid;
    }


    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public EditableObject copy() {
        SuretyTaxAndCommission suretyTaxAndCommission =new SuretyTaxAndCommission();
        suretyTaxAndCommission.setUuid(getUuid());
        suretyTaxAndCommission.setCommission(getCommission());
        suretyTaxAndCommission.setTaxes(getTaxes());
        suretyTaxAndCommission.setSuretyType(getSuretyType());
        return suretyTaxAndCommission;
    }
}