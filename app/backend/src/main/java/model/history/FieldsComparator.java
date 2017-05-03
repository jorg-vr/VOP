package model.history;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Billie Devolder
 */
public class FieldsComparator {

    /**
     * Compare the values of all the fields between 2 objects of the same class, including the fields of all superclasses
     *
     * @param o1  the old version of the object
     * @param o2  the new version of the object
     * @return a collection with descriptions of all the fields that are different
     */
    public static Collection<Description> compareFields(Object o1, Object o2) {
        if (o1.getClass() != o2.getClass()) {
            throw new IllegalArgumentException("o1 and o2 should be of the same class!");
        }

        Collection<Description> descriptions = new ArrayList<>();
        try {
            Class currentClass = o1.getClass();
            while (currentClass.getSuperclass() != null) {
                for (Field field1 : currentClass.getDeclaredFields()) {
                    field1.setAccessible(true);
                    Object oldObject = field1.get(o1);

                    Field fieldOther = currentClass.getDeclaredField(field1.getName());
                    fieldOther.setAccessible(true);
                    Object newObject = fieldOther.get(o2);

                    if ((oldObject == null && newObject != null) ||
                            (oldObject != null && newObject == null) ||
                            (oldObject != null && !oldObject.equals(newObject))) {
                        descriptions.add(new Description(field1.getName(), oldObject, newObject));
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return descriptions;
    }
}
