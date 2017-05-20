package spring.model;

import model.insurance.SuretyType;

/**
 * Created by Billie Devolder on 2/05/2017.
 */
public class RESTTax {

    private SuretyType suretyType;
    private Double tax;

    public RESTTax() {
    }

    public RESTTax(Double tax, SuretyType suretyType) {
        this.tax = tax;
        this.suretyType=suretyType;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public SuretyType getSuretyType() {
        return suretyType;
    }

    public void setSuretyType(SuretyType suretyType) {
        this.suretyType = suretyType;
    }
}
