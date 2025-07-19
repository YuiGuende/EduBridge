
<%-- 
    Document   : instructorInforForm
    Created on : Jul 6, 2025, 3:45:29 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="instructor" class="model.user.Instructor" scope="request" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Instructor Information Form</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Main Styles -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/instructorSignup/InstructorSignupStyles.css" />

        <!-- Bootstrap & Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />

    </head>
    <body>
        <jsp:include page="/component/header.jsp" />

        <div class="container-fluid row">
            <div class="left-side col-md-7">
                <h1>Become an Instructor Today</h1>
                <h4>Change lives — including your own</h4>
            </div>
            <div class="right-side col-md-5">
                <c:if test="${not empty error}">
                    <p class="text-danger text-center fw-bold">${error}</p>
                </c:if>
                <form action="signup-instructor" method="POST" enctype="multipart/form-data" accept-charset="UTF-8" class="needs-validation" novalidate>
                    <input type="hidden" name="step" value="2">
                    <div class="mb-2">
                        <label for="avatarFile" class="form-label">Upload Avatar</label>
                        <input type="file" id="avatarFile" name="avatarFile" class="form-control" accept="image/*" />
                    </div>

                    <div class="mb-2">
                        <label for="bio" class="form-label">Bio</label>
                        <textarea id="bio" name="bio" class="form-control" rows="3">${instructor.bio}</textarea>
                    </div>

                    <!-- Experience + Education Level same row -->
                    <div class="row mb-2">
                        <div class="col-md-5">
                            <label for="experience" class="form-label">Years of Experience <span class="text-danger">*</span></label>
                            <input type="number" min="0" id="experience" name="experience"
                                   class="form-control" value="${instructor.experienceYears}" required />
                            <div class="invalid-feedback">Please enter your years of experience.</div>
                        </div>

                        <div class="col-md-7">
                            <label for="education_level" class="form-label">Education Level <span class="text-danger">*</span></label>
                            <select id="education_level" name="education_level" class="form-select" required>
                                <option value="">Select Education Level</option>
                                <option value="Associate" ${instructor.educationLevel == 'Associate' ? 'selected' : ''}>Associate</option>
                                <option value="Bachelor" ${instructor.educationLevel == 'Bachelor' ? 'selected' : ''}>Bachelor</option>
                                <option value="Master" ${instructor.educationLevel == 'Master' ? 'selected' : ''}>Master</option>
                                <option value="PhD" ${instructor.educationLevel == 'PhD' ? 'selected' : ''}>PhD</option>
                            </select>
                            <div class="invalid-feedback">Please select your education level.</div>
                        </div>
                    </div>

                    <div class="mb-2">
                        <label for="specialization" class="form-label">Specialization <span class="text-danger">*</span></label>
                        <input type="text" id="specialization" name="specialization"
                               class="form-control" value="${instructor.specialization}" required />
                        <div class="invalid-feedback">Please enter your specialization.</div>
                    </div>

                    <div class="mb-3">
                        <label for="linkedin" class="form-label">LinkedIn Profile</label>
                        <input type="text" id="linkedin" name="linkedin" class="form-control" />
                    </div>
                    <div class="mb-3">
                        <label for="bankName" class="form-label">Bank Name<span class="text-danger">*</span></label>
                        <input type="text" id="bankName" name="bankName"
                               class="form-control ${not empty bankNameError ? 'is-invalid' : ''}"
                               value="${bank.bankName}" placeholder="Enter bank name" required />
                        <div class="invalid-feedback">
                            Please enter the bank name.
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="accountName" class="form-label">Account Name<span class="text-danger">*</span></label>
                            <input type="text" id="accountName" name="accountName"
                                   class="form-control ${not empty accountNameError ? 'is-invalid' : ''}"
                                   value="${bank.accountName}" placeholder="Enter account holder name" required />
                            <div class="invalid-feedback">
                                Please enter the account name.
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label for="accountNumber" class="form-label">Account Number<span class="text-danger">*</span></label>
                            <input type="text" id="accountNumber" name="accountNumber"
                                   class="form-control ${not empty accountNumberError ? 'is-invalid' : ''}"
                                   value="${bank.accountNumber}" placeholder="Enter account number" required />
                            <div class="invalid-feedback">
                                Please enter the account number.
                            </div>
                        </div>
                    </div>

                    <div class="d-grid mb-2">
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
        <jsp:include page="/component/footer.jsp" />
        <script>
            (function () {
                'use strict';
                const forms = document.querySelectorAll('.needs-validation');
                Array.from(forms).forEach(function (form) {
                    form.addEventListener('submit', function (event) {
                        if (!form.checkValidity()) {
                            event.preventDefault();
                            event.stopPropagation();
                        }

                        form.classList.add('was-validated');
                    }, false);
                });
            })();
        </script>
    </body>
</html>
