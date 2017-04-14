package spring.controller;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import util.UUIDUtil;

import java.util.UUID;

import static org.junit.Assert.*;


public class UUIDUtilTest {

    private UUID UUID_MIN = new UUID(Long.MIN_VALUE, Long.MIN_VALUE);
    private UUID UUID_MIDDLE = new UUID(0, 0);
    private UUID UUID_MAX = new UUID(Long.MAX_VALUE, Long.MAX_VALUE);
    private UUID[] UUID_VALUES = new UUID[]{UUID_MIN, UUID_MIDDLE, UUID_MAX};

    private String STRING_0 = "0";
    private String STRING_1 = "176208472498";
    private String[] STRING_VALUES = new String[]{STRING_0, STRING_1};

    /**
     * Test if uuid == toUUID(UUIDToNumberString(uuid)
     *
     * @throws Exception
     */
    @Test
    public void testInverse1() throws Exception {
        // UUID to String and back to UUID
        for (UUID uuid : UUID_VALUES) {
            String str = UUIDUtil.UUIDToNumberString(uuid);
            UUID uuid2 = UUIDUtil.toUUID(str);
            assertEquals(uuid, uuid2);
        }
    }

    /**
     * Test if string == UUIDToNumberString(toUUID(string))
     *
     * @throws Exception
     */
    @Test
    public void testInverse2() throws Exception {
        // UUID to String and back to UUID
        for (String string : STRING_VALUES) {
            UUID uuid = UUIDUtil.toUUID(string);
            String string2 = UUIDUtil.UUIDToNumberString(uuid);
            assertEquals(string, string2);
        }
    }

    @Test
    public void testNull() throws Exception {
        assertNull(UUIDUtil.toUUID(null));
        assertNull(UUIDUtil.UUIDToNumberString(null));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testNotANumber() throws Exception {
        thrown.expect(NumberFormatException.class);
        UUIDUtil.toUUID("aeeabbeb");
    }

    @Test
    public void testNegativeNumber() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        UUIDUtil.toUUID("-10");
    }
}