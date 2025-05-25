<%-- 
    Document   : AddCourse
    Created on : May 24, 2025, 10:37:06 AM
    Author     : LEGION
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <form action="add-course" method="POST" enctype="multipart/form-data">
                <h5  >Course title</h5>
                <div class="title-div row">

                    <input class="title" type="text" name="title"/>
                </div>
                <h4  >Headline</h4>
                <div class="title-div row">

                    <input class="title" type="text" name="headline"/>
                </div>
                <hr/>
                <div class="requirement-header">
                    <h5 >Learning outcome</h5>
                    <button class="newBtn" type="button" onclick="addLearningOutcome()">Append</button>
                </div>

                <div class="requirement-input row">

                    <div  id="outcomeList">
                        <input type="text" name="outcome"/>
                    </div>

                </div>

                <div class="requirement-header">
                    <h5 >Requirement</h5>
                    <button class="newBtn" type="button" onclick="addRequirements()">Append</button>
                </div>

                <div class="requirement-input row">

                    <div  id="requirementList">
                        <input type="text" name="requirements"/>
                    </div>

                </div>

                <hr/>
                <h5 class="col-md-2">Description</h5>
                <div class="row">

                    <textarea placeholder="description" name="description">
            
                    </textarea>
                </div>
                <hr/>
                <div class="requirement-header">
                    <h5 >Course for</h5>
                    <button class="newBtn" type="button" onclick="addCourseFors()">Append</button>
                </div>

                <div class="requirement-input row">

                    <div  id="courseForList">
                        <input type="text" name="courseFors"/>
                    </div>

                </div>
                <hr/>
                <h5 >Thumbnail</h5>
                <input type="file" name="image" accept="image/*" /><br/>
                <hr/>

                <input type="submit" value="CREATE" id="signinBtn"/>
            </form>
        </div>



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
                            plugins: [
                                // Core editing features
                                'anchor', 'autolink', 'charmap', 'codesample', 'emoticons', 'image', 'link', 'lists', 'media', 'searchreplace', 'table', 'visualblocks', 'wordcount',
                                // Your account includes a free trial of TinyMCE premium features
                                // Try the most popular premium features until Jun 7, 2025:
                                'checklist', 'mediaembed', 'casechange', 'formatpainter', 'pageembed', 'a11ychecker', 'tinymcespellchecker', 'permanentpen', 'powerpaste', 'advtable', 'advcode', 'editimage', 'advtemplate', 'ai', 'mentions', 'tinycomments', 'tableofcontents', 'footnotes', 'mergetags', 'autocorrect', 'typography', 'inlinecss', 'markdown', 'importword', 'exportword', 'exportpdf'
                            ],
                            toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table mergetags | addcomment showcomments | spellcheckdialog a11ycheck typography | align lineheight | checklist numlist bullist indent outdent | emoticons charmap | removeformat',
                            tinycomments_mode: 'embedded',
                            tinycomments_author: 'Author name',
                            mergetags_list: [
                                {value: 'First.Name', title: 'First Name'},
                                {value: 'Email', title: 'Email'},
                            ],
                            ai_request: (request, respondWith) => respondWith.string(() => Promise.reject('See docs to implement AI Assistant')),
                        });

        </script>

    </body>
</html>
