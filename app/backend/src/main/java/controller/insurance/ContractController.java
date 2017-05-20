package controller.insurance;


import controller.AbstractController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.interfaces.ContractDAO;
import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Resource;
import model.identity.Company;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.insurance.Contract;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class ContractController extends AbstractController<Contract> {

    private ContractDAO dao;

    public ContractController(Function function, DAOManager manager) {
        super(manager, manager.getContractDao(), Resource.INSURANCE, function);
        this.dao = manager.getContractDao();
    }

    @Override
    public boolean isOwner(Contract contract, Function function) {
        Company company = function.getCompany();
        return contract.getCompany().equals(company) || contract.getCustomer().equals(company);
    }

    public Collection<Contract> getFiltered(Customer customer, InsuranceCompany insuranceCompany,
                                            String startsBefore, String startsOn, String startsAfter,
                                            String endsBefore, String endsOn, String endsAfter) throws DataAccessException, UnAuthorizedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");

        LocalDateTime startsBeforeDate = startsBefore == null ? null : LocalDateTime.parse(startsBefore + " 00-00", formatter);
        LocalDateTime startsAfterDate = startsAfter == null ? null : LocalDateTime.parse(startsAfter + " 00-00", formatter);
        LocalDateTime endsBeforeDate = endsBefore == null ? null : LocalDateTime.parse(endsBefore + " 00-00", formatter);
        LocalDateTime endsAfterDate = endsAfter == null ? null : LocalDateTime.parse(endsAfter + " 00-00", formatter);

        if (startsOn != null) {
            startsBeforeDate = LocalDateTime.parse(startsOn + " 00-00", formatter).plusDays(1);
            startsAfterDate = LocalDateTime.parse(startsOn + " 00-00", formatter).minusDays(1);
        }
        if (endsOn != null) {
            endsBeforeDate = LocalDateTime.parse(endsOn + " 00-00", formatter).plusDays(1);
            endsAfterDate = LocalDateTime.parse(endsOn + " 00-00", formatter).minusDays(1);
        }

        return getAll(
                dao.byCustomer(customer),
                dao.byInsuranceCompany(insuranceCompany),
                dao.startsBefore(startsBeforeDate),
                dao.startsAfter(startsAfterDate),
                dao.endsBefore(endsBeforeDate),
                dao.endsAfter(endsAfterDate)
        );
    }
}
