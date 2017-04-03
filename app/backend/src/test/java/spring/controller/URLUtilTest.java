package spring.controller;

import org.hibernate.validator.constraints.URL;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Billie Devolder on 26/03/2017.
 */
public class URLUtilTest {

    @Test
    public void replace() throws Exception {
        String url = "/x?a=10";
        assertEquals("/x?a=20",URLUtil.replace(url, "a", "20"));

        url = "/x?a=10&b=10";
        assertEquals("/x?a=20&b=10",URLUtil.replace(url, "a", "20"));

        url = "/x?a=10&a=10";
        assertEquals("/x?a=20&a=20",URLUtil.replace(url, "a", "20"));

        url = "/x?b=10&a=10";
        assertEquals("/x?b=10&a=20",URLUtil.replace(url, "a", "20"));

        // Dont replace strings where a is a substring of
        url = "/x?a=10&aa=10";
        assertEquals("/x?a=20&aa=10",URLUtil.replace(url, "a", "20"));
    }
}