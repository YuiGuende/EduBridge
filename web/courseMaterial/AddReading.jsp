<%-- 
    Document   : AddReading
    Created on : Jun 1, 2025, 12:46:40 PM
    Author     : LEGION
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
        <script src="https://cdn.tiny.cloud/1/ig8h2aervco4095cr1xa4rswh3zk0islnit36osyurlngni9/tinymce/7/tinymce.min.js" referrerpolicy="origin"></script>
        <link rel="stylesheet" href="courseMaterial/AddReading.css"/>

    </head>
    <body>
        <jsp:include page="/component/header.jsp" />
        <div class="container-fluid">

            <div class="row ">

                <form class="right-body col-md-8 " action="add-lesson-item" method="POST" enctype="multipart/form-data">
                    <div class="first-row">
                        <p class="lesson-title">Lesson: How to become a millionaire</p>
                        <button value="submit" >Add</button>
                    </div>
                    <hr style="border-bottom:  2px solid;margin-top: 5px;">
                    <div class="row second-row">
                        <div class="row col-md-7 title" >
                            <p class="col-md-2">Title</p>
                            <input class=" row col-md-10 " type="text" name="title" value="${course.title}" placeholder="Course title"/>
                        </div>
                        <div class="row col-md-2 offset-md-1 duration">
                            <p class="col-md-6" style="padding-left: 0px;">Duration</p>
                            <input class="col-md-4 " type="number" name="duration" />
                        </div>
                        <div class="row col-md-2 duration">
                            <p class="col-md-6" style="padding-left: 0px;">Index</p>
                            <input class="col-md-4 " type="number" name="index" />
                        </div>
                    </div> 
                    <hr>
                    <div class="row">
                        <p class="col-md-2">Content</p>
                        <textarea class="tinymce-editor" placeholder="description" name="description">
                        </textarea>
                    </div>


                </form>
            </div>
            <script>
                tinymce.init({
                    selector: 'textarea.tinymce-editor',
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
