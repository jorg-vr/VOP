package controller.insurance;


import controller.AbstractController;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.fleet.Fleet;
import model.identity.Company;
import model.insurance.Contract;

public class ContractController extends AbstractController<Contract> {

    public ContractController(Function function) {
        super(BackendApplication.getProvider().getContractDao(), Resource.INSURANCE, function);
    }

    @Override
    public boolean isOwner(Contract contract, Function function) {
        Company company = function.getCompany();
        return contract.getCompany().equals(company) || contract.getCustomer().equals(company);
    }
}
