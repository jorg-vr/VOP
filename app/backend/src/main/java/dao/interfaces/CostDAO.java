package dao.interfaces;

import model.billing.Cost;
import model.fleet.Vehicle;

import java.time.LocalDate;

/**
 * Created by sam on 4/11/17.
 */
public interface CostDAO extends DAO<Cost> {

    /**
     * Returns a Filter to use in listFiltered in this class, which returns all costs that are paid or not
     *
     * @param paid boolean if is paid
     * @return A useable Filter for listFiltered
     */
    Filter<Cost> byPaid(boolean paid);

    //TODO insurance object
    Filter<Cost> byInsurance(int contractID);

    /**
     * Returns a Filter to use in listFiltered in this class, which returns all costs after the given startDate.
     *
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Cost> startAfter(LocalDate date);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all costs before the given endDate.
     *
     * @param date The date to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Cost> endBefore(LocalDate date);

}
