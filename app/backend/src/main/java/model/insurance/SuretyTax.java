package model.insurance;


import model.fleet.VehicleType;
import model.history.EditableObject;

import java.util.UUID;

/**
 *  SuretyTax class representing a surety (verzekeringswaarborg). A surety is characterized by its suretyType.
 *  Each suretyType of an surety surety has different taxes/charges and commission costs. These costs have
 *  default values for each suretyType but can be adjusted on subfleet level by the administrator.
 */
public class SuretyTax implements EditableObject {

    private UUID uuid;

    /**
     *  Taxes and charges to federal government and other organisations.
     *  Expressed as a percentage.
     */
    private double taxes;

    /**
     * Type of the surety  as defined in SuretyType.
     */
    private SuretyType suretyType;

    /**
     * Combined with suretytype this defines the  taxes.
     */
    private VehicleType vehicleType;

    /**
     * Constructor
     */
    public SuretyTax() {
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
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
        SuretyTax suretyTax =new SuretyTax();
        suretyTax.setUuid(getUuid());
        suretyTax.setTaxes(getTaxes());
        suretyTax.setSuretyType(getSuretyType());
        return suretyTax;
    }
}