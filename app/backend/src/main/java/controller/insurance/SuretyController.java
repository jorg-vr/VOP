package controller.insurance;

import controller.AbstractController;
import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Resource;
import model.insurance.Surety;

/**
 * Created by Billie Devolder on 18/04/2017.
 */
public class SuretyController extends AbstractController<Surety> {

    public SuretyController(Function function, DAOManager manager) {
        super(manager, manager.getSuretyDao(), Resource.INSURANCE, function);
    }

    @Override
    public boolean isOwner(Surety surety, Function function) {
        return true;
    }
}
