package model;

import model.history.EditableObject;
import model.insurance.SuretyType;

import java.util.Map;

/**
 * Created by jorg on 5/10/17.
 *
 * Represent a resource which contains commissions
 */
public interface CommissionContainer extends EditableObject {
    /***
     * @return Map containing a commission double for each suretytype
     */
    Map<SuretyType, Double> getCommissions();

    /***
     * @param commissions Map containing a commission double for each suretytype
     */
    void setCommissions(Map<SuretyType, Double> commissions);
}
