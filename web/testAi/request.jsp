<%-- 
    Document   : request.jsp
    Created on : Jun 5, 2025, 5:26:31 PM
    Author     : LEGION
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="suggestion-ai-agent" method="get">
            <input type="text" name="message" value="" /><br>

            <input type="submit" value="Send" />
        </form>
        <h2>Trò chiện dới AI nè</h2>
        <c:forEach items="${sessionScope.conversations}" var="c">
            Bạn nhắn:${c.request}<br>
            Ai nhắn:${c.response}<br>
        </c:forEach>

    </body>
</html>
