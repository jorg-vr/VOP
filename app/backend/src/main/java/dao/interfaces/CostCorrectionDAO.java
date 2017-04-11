package dao.interfaces;

import model.billing.Cost;
import model.billing.CostCorrection;

/**
 * Created by sam on 4/11/17.
 */
public interface CostCorrectionDAO extends CostDAO<CostCorrection> {

    Filter<CostCorrection> byOriginalCost(Cost cost);
}
