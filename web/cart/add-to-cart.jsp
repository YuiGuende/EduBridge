<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Course to Cart</title>
</head>
<body>
    <h2>Add Course to Cart</h2>

    <form action="${pageContext.request.contextPath}/cart" method="post">
        <label for="courseId">Course ID:</label>
        <input type="number" id="courseId" name="id" required min="1" />
        <input type="hidden" name="action" value="add" />
        <button type="submit">Add to Cart</button>
    </form>

    <br>
    <a href="${pageContext.request.contextPath}/cart?action=view">Go to Cart</a>
</body>
</html>
