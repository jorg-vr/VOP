package controller;

import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.PersonDAO;
import dao.test.TestDAOProvider;
import dao.test.TestPersonDAO;
import main.BackendApplication;
import model.identity.Person;

import java.util.UUID;

public class PersonController extends AbstractController<Person> {

    private DAOProvider provider;

    private PersonDAO dao;

    public PersonController() {
        super(BackendApplication.PROVIDER.getPersonDAO());
        provider = BackendApplication.PROVIDER;
        this.dao = provider.getPersonDAO();
    }

    public Person createPerson(String firstName, String lastName, String email) throws DataAccessException {
        return dao.create(firstName, lastName, email);
    }

    public Person updatePerson(UUID personId, String firstName, String lastName) throws DataAccessException {
        return dao.update(personId, firstName, lastName);
    }

}
