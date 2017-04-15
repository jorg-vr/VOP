package controller.insurance;

import controller.AbstractController;
import dao.interfaces.DAO;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.insurance.SuretyTax;

/**
 * Created by Billie Devolder on 15/04/2017.
 */
public class SuretyTaxController extends AbstractController<SuretyTax> {

    public SuretyTaxController(Function function) {
        super(BackendApplication.getProvider().getSuretyTaxDao(), Resource.INSURANCE, function);
    }

    @Override
    public boolean isOwner(SuretyTax suretyTax, Function function) {
        // TODO ???
        return true;
    }
}
