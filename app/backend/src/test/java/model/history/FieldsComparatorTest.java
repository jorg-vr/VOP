package model.history;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by Billie Devolder on 3/05/2017.
 */
public class FieldsComparatorTest {

    @Test
    public void compareFields() throws Exception {
        A a = new A("1", "2");
        A b = new A("3", "4");

        Collection<Description> descriptions = FieldsComparator.compareFields(a, b);
        assertEquals("There are more/less descriptions than changed fields", 2, descriptions.size());

        for (Description description: descriptions) {
            if (description.getField().equals("a")) {
                assertEquals("1", description.getOldValue());
            }
        }
    }
}