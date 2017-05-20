package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.insurance.SpecialCondition;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sam on 5/20/17.
 */
public class ProductionSpecialConditionDAOTest {

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        ProductionProvider.getInstance().close();
    }

    @Test
    public void crud() throws Exception {
        SpecialCondition specialCondition1 = new SpecialCondition();
        specialCondition1.setTitle("test1");
        specialCondition1.setText("TestText");
        specialCondition1.setReferenceCode("1");

        SpecialCondition specialCondition2 = new SpecialCondition();
        specialCondition1.setTitle("test2");
        specialCondition1.setText("TestText");
        specialCondition1.setReferenceCode("1");

        SpecialCondition specialCondition3 = new SpecialCondition();
        specialCondition1.setTitle("test3");
        specialCondition1.setText("TestText");
        specialCondition1.setReferenceCode("1");

        SpecialCondition specialCondition4 = new SpecialCondition();
        specialCondition1.setTitle("test1");
        specialCondition1.setText("TestText");
        specialCondition1.setReferenceCode("1");

        SpecialCondition specialCondition5 = new SpecialCondition();
        specialCondition1.setTitle("test1");
        specialCondition1.setText("TestText");
        specialCondition1.setReferenceCode("1");

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getSpecialConditionDao().create(specialCondition1);
            manager.getSpecialConditionDao().create(specialCondition2);
            manager.getSpecialConditionDao().create(specialCondition3);
            manager.getSpecialConditionDao().create(specialCondition4);
            manager.getSpecialConditionDao().create(specialCondition5);
        }

        specialCondition1.setTitle("Test5");
        specialCondition2.setReferenceCode("27");
        specialCondition3.setText("update");
        specialCondition4.setReferenceCode("27");
        specialCondition5.setTitle("1");
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getSpecialConditionDao().update(specialCondition1);
            manager.getSpecialConditionDao().update(specialCondition2);
            manager.getSpecialConditionDao().update(specialCondition3);
            manager.getSpecialConditionDao().update(specialCondition4);
            manager.getSpecialConditionDao().update(specialCondition5);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            assertTrue(manager.getSpecialConditionDao().get(specialCondition1.getUuid()).getTitle().equals("Test5"));
            assertTrue(manager.getSpecialConditionDao().get(specialCondition2.getUuid()).getReferenceCode().equals("27"));
            assertTrue(manager.getSpecialConditionDao().get(specialCondition3.getUuid()).getText().equals("update"));
            assertTrue(manager.getSpecialConditionDao().get(specialCondition4.getUuid()).getReferenceCode().equals("27"));
            assertTrue(manager.getSpecialConditionDao().get(specialCondition5.getUuid()).getTitle().equals("1"));
        }
        try(DAOManager manager = ProductionProvider.getInstance().getDaoManager()){
            manager.getSpecialConditionDao().remove(specialCondition1.getUuid());
            manager.getSpecialConditionDao().remove(specialCondition2.getUuid());
            manager.getSpecialConditionDao().remove(specialCondition3.getUuid());
            manager.getSpecialConditionDao().remove(specialCondition4.getUuid());
            manager.getSpecialConditionDao().remove(specialCondition5.getUuid());
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            try {
                manager.getSpecialConditionDao().get(specialCondition1.getUuid());
                fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getSpecialConditionDao().get(specialCondition2.getUuid());
                fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getSpecialConditionDao().get(specialCondition3.getUuid());
                fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getSpecialConditionDao().get(specialCondition4.getUuid());
                fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getSpecialConditionDao().get(specialCondition5.getUuid());
                fail();
            } catch (ObjectNotFoundException e) {

            }
        }
    }

}