package spring.controller;

import javax.servlet.http.HttpServletRequest;

public class URLUtil {

    /**
     * @param request
     * @return if there is a query: format = /x?a=b&c=d
     *         if there is no query: format = /x
     */
    public static String getRelativeURL(HttpServletRequest request) {
        String url = request.getRequestURI();
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }
        return url;
    }

    /**
     * Replaces the value of the query parameter with newValue
     * @param relativeURL string representation of relative url that contains the paramter
     * @param parameter parameter whose value should be changed
     * @param newValue the new value of the parameter
     * @return string with updated value of parameter
     */
    public static String replace(String relativeURL, String parameter, String newValue) {
        return relativeURL.replaceAll("([?&]" + parameter + "=)[^&]+", "$1" + newValue);
    }

    /**
     * Same method as above but this one accepts an integer value for newValue
     */
    public static String replace(String relativeURL, String parameter, int newValue) {
        return replace(relativeURL, parameter, newValue + "");
    }
}
