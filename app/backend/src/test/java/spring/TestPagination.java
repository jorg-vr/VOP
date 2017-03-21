package spring;

import org.junit.*;
import spring.model.RESTSchema;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;


public class TestPagination {

    private static final Integer SIZE = 10;

    private Collection<Integer> collection;

    @Before
    public void setUp() throws Exception {
        collection = createCollection(SIZE);
    }

    /**
     * Creates a collection that contains the numbers from 0 to size - 1
     */
    private Collection<Integer> createCollection(int size) {
        collection = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            collection.add(i);
        }
        return collection;
    }

    @Test
    public void testNullParameters() throws Exception {
        testNullParametersHelp(null, null);
        testNullParametersHelp(null, 1);
        testNullParametersHelp(1, null);
    }

    private void testNullParametersHelp(Integer page, Integer limit) {
        RESTSchema<Integer> result = new RESTSchema<>(collection, page, limit, "/int?");
        assertEquals(SIZE, result.getTotal());
        assertNull(result.getFirst());
        assertNull(result.getLast());
        assertNull(result.getLimit());
        assertNull(result.getOffset());
        assertNull(result.getNext());
        assertNull(result.getPrevious());
    }

    @Test
    public void testSizeIsLimit() throws Exception {
        Integer page = 0;
        Integer limit = SIZE;
        RESTSchema<Integer> result = new RESTSchema<>(collection, page, limit, "/int?");
        assertEquals(SIZE, result.getTotal());
        assertEquals(limit, result.getLimit());
        assertEquals(new Integer((page * limit)), result.getOffset());
        assertNull(result.getPrevious());
        assertNull(result.getNext());
        String first = "/int?page=0&limit=" + limit;
        assertEquals(first, result.getFirst());
        assertEquals(first, result.getLast());
    }

    @Test
    public void testTwoPages() throws Exception {
        Integer limit = 10;
        int[] sizes = new int[]{limit + 1, limit + limit / 2, 2 * limit};
        for (int size : sizes) {
            Collection<Integer> col = createCollection(size);
            RESTSchema<Integer> result = new RESTSchema<>(col, 0, limit, "/int?");

            assertEquals("/int?page=1&limit=" + limit, result.getNext());
            assertNull(result.getPrevious());
        }
    }

}