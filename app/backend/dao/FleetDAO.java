package dao;

import model.Fleet;

import java.util.Collection;

/**
 * Created by sam on 2/26/17.
 */
public interface FleetDAO {

    Collection<Fleet> listFiltered(Filter... filters);

}
