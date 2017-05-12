package controller.insurance;

import controller.AbstractController;
import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Resource;
import model.insurance.SpecialCondition;

/**
 * Created by Billie Devolder on 7/05/2017.
 */
public class SpecialConditionController extends AbstractController<SpecialCondition> {

    public SpecialConditionController(Function function, DAOManager manager) {
        super(manager, manager.getSpecialConditionDao(), Resource.INSURANCE, function);
    }

    @Override
    public boolean isOwner(SpecialCondition specialCondition, Function function) {
        return true;
    }
}
