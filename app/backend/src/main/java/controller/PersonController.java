package controller;

import dao.database.ProductionProvider;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.PersonDAO;
import dao.test.TestDAOProvider;
import dao.test.TestPersonDAO;
import main.BackendApplication;
import model.identity.Person;

import java.util.UUID;

/**
 * For more information of what this class does, see AbstractController
 */
public class PersonController extends AbstractController<Person> {

    private DAOProvider provider;

    private PersonDAO dao;

    public PersonController() {
        super(BackendApplication.getProvider().getPersonDAO());
        provider = BackendApplication.getProvider();
        this.dao = provider.getPersonDAO();
    }

    public Person createPerson(String firstName, String lastName, String email) throws DataAccessException {
        return dao.create(firstName, lastName, email);
    }

    public Person updatePerson(UUID personId, String firstName, String lastName, String email) throws DataAccessException {
        return dao.update(personId, firstName, lastName, email);
    }

}
