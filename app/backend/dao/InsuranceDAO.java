package dao;

import model.Insurance;

import java.util.Collection;

public interface InsuranceDAO {

    Collection<Insurance> listFiltered(Filter... filters);
}
