package dao.database;

import dao.interfaces.*;
import model.account.Account;
import model.account.Function;
import model.account.Role;
import model.identity.Address;
import model.identity.Company;
import model.identity.Person;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by Ponti on 15/03/2017.
 */
public class ProductionFunctionDAOTest {
    private static DAOProvider daoProvider;
    private static FunctionDAO functionDAO;
    private static CompanyDAO companyDAO;
    private static AddressDAO addressDAO;
    private static PersonDAO personDAO;
    private static AccountDAO accountDAO;
    //private static RoleDAO roleDAO;


    //TODO: production to false, when local
    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider(true);
        daoProvider = ProductionProvider.getInstance();
        functionDAO = daoProvider.getFunctionDAO();
        //companyDAO = daoProvider.getCompanyDAO();
        //addressDAO = daoProvider.getAddressDao();
        personDAO = daoProvider.getPersonDAO();
        accountDAO = daoProvider.getAccountDao();
        //roleDAO = daoProvider.getRoleDAO();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
    }

    //TODO: change person creation when create method in PersonDAO gets changed
    @Ignore
    @Test
    public void createGetRemoveTest() throws Exception {
        Company comp1 = null;
        Address adr1 = null;
        Person p1 = null;
        Account acc1 = null;
        Role r1 = null;
        Function f1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a function can be succesfully added to the database
        /*try {
            comp1 = companyDAO.create();
        } catch (Exception e) {
            fail("Failed trying to create a new company");
        }*/
        try {
            adr1 = addressDAO.create("streettest n1","59","town 1","9999","country 1");
        } catch (Exception e) {
            fail("Failed trying to create a new address");
        }
        try {
            p1 = personDAO.create("Firstname 1", "Lastname 1", "Email@address1.com");
        } catch (Exception e) {
            fail("Failed trying to create a new person");
        }
        try {
            acc1 = accountDAO.create("login1", "hashedPassword1", p1);
        } catch (Exception e) {
            fail("Failed trying to create a new account");
        }
        /*try {
            r1 = roleDAO.create();
        } catch (Exception e) {
            fail("Failed trying to create a new role");
        }*/
        try {
            f1 = functionDAO.create(comp1, r1, acc1, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2017, 8, 3, 0, 0));
        } catch (Exception e) {
            fail("Failed trying to create a new function");
        }
        //If a function was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            if (f1 != null) {
                Function f2 = functionDAO.get(f1.getUuid());
                assertEquals("company field not created correctly",f1.getCompany() ,f2.getCompany() );
                assertEquals("role field not created correctly",f1.getRole() ,f2.getRole() );
                assertEquals("account field not created correctly",f1.getAccount() ,f2.getAccount() );
                assertEquals("startDate field not created correctly",f1.getStartDate() ,f2.getStartDate() );
                assertEquals("endDate field not created correctly",f1.getEndDate() ,f2.getEndDate() );
                present = true;
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing function from the database");
        }
        //If the function is confirmed to be present in the database, try to remove it
        try {
            if (f1 != null && present) {
                functionDAO.remove(f1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove an function from the database");
        }
        //Check if the function is effectively removed (if create, get and remove tests passed)
        try {
            if (f1 != null && present && removed) {
                Function f2 = functionDAO.get(f1.getUuid());
                //adding this because I'm not sure if the get method returns a null object or an error for a non existing uuid
                assertNull("Function is still in database after removal", f2);
            }
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (Exception e) {
            //Nothing because the test passed in this case
        }
        //make sure everything is removed from the database again
        if(f1 != null){functionDAO.remove(f1.getUuid());}
        //if(r1 != null){roleDAO.remove(r1.getUuid());}
        if(acc1 != null){accountDAO.remove(acc1.getUuid());}
        if(p1 != null){personDAO.remove(p1.getUuid());}
        if(adr1 != null){addressDAO.remove(adr1.getUuid());}
        if(comp1 != null){companyDAO.remove(comp1.getUuid());}
    }

    @Ignore
    @Test
    public void update() throws Exception {
        Company comp1 = null;
        //Company comp1 = companyDAO.create();
        Address adr1 = addressDAO.create("streettest n1","59","town 1","9999","country 1");
        Person p1 = personDAO.create("Firstname 1", "Lastname 1", "Email@address1.com");
        Account acc1 = accountDAO.create("login1", "hashedPassword1", p1);
        Role r1 = null;
        //Role r1 = roleDAO.create();

        Company comp2 = null;
        //Company comp2 = companyDAO.create();
        Address adr2 = addressDAO.create("streettest n2","60","town 2","99999","country 2");
        Person p2 = personDAO.create("Firstname 2", "Lastname 2", "Email@address2.com");
        Account acc2 = accountDAO.create("login2", "hashedPassword2", p2);
        Role r2 = null;
        //Role r2 = roleDAO.create();
        LocalDateTime t1 = LocalDateTime.of(2017, 7, 15, 0, 0);
        LocalDateTime t2 = LocalDateTime.of(2018, 8, 3, 0, 0);

        Function f1 = functionDAO.create(comp1, r1, acc1, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2017, 8, 3, 0, 0));
        Function f2 = functionDAO.update(f1.getUuid(), comp2, r2, acc2, t1, t2);
        Function f3 = functionDAO.get(f1.getUuid());
        assertEquals("company field not updated correctly", comp2,f3.getCompany() );
        assertEquals("role field not updated correctly", r2,f3.getRole() );
        assertEquals("account field not updated correctly",acc2 ,f3.getAccount() );
        assertEquals("startDate field not updated correctly", t1,f3.getStartDate() );
        assertEquals("endDate field not updated correctly", t2,f3.getEndDate() );

        functionDAO.remove(f1.getUuid());
        //roleDAO.remove(r1.getUuid());
        //roleDAO.remove(r2.getUuid());
        accountDAO.remove(acc1.getUuid());
        accountDAO.remove(acc2.getUuid());
        personDAO.remove(p1.getUuid());
        personDAO.remove(p2.getUuid());
        addressDAO.remove(adr1.getUuid());
        addressDAO.remove(adr2.getUuid());
        companyDAO.remove(comp1.getUuid());
        companyDAO.remove(comp2.getUuid());
    }
}
