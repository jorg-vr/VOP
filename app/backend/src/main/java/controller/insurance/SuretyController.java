package controller.insurance;

import controller.AbstractController;
import dao.interfaces.DAO;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.insurance.Surety;

/**
 * Created by Billie Devolder on 18/04/2017.
 */
public class SuretyController extends AbstractController<Surety> {

    public SuretyController(Function function) {
        super(BackendApplication.getProvider().getSuretyDao(), Resource.INSURANCE, function);
    }

    @Override
    public boolean isOwner(Surety surety, Function function) {
        return true;
    }
}
