package controller;

import dao.interfaces.DAO;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.identity.Company;
import model.identity.Customer;

/**
 * Created by Billie Devolder on 19/04/2017.
 */
public class CompanyController extends AbstractController<Company> {

    public CompanyController(Function function) {
        super(BackendApplication.getProvider().getCompanyDAO(), Resource.COMPANY, function);
    }

    @Override
    public boolean isOwner(Company company, Function function) {
        return function.getCompany().equals(company);
    }
}
