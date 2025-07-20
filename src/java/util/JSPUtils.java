//package util;
//
//
//import jakarta.servlet.http.HttpServletRequest;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class JSPUtils {
//
//    /**
//     * Safely encode string for URL parameters
//     */
//    public static String urlEncode(String value) {
//        if (value == null) {
//            return "";
//        }
//        try {
//            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
//        } catch (UnsupportedEncodingException e) {
//            return value;
//        }
//    }
//
//    /**
//     * Encodes a string for safe display in HTML to prevent XSS attacks.
//     * Replaces characters like <, >, &, ", ' with their HTML entities.
//     *
//     * @param text The string to encode.
//     * @return The HTML-encoded string.
//     */
//    public static String htmlEncode(Object text) {
//        if (text == null) {
//            return "";
//        }
//        String s = text.toString();
//        StringBuilder sb = new StringBuilder(s.length());
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            switch (c) {
//                case '<':
//                    sb.append("&lt;");
//                    break;
//                case '>':
//                    sb.append("&gt;");
//                    break;
//                case '&':
//                    sb.append("&amp;");
//                    break;
//                case '"':
//                    sb.append("&quot;");
//                    break;
//                case '\'':
//                    sb.append("&#39;"); // Apostrophe
//                    break;
//                case '/':
//                    sb.append("&#x2F;"); // Solidus (forward slash)
//                    break;
//                default:
//                    sb.append(c);
//            }
//        }
//        return sb.toString();
//    }
//
//    /**
//     * Get parameter with default value
//     */
//    public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
//        String value = request.getParameter(name);
//        return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
//    }
//
//    /**
//     * Get integer parameter with default value
//     */
//    public static int getIntParameter(HttpServletRequest request, String name, int defaultValue) {
//        try {
//            String value = request.getParameter(name);
//            return (value != null && !value.trim().isEmpty()) ? Integer.parseInt(value) : defaultValue;
//        } catch (NumberFormatException e) {
//            return defaultValue;
//        }
//    }
//
//    /**
//     * Builds a pagination URL by adding or updating page and size parameters.
//     *
//     * @param baseUrl The base URL (e.g., "/admin/reports").
//     * @param currentParams A map of current request parameters.
//     * @param page The new page number.
//     * @param size The new page size.
//     * @return The constructed URL with pagination parameters.
//     */
//    public static String buildPaginationUrl(String baseUrl, Map<String, String[]> currentParams, int page, int size) {
//        StringBuilder url = new StringBuilder(baseUrl);
//        url.append("?");
//
//        // Add existing parameters, excluding 'page' and 'size'
//        for (Map.Entry<String, String[]> entry : currentParams.entrySet()) {
//            String paramName = entry.getKey();
//            if (!"page".equals(paramName) && !"size".equals(paramName)) {
//                for (String paramValue : entry.getValue()) {
//                    url.append(URLEncoder.encode(paramName, StandardCharsets.UTF_8))
//                            .append("=")
//                            .append(URLEncoder.encode(paramValue, StandardCharsets.UTF_8))
//                            .append("&");
//                }
//            }
//        }
//
//        // Add new page and size parameters
//        url.append("page=").append(page);
//        url.append("&size=").append(size);
//
//        return url.toString();
//    }
//
//    public static Date toUtilDate(LocalDateTime localDateTime) {
//        if (localDateTime == null) {
//            return null;
//        }
//        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
//    }
//
//    public static String buildPaginationUrl(HttpServletRequest request, int page) {
//        return buildPaginationUrl(request.getRequestURI(), request.getParameterMap(), page, 10); // Default pageSize to 10
//    }
//
//}
package util;

import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map; // Keep this import if other methods use Map

public class JSPUtils {

    /**
     * Encodes a string for safe display in HTML to prevent XSS attacks.
     * @param text The string to encode.
     * @return The HTML-encoded string.
     */
    public static String htmlEncode(Object text) {
        if (text == null) {
            return "";
        }
        String encodedText = String.valueOf(text);
        StringBuilder sb = new StringBuilder();
        for (char c : encodedText.toCharArray()) {
            switch (c) {
                case '<': sb.append("&lt;"); break;
                case '>': sb.append("&gt;"); break;
                case '&': sb.append("&amp;"); break;
                case '"': sb.append("&quot;"); break;
                case '\'': sb.append("&#39;"); break; // Apostrophe
                case '/': sb.append("&#x2F;"); break; // Forward slash
                default: sb.append(c); break;
            }
        }
        return sb.toString();
    }

    /**
     * Encodes a string for safe use in URL parameters.
     * @param text The string to encode.
     * @return The URL-encoded string.
     */
    public static String urlEncode(String text) {
        if (text == null) {
            return "";
        }
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // This should not happen in a standard Java environment
            throw new RuntimeException("UTF-8 encoding not supported", e);
        }
    }

    /**
     * Converts a LocalDateTime object to a java.util.Date object.
     * Useful for JSTL fmt:formatDate tag.
     * @param localDateTime The LocalDateTime to convert.
     * @return The converted java.util.Date.
     */
    public static Date toUtilDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Builds a pagination URL by preserving existing query parameters and including pageSize from request attributes.
     * @param request The HttpServletRequest.
     * @param page The target page number.
     * @return The constructed URL with updated page and pageSize parameters.
     */
    public static String buildPaginationUrl(HttpServletRequest request, int page) {
        StringBuilder url = new StringBuilder(request.getContextPath());
        url.append(request.getServletPath());
        url.append("?");

        Enumeration<String> paramNames = request.getParameterNames();
        boolean firstParam = true;
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            // Exclude 'page' and 'pageSize' from existing parameters to add the new ones
            if (!"page".equals(paramName) && !"pageSize".equals(paramName)) {
                String[] paramValues = request.getParameterValues(paramName);
                for (String paramValue : paramValues) {
                    if (!firstParam) {
                        url.append("&");
                    }
                    url.append(urlEncode(paramName)).append("=").append(urlEncode(paramValue));
                    firstParam = false;
                }
            }
        }

        // Add the new page parameter
        if (!firstParam) {
            url.append("&");
        }
        url.append("page=").append(page);

        // Add pageSize from request attribute if available, otherwise use a default
        Integer pageSize = (Integer) request.getAttribute("pageSize");
        if (pageSize != null) {
            url.append("&pageSize=").append(pageSize);
        } else {
            // Fallback to a default if pageSize is not explicitly set as an attribute
            url.append("&pageSize=").append(10); // Default pageSize
        }

        return url.toString();
    }

    // You can keep this method if it's used elsewhere, but it's not used in the pagination fix.
    // public static String buildPaginationUrl(String baseUrl, Map<String, String[]> params, int page, int pageSize) {
    //     StringBuilder url = new StringBuilder(baseUrl);
    //     url.append("?");
    //
    //     boolean firstParam = true;
    //     for (Map.Entry<String, String[]> entry : params.entrySet()) {
    //         String paramName = entry.getKey();
    //         if (!paramName.equals("page") && !paramName.equals("pageSize")) {
    //             for (String value : entry.getValue()) {
    //                 if (!firstParam) {
    //                     url.append("&");
    //                 }
    //                 url.append(urlEncode(paramName)).append("=").append(urlEncode(value));
    //                 firstParam = false;
    //             }
    //         }
    //     }
    //
    //     if (!firstParam) {
    //         url.append("&");
    //     }
    //     url.append("page=").append(page);
    //     url.append("&pageSize=").append(pageSize);
    //
    //     return url.toString();
    // }
}
