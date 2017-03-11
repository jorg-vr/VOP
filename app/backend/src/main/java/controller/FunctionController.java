package controller;

import dao.interfaces.DAO;
import dao.test.TestDAOProvider;
import dao.test.TestFunctionDAO;
import model.account.Function;

/**
 * Created by Billie Devolder on 11/03/2017.
 */
public class FunctionController extends AbstractController<Function> {

    public FunctionController() {
        super(new TestFunctionDAO()); // TODO add to provider
    }


}
