package controller;

import controller.exceptions.UnAuthorizedException;
import dao.interfaces.CompanyDAO;
import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.identity.Company;
import model.identity.CompanyType;
import model.identity.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public Collection<Company> getFiltered(String nameContains, String country,
                                    String city, String postalCode) throws DataAccessException, UnAuthorizedException {
        CompanyDAO<Company> dao = (CompanyDAO<Company>) getDao();
        List<Filter<Company>> filters = new ArrayList<>();
        return getDao().listFiltered(
                dao.containsName(nameContains)
        );
    }
}
