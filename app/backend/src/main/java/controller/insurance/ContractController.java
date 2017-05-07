package controller.insurance;


import controller.AbstractController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.ContractDAO;
import dao.interfaces.DAOManager;
import dao.exceptions.DataAccessException;
import model.account.Function;
import model.account.Resource;
import model.identity.Company;
import model.identity.Customer;
import model.insurance.Contract;

import java.util.Collection;

public class ContractController extends AbstractController<Contract> {

    public ContractController(Function function, DAOManager manager) {
        super(manager.getContractDao(), Resource.INSURANCE, function);
    }

    @Override
    public boolean isOwner(Contract contract, Function function) {
        Company company = function.getCompany();
        return contract.getCompany().equals(company) || contract.getCustomer().equals(company);
    }

    public Collection<Contract> getFiltered(Customer company) throws DataAccessException, UnAuthorizedException {
        ContractDAO dao = (ContractDAO) getDao();
        return getAll(
                dao.byCustomer(company)
        );
    }
}
