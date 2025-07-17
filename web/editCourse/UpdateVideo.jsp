<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Video</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="courseMaterial/AddVideo.css"/>
</head>
<body>
<jsp:include page="/component/header.jsp" />
<div class="container-fluid">
    <div class="row">
        <form class="right-body col-md-8" action="update-lesson-item" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="lessonId" value="${lessonId}" />
            <input type="hidden" name="lessonItemId" value="${lessonItemId}" />
            <input type="hidden" name="type" value="video" />
            <div class="first-row">
                <p class="lesson-title">Lesson: ${lessonTile}</p>
                <button type="submit">Update</button>
            </div>
            <hr style="border-bottom: 2px solid; margin-top: 5px;">
            <div class="row second-row">
                <div class="row col-md-7 title">
                    <p class="col-md-2">Title</p>
                    <input class="row col-md-10" type="text" name="title" value="${video.title}" placeholder="Video title"/>
                </div>
                <div class="row col-md-2 offset-md-1 duration">
                    <p class="col-md-6">Duration</p>
                    <input class="col-md-4" type="number" name="duration" value="${video.estimatedDuration}" />
                </div>
                <div class="row col-md-2 duration">
                    <p class="col-md-6">Index</p>
                    <input class="col-md-4" type="number" name="index" value="${video.index}" />
                </div>
            </div>
            <hr>
            <div class="row">
                <p class="col-md-2">Replace Video</p>
                <input type="file" name="video" accept="video/*" />
                <small class="text-muted">Leave blank to keep current video.</small>
            </div>
        </form>
    </div>
</div>
</body>
</html>
