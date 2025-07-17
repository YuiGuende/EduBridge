<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Module</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .section-box {
                border: 1px solid #dee2e6;
                border-radius: 8px;
                padding: 15px;
                margin: 20px auto;
                max-width: 900px;
            }

            .section-title {
                font-weight: 600;
                font-size: 18px;
                margin-bottom: 15px;
            }

            .preview-checkbox {
                margin-top: 10px;
            }
        </style>
    </head>
    <body>

        <div class="container mt-4">


            <form action="module" method="post">
                <input type="hidden" name="action" value="add" />
                <input type="hidden" name="courseId" value="${course.id}" />
                <!-- Basic Info -->
                <div class="section-box">

                    <h4><i class="bi bi-plus-circle"></i> Add Module</h4>
                    <p class="text-muted">Course: <strong>${course.title}</strong></p>
                    <div class="section-title">📘 Basic Information</div>

                    <div class="mb-3">
                        <label for="title" class="form-label">Module Title *</label>
                        <input type="text" class="form-control form-control-sm" name="title" id="title" required>
                    </div>

                    <div class="mb-3">
                        <label for="description" class="form-label">Module Description</label>
                        <textarea class="form-control form-control-sm" name="description" id="description" rows="3"></textarea>
                    </div>

                    <div class="row">
                        <div class="mb-3 col-md-6">
                            <label for="index" class="form-label">Module Index *</label>
                            <input type="number" class="form-control form-control-sm" name="index" id="index" required>
                        </div>

                        <div class="mb-3 col-md-6">
                            <label for="estimatedDuration" class="form-label">Duration (min)</label>
                            <input type="number" class="form-control form-control-sm" name="estimatedDuration" id="estimatedDuration">
                        </div>
                    </div>

                    <div class="form-check preview-checkbox">
                        <input class="form-check-input" type="checkbox" name="preview" id="preview">
                        <label class="form-check-label" for="preview">
                            Allow Preview
                        </label>
                    </div>

                    <input type="hidden" name="courseId" value="${course.id}">
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-primary btn-sm">Add Module</button>
                </div>
            </form>
        </div>

        <!-- Bootstrap JS (optional) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
