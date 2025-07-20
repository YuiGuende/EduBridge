package util;


import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class JSPUtils {

    /**
     * Safely encode string for URL parameters
     */
    public static String urlEncode(String value) {
        if (value == null) {
            return "";
        }
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    /**
     * Encodes a string for safe display in HTML to prevent XSS attacks.
     * Replaces characters like <, >, &, ", ' with their HTML entities.
     *
     * @param text The string to encode.
     * @return The HTML-encoded string.
     */
    public static String htmlEncode(Object text) {
        if (text == null) {
            return "";
        }
        String s = text.toString();
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '\'':
                    sb.append("&#39;"); // Apostrophe
                    break;
                case '/':
                    sb.append("&#x2F;"); // Solidus (forward slash)
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Get parameter with default value
     */
    public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
    }

    /**
     * Get integer parameter with default value
     */
    public static int getIntParameter(HttpServletRequest request, String name, int defaultValue) {
        try {
            String value = request.getParameter(name);
            return (value != null && !value.trim().isEmpty()) ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Builds a pagination URL by adding or updating page and size parameters.
     *
     * @param baseUrl The base URL (e.g., "/admin/reports").
     * @param currentParams A map of current request parameters.
     * @param page The new page number.
     * @param size The new page size.
     * @return The constructed URL with pagination parameters.
     */
    public static String buildPaginationUrl(String baseUrl, Map<String, String[]> currentParams, int page, int size) {
        StringBuilder url = new StringBuilder(baseUrl);
        url.append("?");

        // Add existing parameters, excluding 'page' and 'size'
        for (Map.Entry<String, String[]> entry : currentParams.entrySet()) {
            String paramName = entry.getKey();
            if (!"page".equals(paramName) && !"size".equals(paramName)) {
                for (String paramValue : entry.getValue()) {
                    url.append(URLEncoder.encode(paramName, StandardCharsets.UTF_8))
                            .append("=")
                            .append(URLEncoder.encode(paramValue, StandardCharsets.UTF_8))
                            .append("&");
                }
            }
        }

        // Add new page and size parameters
        url.append("page=").append(page);
        url.append("&size=").append(size);

        return url.toString();
    }

    public static Date toUtilDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String buildPaginationUrl(HttpServletRequest request, int page) {
        return buildPaginationUrl(request.getRequestURI(), request.getParameterMap(), page, 10); // Default pageSize to 10
    }

}
