<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Reading</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
        <script src="https://cdn.tiny.cloud/1/ig8h2aervco4095cr1xa4rswh3zk0islnit36osyurlngni9/tinymce/7/tinymce.min.js" referrerpolicy="origin"></script>
        <link rel="stylesheet" href="courseMaterial/AddReading.css"/>
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />
        <div class="container-fluid">
            <div class="row">
                <form class="right-body col-md-8" action="update-lesson-item" method="POST">
                    <input type="text" name="lessonId" value="${lessonId}" />
                    <input type="text" name="lessonItemId" value="${lessonItemId}" />
                    <input type="text" name="type" value="reading" />
                    <div class="first-row">
                        <p class="lesson-title">Lesson: ${lessonTile}</p>
                        <button type="submit">Update</button>
                    </div>
                    <hr style="border-bottom: 2px solid; margin-top: 5px;">
                    <div class="row second-row">
                        <div class="row col-md-7 title">
                            <p class="col-md-2">Title</p>
                            <input class="row col-md-10" type="text" name="title" value="${reading.title}" placeholder="Reading title"/>
                        </div>
                        <div class="row col-md-2 offset-md-1 duration">
                            <p class="col-md-6">Duration</p>
                            <input class="col-md-4" type="number" name="duration" value="${reading.estimatedDuration}" />
                        </div>
                        <div class="row col-md-2 duration">
                            <p class="col-md-6">Index</p>
                            <input class="col-md-4" type="number" name="index" value="${reading.index}" />
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <p class="col-md-2">Content</p>
                        <textarea class="tinymce-editor" name="description">${reading.content}</textarea>
                    </div>
                </form>
            </div>
        </div>
        <script>
            tinymce.init({
                selector: 'textarea.tinymce-editor',
                plugins: [
                    'anchor', 'autolink', 'charmap', 'codesample', 'emoticons', 'image', 'link',
                    'lists', 'media', 'searchreplace', 'table', 'visualblocks', 'wordcount'
                ],
                toolbar: 'undo redo | bold italic underline | link image media | bullist numlist | removeformat',
                tinycomments_mode: 'embedded',
                tinycomments_author: 'Author name'
            });
        </script>
    </body>
</html>
