package spring.model.insurance;

import model.insurance.Surety;
import model.insurance.SuretyType;

/**
 * Created by Billie Devolder on 2/05/2017.
 */
public class RESTCommission {

    private SuretyType suretyType;

    private Double commission;

    public RESTCommission() {
    }

    public RESTCommission(Double commission,SuretyType suretyType) {
        this.commission = commission;
        this.suretyType=suretyType;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public SuretyType getSuretyType() {
        return suretyType;
    }

    public void setSuretyType(SuretyType suretyType) {
        this.suretyType = suretyType;
    }
}
