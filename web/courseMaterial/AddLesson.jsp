<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Add Lesson</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f1f1f1;
                margin: 0;
                padding: 0;
            }
            .form-container {
                width: 800px;
                margin: 50px auto;
                background-color: white;
                padding: 40px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                border-radius: 10px;
            }
            h2 {
                text-align: center;
                margin-bottom: 30px;
                color: #333;
            }
            .form-group {
                margin-bottom: 20px;
            }
            .form-inline {
                display: flex;
                gap: 20px;
                margin-bottom: 20px;
            }
            .form-inline .form-subgroup {
                flex: 1;
            }
            label {
                display: block;
                font-weight: bold;
                margin-bottom: 8px;
            }
            input[type="text"],
            input[type="number"],
            textarea {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 14px;
            }
            textarea {
                resize: vertical;
                min-height: 80px;
            }
            .info-text {
                margin-bottom: 30px;
                padding: 12px;
                background-color: #eef;
                border: 1px solid #99c;
                border-radius: 6px;
            }
            .btn-submit {
                background-color: #28a745;
                color: white;
                padding: 12px 20px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                width: 100%;
                font-size: 16px;
            }
            .btn-submit:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>
        <div class="form-container">
            <h2>Add New Lesson</h2>
            <c:if test="${not empty error}">
                <div style="color: red; margin-bottom: 10px;">
                    ${error}
                </div>
            </c:if>
            <div class="info-text">
                <strong>Course:</strong> ${course.title} <br/>
                <strong>Module:</strong> ${module.title}
            </div>

            <form action="lesson" method="post">
                <input type="hidden" name="courseId" value="${course.id}">
                <input type="hidden" name="moduleId" value="${module.id}">
                <input type="hidden" name="action" value="add">
                <div class="form-inline">
                    <div class="form-subgroup">
                        <label for="title">Lesson Title</label>
                        <input type="text" name="title" id="title" required>
                    </div>
                    <div class="form-subgroup" style="max-width: 120px;">
                        <label for="index">Index</label>
                        <input type="number" name="index" id="index" required min="0">
                    </div>
                </div>

                <div class="form-group">
                    <label for="description">Lesson Description</label>
                    <textarea name="description" id="description" placeholder="Enter description (optional)..."></textarea>
                </div>

                <button type="submit" class="btn-submit">Save Lesson</button>
            </form>
        </div>
    </body>
</html>
