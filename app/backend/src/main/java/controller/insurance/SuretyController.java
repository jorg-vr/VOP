package controller.insurance;

import controller.AbstractController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.SuretyDAO;
import model.account.Function;
import model.account.Resource;
import model.identity.InsuranceCompany;
import model.insurance.Surety;

import java.util.Collection;

/**
 * Created by Billie Devolder on 18/04/2017.
 */
public class SuretyController extends AbstractController<Surety> {

    private SuretyDAO<Surety> dao;

    public SuretyController(Function function, DAOManager manager) {
        super(manager, manager.getSuretyDao(), Resource.INSURANCE, function);
        this.dao = manager.getSuretyDao();
    }

    @Override
    public boolean isOwner(Surety surety, Function function) {
        return true;
    }


    public Collection<Surety> getFiltered(InsuranceCompany company) throws DataAccessException, UnAuthorizedException {
        return getAll(
                dao.byOwner(company)
        );
    }
}
