package dao;

import model.Subfleet;

import java.util.Collection;

/**
 * Created by sam on 2/26/17.
 */
public interface SubfleetDAO {
    public Collection<Subfleet> listFiltered(Filter... filters);

}
