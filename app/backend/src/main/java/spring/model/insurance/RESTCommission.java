package spring.model.insurance;

/**
 * Created by Billie Devolder on 2/05/2017.
 */
public class RESTCommission {

    private Double commission;

    public RESTCommission() {
    }

    public RESTCommission(Double commission) {
        this.commission = commission;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }
}
