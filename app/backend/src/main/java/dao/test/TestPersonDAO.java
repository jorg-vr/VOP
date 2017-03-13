package dao.test;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.PersonDAO;
import model.identity.Address;
import model.identity.Function;
import model.identity.Person;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestPersonDAO extends TestDAO<Person> implements PersonDAO {

    public static Person PERSON_0 = new Person();
    public static Person PERSON_1 = new Person();

    public static final UUID PERSON_0_ID = new UUID(0, 0);
    public static final UUID PERSON_1_ID = new UUID(0, 1);

    static {
        PERSON_0.setFirstName("Jan");
        PERSON_0.setLastName("Janssens");
        PERSON_0.setEmail("jan.janssens@mail.com");
        PERSON_0.setUuid(PERSON_0_ID);

        PERSON_1.setFirstName("Rik");
        PERSON_1.setLastName("Peeters");
        PERSON_1.setEmail("rik.peeters@mail.com");
        PERSON_1.setUuid(PERSON_1_ID);
    }


    private static Map<UUID, Person> persons;

    public TestPersonDAO() {
        super();
        persons = new HashMap<>();

        persons.put(PERSON_0_ID, PERSON_0);
        persons.put(PERSON_1_ID, PERSON_1);

        setMapping(persons);
    }

    @Override
    public Person create(String firstName, String lastName, String email) {
        // TODO email should be unique
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        UUID uuid = UUID.randomUUID();
        person.setUuid(uuid);
        persons.put(uuid, person);
        return person;
    }

    @Override
    public Person update(UUID id, String firstName, String lastName) throws DataAccessException {
        if (!persons.containsKey(id)) {
            throw new DataAccessException();
        }
        Person person = persons.get(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    @Override
    public Filter<Person> nameContains(String name) {
        return null;
    }

    @Override
    public Filter<Person> function(Function function) {
        return null;
    }

    @Override
    public Filter<Person> bornBefore(LocalDate date) {
        return null;
    }

    @Override
    public Filter<Person> bornAfter(LocalDate date) {
        return null;
    }

    @Override
    public Filter<Person> byAddress(Address address) {
        return null;
    }

    @Override
    public Filter<Person> byBankAccountNummber(String bankAccountNumber) {
        return null;
    }

    @Override
    public Filter<Person> byEmail(String email) {
        return null;
    }

    @Override
    public void remove(UUID id) throws DataAccessException {

    }

    @Override
    public void close() {

    }
}
