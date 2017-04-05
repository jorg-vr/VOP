package controller;

import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.PersonDAO;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.identity.Person;

import java.util.UUID;

/**
 * For more information of what this class does, see AbstractController
 */
public class PersonController extends AbstractController<Person> {

    private DAOProvider provider;

    private PersonDAO dao;

    public PersonController(Function function) {
        super(BackendApplication.getProvider().getPersonDAO(), Resource.PERSON,function);
        provider = BackendApplication.getProvider();
        this.dao = provider.getPersonDAO();
    }


    @Override
    public boolean isOwner(Person person, Function function) {
        return function.getAccount().getPerson().equals(person);
    }
}
