<%-- 
    Document   : AddCourse
    Created on : May 24, 2025, 10:37:06 AM
    Author     : LEGION
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add course</title>
        <link rel="stylesheet" href="addCourse/AddCourseStyles.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />
        <div class="container">
            <form action="add-course" method="POST" enctype="multipart/form-data" accept-charset="UTF-8" class="needs-validation" novalidate>

                <!-- Primary Language -->
                <div class="form-group mb-3">
                    <label for="languages" class="form-label">Primary Language:</label>
                    <select name="primaryLanguageId" id="languages" class="form-select" required>
                        <option value="">-- Select a language --</option>
                        <c:forEach var="language" items="${languages}">
                            <option value="${language.id}"
                                    <c:if test="${param.primaryLanguageId == language.id}">selected</c:if>>
                                ${language.name} (${language.code})
                            </option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback">
                        Please select a language.
                    </div>
                </div>
                <div class="form-group mb-3">
                    <h4 class="fw-bold">Tag</h4>
                    <c:forEach var="type" items="${tagTypes}">
                        <div class="mb-3">
                            <label class="form-label">${type}</label>
                            <select name="tagName_${type}" class="form-select mb-2">
                                <option value="">-- Select Tag --</option>
                                <c:forEach var="tag" items="${tagMap[type]}">
                                    <option value="${tag.name}">${tag.name}
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">
                                Please select a tag or enter a new one for ${type}.
                            </div>
                            <input name="newTagName_${type}" type="text" class="form-control" placeholder="Or new tag name (optional)">
                            <div class="invalid-feedback">
                                Please select a tag or enter a new one for ${type}.
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="title-div row">
                    <h5  >Course title</h5>
                    <input class="form-control" type="text" name="title" required/>
                    <div class="invalid-feedback">
                        Please enter a course title.
                    </div>
                </div>
                <h6  >Headline</h6>
                <div class="title-div row">
                    <input class="form-control" type="text" name="headline" required/>
                    <div class="invalid-feedback">
                        Please enter a headline.
                    </div>
                </div>
                <hr/>
                <div class="requirement-header">
                    <h5 >Learning outcome</h5>
                    <button class="newBtn" type="button" onclick="addLearningOutcome()">Append</button>
                </div>

                <div class="requirement-input row">

                    <div  id="outcomeList">
                        <div class="mb-3">
                            <input type="text" name="outcome" class="form-control" required/>
                            <div class="invalid-feedback">
                                Please enter at least one learning outcome.
                            </div>
                        </div>
                    </div>

                </div>

                <div class="requirement-header">
                    <h5 >Requirement</h5>
                    <button class="newBtn" type="button" onclick="addRequirements()">Append</button>
                </div>

                <div class="requirement-input row">

                    <div  id="requirementList">
                        <div class="mb-3">
                            <input type="text" name="requirements" class="form-control" required/>
                            <div class="invalid-feedback">
                                Please enter at least one requirement.
                            </div>
                        </div>
                    </div>

                </div>

                <hr/>
                <h5 class="col-md-2">Description</h5>
                <div class="row">

                    <textarea placeholder="description" name="description" class="form-control" required>
            
                    </textarea>
                    <div class="invalid-feedback">
                        Please enter a course description.
                    </div>
                </div>
                <hr/>
                <div class="requirement-header">
                    <h5 >Course for</h5>
                    <button class="newBtn" type="button" onclick="addCourseFors()">Append</button>
                </div>

                <div class="requirement-input row">

                    <div  id="courseForList">
                        <div class="mb-3">
                            <input type="text" name="courseFors" class="form-control" required/>
                            <div class="invalid-feedback">
                                Please enter at least one target audience.
                            </div>
                        </div>

                    </div>

                </div>
                <hr/>
                <div class="mb-3">
                    <label for="price" class="form-label">Price</label>
                    <input type="number" class="form-control" id="price" name="price" placeholder="Enter course price" min="0" step="0.01" required>
                    <div class="invalid-feedback">
                        Please enter a valid price.
                    </div>
                </div>
                <hr/>
                <div class="mb-3">
                    <label for="dcPrice" class="form-label">Discount Price</label>
                    <input type="number" class="form-control" id="price" name="dcPrice" placeholder="Enter course discount price" min="0" step="0.01" required>
                    <div class="invalid-feedback">
                        Please enter a valid price.
                    </div>
                </div>
                <hr/>

                <h5 >Thumbnail</h5>
                <input type="file" name="image" accept="image/*" class="form-control" onchange="validateImage()" required/><br/>
                <hr/>

                <input type="submit" value="CREATE" id="signinBtn"/>
            </form>
        </div>

        <%@ include file="/component/footer.jsp" %>


        <!-- Place the first <script> tag in your HTML's <head> -->
        <script src="https://cdn.tiny.cloud/1/ig8h2aervco4095cr1xa4rswh3zk0islnit36osyurlngni9/tinymce/7/tinymce.min.js" referrerpolicy="origin"></script>

        <!-- Place the following <script> and <textarea> tags your HTML's <body> -->
        <script>

                    function addRequirements() {
                        const container = document.getElementById('requirementList');
                        const input = document.createElement("input");
                        input.type = "text";
                        input.name = "requirements";
                        container.appendChild(input);
                    }
                    function addCourseFors() {
                        const container = document.getElementById('courseForList');
                        const input = document.createElement("input");
                        input.type = "text";
                        input.name = "courseFors";
                        container.appendChild(input);
                    }

                    function addLearningOutcome() {
                        const container = document.getElementById('outcomeList');
                        const input = document.createElement("input");
                        input.type = "text";
                        input.name = "outcome";
                        container.appendChild(input);
                    }
                    tinymce.init({
                        selector: 'textarea',
                        license_key: 'gpl',
                        plugins: [
                            // Core editing features
                            'anchor', 'autolink', 'charmap', 'codesample', 'emoticons', 'image', 'link', 'lists', 'media', 'searchreplace', 'table', 'visualblocks', 'wordcount'
                        ],
                        toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table mergetags | addcomment showcomments | spellcheckdialog a11ycheck typography | align lineheight | checklist numlist bullist indent outdent | emoticons charmap | removeformat',
                        tinycomments_mode: 'embedded',
                        tinycomments_author: 'Author name',
                        mergetags_list: [
                            {value: 'First.Name', title: 'First Name'},
                            {value: 'Email', title: 'Email'}
                        ],
                        ai_request: (request, respondWith) => respondWith.string(() => Promise.reject('See docs to implement AI Assistant'))
                    });

                    (function () {
                        'use strict';
                        const form = document.querySelector('form');

                        form.addEventListener('submit', function (event) {
                            let customValid = true;

                            // Validate từng block Tag (dựa theo class hoặc name bắt đầu)
                            const tagBlocks = document.querySelectorAll('.tag-block'); // Thêm class vào div ngoài

                            tagBlocks.forEach(block => {
                                const select = block.querySelector('select');
                                const input = block.querySelector('input');

                                select.classList.remove('is-invalid', 'is-valid');
                                input.classList.remove('is-invalid', 'is-valid');

                                if (select.value.trim() === '' && input.value.trim() === '') {
                                    select.classList.add('is-invalid');
                                    input.classList.add('is-invalid');
                                    customValid = false;
                                } else {
                                    if (select.value.trim() !== '') {
                                        select.classList.add('is-valid');
                                    }
                                    if (input.value.trim() !== '') {
                                        input.classList.add('is-valid');
                                    }
                                }
                            });

                            // Validate Bootstrap còn lại
                            if (!form.checkValidity() || !customValid) {
                                event.preventDefault();
                                event.stopPropagation();
                            }

                            form.classList.add('was-validated');
                        }, false);
                    })();
                    function validateImage() {
                        const imageInput = document.getElementById('image');
                        if (imageInput.files.length > 0) {
                            imageInput.classList.remove('is-invalid');
                            imageInput.classList.add('is-valid');
                        } else {
                            imageInput.classList.remove('is-valid');
                        }
                    }


        </script>

    </body>
</html>
