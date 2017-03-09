package controller;

import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.PersonDAO;
import dao.test.TestPersonDAO;
import model.identity.Person;

public class PersonController extends AbstractController<Person> {

    private DAOProvider provider;

    private PersonDAO dao;

    public PersonController(DAOProvider provider) {
        super(new TestPersonDAO());
        this.provider = provider;
        this.dao = new TestPersonDAO();
    }

    public Person createPerson(String firstName, String lastName, String email, String phoneNumber) throws DataAccessException {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setPhoneNumber(phoneNumber);

        return dao.create(person);
    }
}
