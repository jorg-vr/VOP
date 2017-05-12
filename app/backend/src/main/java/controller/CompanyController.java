package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.interfaces.CompanyDAO;
import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Resource;
import model.identity.Company;
import model.identity.CompanyType;

import java.util.Collection;
import java.util.stream.Collectors;

import static util.Compare.containsIgnoreCase;


/**
 * Created by Billie Devolder on 19/04/2017.
 */
public class CompanyController extends AbstractController<Company> {

    private CompanyDAO<Company> dao;

    public CompanyController(Function function, DAOManager manager) {
        super(manager, manager.getCompanyDAO(), Resource.COMPANY, function);
        dao = manager.getCompanyDAO();
    }

    @Override
    public boolean isOwner(Company company, Function function) {
        return function.getCompany().equals(company);
    }

    /**
     * When you don't want to filter on a certain argument, pass a null value
     *
     * @param nameContains Only return companies whose name contains nameContains
     * @param country      NOT implemented TODO
     * @param city         NOT implemented TODO
     * @param postalCode   NOT implemented TODO
     * @param type         only return companies of this type
     * @return all companies, filtered on the arguments
     * @throws DataAccessException   Something went horribly wrong with the database
     * @throws UnAuthorizedException Function is not authorized to get all the objects.
     */
    public Collection<Company> getFiltered(String nameContains, String country,
                                           String city, String postalCode, CompanyType type) throws DataAccessException, UnAuthorizedException {
        //CompanyDAO<Company> dao = (CompanyDAO<Company>) getDao();
        Collection<Company> result = getAll(dao.containsName(nameContains));

        // Filter companies on criteria that are not supported by the database
        return result.stream()
                .filter(c -> type == null || c.getCompanyType() == type)
                .filter(c -> postalCode == null || (c.getAddress() != null && containsIgnoreCase(c.getAddress().getPostalCode(), postalCode)))
                .filter(c -> country == null || (c.getAddress() != null && containsIgnoreCase(c.getAddress().getCountry(), country)))
                .filter(c -> city == null || (c.getAddress() != null && containsIgnoreCase(c.getAddress().getTown(), city)))
                .collect(Collectors.toList());
    }
}
