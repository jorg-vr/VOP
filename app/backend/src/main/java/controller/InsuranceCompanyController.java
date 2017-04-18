package controller;

import controller.AbstractController;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.identity.Customer;
import model.identity.InsuranceCompany;

/**
 * Created by Billie Devolder on 15/04/2017.
 */
public class InsuranceCompanyController extends AbstractController<InsuranceCompany> {

    public InsuranceCompanyController(Function function) {
        super(BackendApplication.getProvider().getInsuranceCompanyDao(), Resource.COMPANY,function);
    }


    @Override
    public boolean isOwner(InsuranceCompany company, Function function) {
        return function.getCompany().equals(company);
    }
}
