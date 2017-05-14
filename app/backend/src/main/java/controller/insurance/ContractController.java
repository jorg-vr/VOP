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
import model.identity.InsuranceCompany;
import model.insurance.Contract;

import java.time.LocalDate;
import java.util.Collection;

public class ContractController extends AbstractController<Contract> {

    public ContractController(Function function, DAOManager manager) {
        super(manager, manager.getContractDao(), Resource.INSURANCE, function);
    }

    @Override
    public boolean isOwner(Contract contract, Function function) {
        Company company = function.getCompany();
        return contract.getCompany().equals(company) || contract.getCustomer().equals(company);
    }

    public Collection<Contract> getFiltered(Customer customer, InsuranceCompany insuranceCompany,
                                            LocalDate startsBefore, LocalDate startsOn, LocalDate startsAfter,
                                            LocalDate endsBefore, LocalDate endsOn, LocalDate endsAfter) throws DataAccessException, UnAuthorizedException {
        ContractDAO dao = (ContractDAO) getDao();
        return getAll(
                dao.byCustomer(customer),
                dao.byInsuranceCompany(insuranceCompany),
                dao.startsBefore(startsBefore),
                dao.startsOn(startsOn),
                dao.startsAfter(startsAfter),
                dao.endsBefore(endsBefore),
                dao.endsOn(endsOn),
                dao.endsAfter(endsAfter)
        );
    }
}
