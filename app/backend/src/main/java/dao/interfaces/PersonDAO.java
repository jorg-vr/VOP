package dao.interfaces;

import model.identity.Function;
import model.identity.Person;

import java.time.LocalDate;

/**
 * Created by sam on 3/7/17.
 */
public interface PersonDAO extends IdentityDAO<Person> {

    //Checks both first and last name
    Filter<Person> nameContains(String name);

    Filter<Person> function(Function function);

    Filter<Person> bornBefore(LocalDate date);

    Filter<Person> bornAfter(LocalDate date);

}
