package model.insurance;

/**
 *  Surety class representing an insurance surety (verzekeringswaarborg). A surety is characterized by its type.
 *  Each type of an insurance surety has different taxes/charges and commission costs. These costs have
 *  default values for each type but can be adjusted on subfleet level by the administrator.
 */
public class Surety {

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
     * Type of the insurance surety as defined in SuretyType.
     */
    private SuretyType type;

    /**
     * Constructor
     */
    public Surety() {
    }

    /**
     * Constructor for a specific type of an insurance surety.
     * Create specific type with default values for taxes/charges and commission costs.
     * @param type of insurance surety.
     */
    public Surety(SuretyType type){
        this.type=type;
        this.taxes=type.getTaxes();
        this.commission=type.getCommission();
    }

    /**
     * Constructor for a specific type of an insurance surety.
     * Create specific type with custom values for taxes/charges and commission costs.
     * @param taxes : Custom value for taxes/charges of the insurance surety
     * @param commission : Custom value for commission cost of insurance surety
     * @param type of insurance surety.
     */
    public Surety(double taxes,double commission,SuretyType type){
        this.taxes=taxes;
        this.commission=commission;
        this.type=type;
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

    public SuretyType getType() {
        return type;
    }

    public void setType(SuretyType type) {
        this.type = type;
    }
}