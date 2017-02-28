package dao;

import model.Insurance;

import java.util.Collection;

/**
 * Created by sam on 2/26/17.
 */
public interface InsuranceDAO {

    public Collection<Insurance> listFiltered(Filter... filters);
}
