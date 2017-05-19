package database.dao;

import dao.database.ProductionProvider;
import dao.interfaces.DAOManager;
import model.history.LogAction;
import model.history.LogEntry;
import model.account.User;
import model.history.LogResource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by sam on 5/19/17.
 */
public class ProductionLogEntryDAOTest {

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
    public void getAllLogs() throws Exception {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        UUID uuid4 = UUID.randomUUID();
        UUID uuid5 = UUID.randomUUID();
        UUID uuid6 = UUID.randomUUID();
        User user = new User();
        user.setLastName("test");
        user.setFirstName("test2");
        user.setEmail("test@test.be");
        user.setNotHashedPassword("test");

        LogEntry logEntry = new LogEntry();
        logEntry.setTime(LocalDateTime.now());
        logEntry.setAction(LogAction.CREATE);

        logEntry.setUser(user);
        logEntry.setInterested(new ArrayList<>(Arrays.asList(new UUID[]{uuid2,uuid3})));
        logEntry.setResource(LogResource.CONTRACT);
        logEntry.setObject(uuid1);

        LogEntry logEntry2 = new LogEntry();
        logEntry2.setTime(LocalDateTime.now());
        logEntry2.setAction(LogAction.CREATE);

        logEntry2.setUser(user);
        logEntry2.setInterested(new ArrayList<>(Arrays.asList(new UUID[]{uuid5,uuid6,uuid3})));
        logEntry2.setResource(LogResource.CONTRACT);
        logEntry2.setObject(uuid4);

        LogEntry logEntry3 = new LogEntry();
        logEntry3.setTime(LocalDateTime.now());
        logEntry3.setAction(LogAction.CREATE);

        logEntry3.setUser(user);
        logEntry3.setInterested(new ArrayList<>(Arrays.asList(new UUID[]{uuid1,uuid5,uuid6,uuid3})));
        logEntry3.setResource(LogResource.CONTRACT);
        logEntry3.setObject(uuid2);

        try(DAOManager manager = ProductionProvider.getInstance().getDaoManager()){
            manager.getUserDAO().create(user);
            manager.getLogEntryDao().create(logEntry);
            manager.getLogEntryDao().create(logEntry2);
            manager.getLogEntryDao().create(logEntry3);
        }
        try(DAOManager manager = ProductionProvider.getInstance().getDaoManager()){
            Collection<LogEntry> ids= manager.getLogEntryDao().getAllLogs(uuid1);
            assertTrue(ids.contains(logEntry));
            assertTrue(!ids.contains(logEntry2));
            assertTrue(ids.contains(logEntry3));
            ids= manager.getLogEntryDao().getAllLogs(uuid4);
            assertTrue(!ids.contains(logEntry));
            assertTrue(ids.contains(logEntry2));
            assertTrue(!ids.contains(logEntry3));
            ids= manager.getLogEntryDao().getAllLogs(uuid3);
            assertTrue(ids.contains(logEntry));
            assertTrue(ids.contains(logEntry2));
            assertTrue(ids.contains(logEntry3));
        }

    }

    @Test
    public void crud() throws Exception {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        UUID uuid4 = UUID.randomUUID();
        UUID uuid5 = UUID.randomUUID();
        UUID uuid6 = UUID.randomUUID();
        User user = new User();
        user.setLastName("test");
        user.setFirstName("test2");
        user.setEmail("test@test.be");
        user.setNotHashedPassword("test");

        LogEntry logEntry = new LogEntry();
        logEntry.setTime(LocalDateTime.now());
        logEntry.setAction(LogAction.CREATE);

        logEntry.setUser(user);
        logEntry.setInterested(new ArrayList<>(Arrays.asList(new UUID[]{uuid2,uuid3})));
        logEntry.setResource(LogResource.CONTRACT);
        logEntry.setObject(uuid1);

        LogEntry logEntry2 = new LogEntry();
        logEntry2.setTime(LocalDateTime.now());
        logEntry2.setAction(LogAction.CREATE);

        logEntry2.setUser(user);
        logEntry2.setInterested(new ArrayList<>(Arrays.asList(new UUID[]{uuid5,uuid6,uuid3})));
        logEntry2.setResource(LogResource.CONTRACT);
        logEntry2.setObject(uuid4);

        LogEntry logEntry3 = new LogEntry();
        logEntry3.setTime(LocalDateTime.now());
        logEntry3.setAction(LogAction.CREATE);

        logEntry3.setUser(user);
        logEntry3.setInterested(new ArrayList<>(Arrays.asList(new UUID[]{uuid1,uuid5,uuid3})));
        logEntry3.setResource(LogResource.CONTRACT);
        logEntry3.setObject(uuid2);
        try(DAOManager manager = ProductionProvider.getInstance().getDaoManager()){
            manager.getUserDAO().create(user);
            manager.getLogEntryDao().create(logEntry);
            manager.getLogEntryDao().create(logEntry2);
            manager.getLogEntryDao().create(logEntry3);
        }
        logEntry.setAction(LogAction.UPDATE);
        logEntry2.setResource(LogResource.ROLE);
        logEntry3.setObject(uuid6);
        logEntry.getInterested().add(uuid6);
        
    }

}