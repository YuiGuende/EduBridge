<%-- 
    Document   : CourseInfor
    Created on : May 25, 2025, 10:39:41 AM
    Author     : LEGION
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Course Information</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <!-- TinyMCE -->
        <script src="https://cdn.tiny.cloud/1/ig8h2aervco4095cr1xa4rswh3zk0islnit36osyurlngni9/tinymce/7/tinymce.min.js" referrerpolicy="origin"></script>

        <style>
            .form-section {
                background: #f8f9fa;
                border-radius: 8px;
                padding: 1.5rem;
                margin-bottom: 1.5rem;
                border: 1px solid #dee2e6;
            }

            .section-title {
                color: #495057;
                font-weight: 600;
                margin-bottom: 1rem;
                padding-bottom: 0.5rem;
                border-bottom: 2px solid #007bff;
            }

            .thumbnail-container {
                border: 2px dashed #dee2e6;
                border-radius: 8px;
                padding: 1rem;
                text-align: center;
                background: #fff;
            }

            .thumbnail-image {
                max-width: 100%;
                height: 200px;
                object-fit: cover;
                border-radius: 6px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }

            .array-item {
                background: #fff;
                border: 1px solid #dee2e6;
                border-radius: 6px;
                padding: 0.75rem;
                margin-bottom: 0.75rem;
            }

            .btn-remove {
                background: #dc3545;
                border: none;
                color: white;
                padding: 0.375rem 0.75rem;
                border-radius: 4px;
                font-size: 0.875rem;
            }

            .btn-add {
                background: #28a745;
                border: none;
                color: white;
                padding: 0.5rem 1rem;
                border-radius: 4px;
                font-size: 0.875rem;
            }

            .alert-custom {
                border-radius: 8px;
                border: none;
                padding: 1rem 1.25rem;
            }

            .alert-success-custom {
                background: #d1edff;
                color: #0c5460;
                border-left: 4px solid #28a745;
            }

            .alert-error-custom {
                background: #f8d7da;
                color: #721c24;
                border-left: 4px solid #dc3545;
            }

            .submit-btn {
                background: linear-gradient(135deg, #007bff, #0056b3);
                border: none;
                padding: 0.75rem 2rem;
                font-weight: 600;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                transition: all 0.3s ease;
            }

            .submit-btn:hover {
                transform: translateY(-1px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.15);
            }

            .course-id-badge {
                background: #e9ecef;
                border: 1px solid #ced4da;
                border-radius: 6px;
                padding: 0.5rem 0.75rem;
                font-family: 'Courier New', monospace;
                font-weight: bold;
                color: #495057;
            }

            .language-info {
                background: #e3f2fd;
                border: 1px solid #bbdefb;
                border-radius: 6px;
                padding: 0.5rem 0.75rem;
                font-size: 0.875rem;
                color: #1565c0;
            }

            .form-select:focus {
                border-color: #007bff;
                box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
            }

            .language-current {
                background: #d4edda;
                color: #155724;
                padding: 0.25rem 0.5rem;
                border-radius: 4px;
                font-size: 0.875rem;
                display: inline-block;
                margin-top: 0.25rem;
            }

            .language-selector {
                position: relative;
            }

            .language-selector::before {
                content: '\f1ab';
                font-family: 'Font Awesome 6 Free';
                font-weight: 900;
                position: absolute;
                left: 12px;
                top: 50%;
                transform: translateY(-50%);
                color: #6c757d;
                z-index: 1;
            }

            .language-selector select {
                padding-left: 2.5rem;
            }
        </style>
    </head>
    <body>
        <!-- Include Header -->
        <jsp:include page="/component/header.jsp" />

        <div class="container-fluid">
            <div class="row">
                <!-- Include Sidebar -->
                <jsp:include page="/component/instructorSideBar/sidebar.jsp"/>
                <!-- Main Content -->
                <div class="col-md-9 col-lg-10 ms-sm-auto px-md-4">
                    <div class="container-fluid py-4">

                        <!-- Page Header -->
                        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                            <h1 class="h2">
                                <i class="fas fa-edit me-2 text-primary"></i>
                                Edit Course Information
                            </h1>

                            <form action="notification" method="POST" class="d-inline-block ms-auto">
                                <input type="hidden" name="action" value="requestValidation" />
                                <input type="hidden" name="id" value="${course.id}" />
                                <button type="submit" class="btn btn-warning">
                                    <i class="fas fa-paper-plane me-1"></i> Request validation
                                </button>
                            </form>

                            <c:if test="${course.status == 'DRAFT'}">
                                <form action="notification" method="POST" class="d-inline-block ms-auto">
                                    <input type="hidden" name="action" value="requestValidation" />
                                    <input type="hidden" name="id" value="${course.id}" />
                                    <button type="submit" class="btn btn-warning">
                                        <i class="fas fa-paper-plane me-1"></i> Request validation
                                    </button>
                                </form>
                            </c:if>
                        </div>

                        <!-- Success/Error Messages -->
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-custom alert-success-custom" role="alert">
                                <i class="fas fa-check-circle me-2"></i>
                                ${successMessage}
                            </div>
                        </c:if>

                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-custom alert-error-custom" role="alert">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                ${errorMessage}
                            </div>
                        </c:if>

                        <!-- Main Form -->
                        <form id="courseForm" action="course" method="POST" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="update" />
                            <input type="hidden" name="id" value="${course.id}" />
                            <!-- Basic Information Section -->
                            <div class="form-section">
                                <h4 class="section-title">
                                    <i class="fas fa-info-circle me-2"></i>
                                    Basic Information
                                </h4>

                                <div class="row">
                                    <div class="col-md-8">
                                        <!-- Course ID and Title -->
                                        <div class="row mb-3">
                                            <div class="col-md-8">
                                                <label for="title" class="form-label">Course Title *</label>
                                                <input type="text" class="form-control" id="title" name="title" 
                                                       value="${course.title}" placeholder="Enter course title" required>
                                            </div>
                                            <div class="col-md-4">
                                                <label for="id" class="form-label">Course ID</label>
                                                <div class="course-id-badge">
                                                    ID: ${course.id}
                                                </div>
                                                <input type="hidden" name="id" value="${course.id}" />
                                            </div>
                                        </div>

                                        <!-- Headline -->
                                        <div class="mb-3">
                                            <label for="headline" class="form-label">Course Headline *</label>
                                            <input type="text" class="form-control" id="headline" name="headline" 
                                                   value="${course.headline}" placeholder="Enter course headline" required>
                                        </div>

                                        <!-- Primary Language -->
                                        <div class="mb-3">
                                            <label for="primaryLanguageId" class="form-label">
                                                <i class="fas fa-language me-1"></i>
                                                Primary Language *
                                            </label>
                                            <div class="language-selector">
                                                <select class="form-select" name="primaryLanguageId" id="primaryLanguageId" required>
                                                    <option value="">-- Select a language --</option>
                                                    <c:forEach var="language" items="${languages}">
                                                        <option value="${language.id}" 
                                                                <c:if test="${course.primaryLanguage.id == language.id}">selected</c:if>>
                                                            ${language.name} (${language.code})
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-text">
                                                <c:if test="${not empty course.primaryLanguage}">
                                                    <span class="language-current">
                                                        <i class="fas fa-check-circle me-1"></i>
                                                        Current: ${course.primaryLanguage.name} (${course.primaryLanguage.code})
                                                    </span>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Thumbnail -->
                                    <div class="col-md-4">
                                        <label class="form-label">Course Thumbnail</label>
                                        <div class="thumbnail-container">
                                            <c:choose>
                                                <c:when test="${not empty course.thumbnailUrl}">
                                                    <input type="hidden" name="imgUrl" value="${course.thumbnailUrl}" />
                                                    <img src="${course.thumbnailUrl}" alt="Course thumbnail" class="thumbnail-image mb-2">
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="text-muted mb-2">
                                                        <i class="fas fa-image fa-3x"></i>
                                                        <p class="mt-2">No image uploaded</p>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                            <input type="file" class="form-control form-control-sm" name="image" accept="image/*">
                                            <small class="text-muted">Recommended: 300x200px, Max: 2MB</small>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Requirements Section -->
                            <div class="form-section">
                                <h4 class="section-title">
                                    <i class="fas fa-list-check me-2"></i>
                                    Course Requirements
                                </h4>

                                <div id="requirementsContainer">
                                    <c:choose>
                                        <c:when test="${not empty course.requirements}">
                                            <c:forEach var="req" items="${course.requirements}" varStatus="status">
                                                <div class="array-item requirement-item">
                                                    <div class="d-flex gap-2">
                                                        <textarea class="form-control" name="requirement" rows="2" 
                                                                  placeholder="Enter requirement">${req}</textarea>
                                                        <button type="button" class="btn btn-remove" onclick="removeRequirement(this)">
                                                            <i class="fas fa-trash"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="array-item requirement-item">
                                                <div class="d-flex gap-2">
                                                    <textarea class="form-control" name="requirement" rows="2" 
                                                              placeholder="Enter requirement"></textarea>
                                                    <button type="button" class="btn btn-remove" onclick="removeRequirement(this)">
                                                        <i class="fas fa-trash"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <button type="button" class="btn btn-add" onclick="addRequirement()">
                                    <i class="fas fa-plus me-1"></i> Add Requirement
                                </button>
                            </div>

                            <!-- Learning Outcomes Section -->
                            <div class="form-section">
                                <h4 class="section-title">
                                    <i class="fas fa-graduation-cap me-2"></i>
                                    Learning Outcomes
                                </h4>

                                <div id="outcomesContainer">
                                    <c:choose>
                                        <c:when test="${not empty course.learningOutcomes}">
                                            <c:forEach var="outcome" items="${course.learningOutcomes}" varStatus="status">
                                                <div class="array-item outcome-item">
                                                    <div class="d-flex gap-2">
                                                        <input type="text" class="form-control" name="outcome" 
                                                               value="${outcome}" placeholder="Enter learning outcome">
                                                        <button type="button" class="btn btn-remove" onclick="removeLearningOutcome(this)">
                                                            <i class="fas fa-trash"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="array-item outcome-item">
                                                <div class="d-flex gap-2">
                                                    <input type="text" class="form-control" name="outcome" 
                                                           placeholder="Enter learning outcome">
                                                    <button type="button" class="btn btn-remove" onclick="removeLearningOutcome(this)">
                                                        <i class="fas fa-trash"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <button type="button" class="btn btn-add" onclick="addLearningOutcome()">
                                    <i class="fas fa-plus me-1"></i> Add Learning Outcome
                                </button>
                            </div>

                            <!-- Target Audience Section -->
                            <div class="form-section">
                                <h4 class="section-title">
                                    <i class="fas fa-users me-2"></i>
                                    Target Audience
                                </h4>

                                <div id="courseForContainer">
                                    <c:choose>
                                        <c:when test="${not empty course.courseFor}">
                                            <c:forEach var="audience" items="${course.courseFor}" varStatus="status">
                                                <div class="array-item coursefor-item">
                                                    <div class="d-flex gap-2">
                                                        <input type="text" class="form-control" name="courseFors" 
                                                               value="${audience}" placeholder="Enter target audience">
                                                        <button type="button" class="btn btn-remove" onclick="removeCourseFor(this)">
                                                            <i class="fas fa-trash"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="array-item coursefor-item">
                                                <div class="d-flex gap-2">
                                                    <input type="text" class="form-control" name="courseFors" 
                                                           placeholder="Enter target audience">
                                                    <button type="button" class="btn btn-remove" onclick="removeCourseFor(this)">
                                                        <i class="fas fa-trash"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <button type="button" class="btn btn-add" onclick="addCourseFor()">
                                    <i class="fas fa-plus me-1"></i> Add Target Audience
                                </button>
                            </div>

                            <!-- Description Section -->
                            <div class="form-section">
                                <h4 class="section-title">
                                    <i class="fas fa-align-left me-2"></i>
                                    Course Description
                                </h4>

                                <textarea class="tinymce-editor" name="description" placeholder="Enter detailed course description">${course.description}</textarea>
                            </div>

                            <!-- Submit Button -->
                            <div class="text-center py-4">
                                <button type="submit" class="btn btn-primary submit-btn" id="submitBtn">
                                    <i class="fas fa-save me-2"></i>
                                    <span id="submitText">Save Course</span>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

        <script>
                                    // Initialize TinyMCE
                                    tinymce.init({
                                        selector: 'textarea.tinymce-editor',
                                        height: 300,
                                        plugins: [
                                            'anchor', 'autolink', 'charmap', 'codesample', 'emoticons', 'image',
                                            'link', 'lists', 'media', 'searchreplace', 'table', 'visualblocks', 'wordcount'
                                        ],
                                        toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | ' +
                                                'link image media table | align lineheight | checklist numlist bullist indent outdent | ' +
                                                'emoticons charmap | removeformat',
                                        content_style: 'body { font-family: -apple-system, BlinkMacSystemFont, San Francisco, Segoe UI, Roboto, Helvetica Neue, sans-serif; font-size: 14px; }',
                                        branding: false,
                                        menubar: false
                                    });

                                    // Language selection change handler
                                    document.getElementById('primaryLanguageId').addEventListener('change', function () {
                                        const selectedOption = this.options[this.selectedIndex];
                                        if (selectedOption.value) {
                                            console.log('Language changed to:', selectedOption.text);
                                            // You can add additional logic here if needed
                                        }
                                    });

                                    // Requirements functions
                                    function addRequirement() {
                                        const container = document.getElementById('requirementsContainer');
                                        const newItem = document.createElement('div');
                                        newItem.className = 'array-item requirement-item';
                                        newItem.innerHTML = `
                    <div class="d-flex gap-2">
                        <textarea class="form-control" name="requirement" rows="2" placeholder="Enter requirement"></textarea>
                        <button type="button" class="btn btn-remove" onclick="removeRequirement(this)">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                `;
                                        container.appendChild(newItem);
                                    }

                                    function removeRequirement(button) {
                                        const container = document.getElementById('requirementsContainer');
                                        if (container.children.length > 1) {
                                            button.closest('.requirement-item').remove();
                                        } else {
                                            alert('At least one requirement is required.');
                                        }
                                    }

                                    // Learning Outcomes functions
                                    function addLearningOutcome() {
                                        const container = document.getElementById('outcomesContainer');
                                        const newItem = document.createElement('div');
                                        newItem.className = 'array-item outcome-item';
                                        newItem.innerHTML = `
                    <div class="d-flex gap-2">
                        <input type="text" class="form-control" name="outcome" placeholder="Enter learning outcome">
                        <button type="button" class="btn btn-remove" onclick="removeLearningOutcome(this)">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                `;
                                        container.appendChild(newItem);
                                    }

                                    function removeLearningOutcome(button) {
                                        const container = document.getElementById('outcomesContainer');
                                        if (container.children.length > 1) {
                                            button.closest('.outcome-item').remove();
                                        } else {
                                            alert('At least one learning outcome is required.');
                                        }
                                    }

                                    // Course For functions
                                    function addCourseFor() {
                                        const container = document.getElementById('courseForContainer');
                                        const newItem = document.createElement('div');
                                        newItem.className = 'array-item coursefor-item';
                                        newItem.innerHTML = `
                    <div class="d-flex gap-2">
                        <input type="text" class="form-control" name="courseFors" placeholder="Enter target audience">
                        <button type="button" class="btn btn-remove" onclick="removeCourseFor(this)">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                `;
                                        container.appendChild(newItem);
                                    }

                                    function removeCourseFor(button) {
                                        const container = document.getElementById('courseForContainer');
                                        if (container.children.length > 1) {
                                            button.closest('.coursefor-item').remove();
                                        } else {
                                            alert('At least one target audience is required.');
                                        }
                                    }

                                    // Form submission handling
                                    document.getElementById('courseForm').addEventListener('submit', function (e) {
                                        const submitBtn = document.getElementById('submitBtn');
                                        const submitText = document.getElementById('submitText');

                                        // Show loading state
                                        submitBtn.disabled = true;
                                        submitText.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Saving...';

                                        // Validate required fields
                                        const title = document.getElementById('title').value.trim();
                                        const headline = document.getElementById('headline').value.trim();
                                        const primaryLanguageId = document.getElementById('primaryLanguageId').value;

                                        if (!title || !headline || !primaryLanguageId) {
                                            e.preventDefault();
                                            alert('Please fill in all required fields (Title, Headline, and Primary Language).');

                                            // Reset button state
                                            submitBtn.disabled = false;
                                            submitText.innerHTML = '<i class="fas fa-save me-2"></i>Save Course';
                                            return;
                                        }

                                        // Update TinyMCE content
                                        tinymce.triggerSave();
                                    });

                                    // Auto-save draft functionality (optional)
                                    let autoSaveTimer;
                                    function autoSave() {
                                        clearTimeout(autoSaveTimer);
                                        autoSaveTimer = setTimeout(() => {
                                            console.log('Auto-saving draft...');
                                            // Implement auto-save logic here
                                        }, 30000); // Auto-save every 30 seconds
                                    }

                                    // Trigger auto-save on input changes
                                    document.addEventListener('input', autoSave);

                                    // Prevent accidental page leave
                                    let formChanged = false;
                                    document.addEventListener('input', () => formChanged = true);

                                    window.addEventListener('beforeunload', function (e) {
                                        if (formChanged) {
                                            e.preventDefault();
                                            e.returnValue = 'You have unsaved changes. Are you sure you want to leave?';
                                        }
                                    });

                                    // Reset form changed flag on successful submit
                                    document.getElementById('courseForm').addEventListener('submit', () => formChanged = false);
        </script>
    </body>
</html>