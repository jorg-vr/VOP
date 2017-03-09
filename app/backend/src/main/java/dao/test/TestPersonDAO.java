package dao.test;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.PersonDAO;
import model.identity.Address;
import model.identity.Function;
import model.identity.Person;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestPersonDAO implements PersonDAO {

    private static Map<UUID, Person> persons = new HashMap<>();

    static {
        Person p0 = new Person();
        p0.setFirstName("Jan");
        p0.setLastName("Janssens");
        p0.setEmail("jan.janssens@mail.com");
        UUID zero = new UUID(0, 0);
        p0.setUuid(zero);
        persons.put(zero, p0);

        Person p1 = new Person();
        p1.setFirstName("Rik");
        p1.setLastName("Peeters");
        p1.setEmail("rik.peeters@mail.com");
        UUID one = new UUID(0, 1);
        p1.setUuid(one);
        persons.put(one, p1);
    }

    @Override
    public Person create(Person person) throws DataAccessException {
        UUID uuid = UUID.randomUUID();
        persons.put(uuid, person);
        person.setUuid(uuid);
        return person;
    }

    @Override
    public Person get(UUID id) throws DataAccessException {
        if (!persons.containsKey(id)) {
            throw new DataAccessException();
        }
        return persons.get(id);
    }

    @Override
    public void update(Person person) throws DataAccessException {
        if (!persons.containsKey(person.getUuid())) {
            throw new DataAccessException();
        }
        persons.put(person.getUuid(), person);
    }

    @Override
    public void remove(Person person) throws DataAccessException {
        if (!persons.containsKey(person.getUuid())) {
            throw new IllegalArgumentException();
        }
        persons.remove(person.getUuid());
    }

    @Override
    public Collection<Person> listFiltered(Filter... filters) throws DataAccessException {
        return persons.values();
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
    public void close() {

    }
}
