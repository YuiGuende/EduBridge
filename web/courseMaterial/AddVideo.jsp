<%-- 
    Document   : AddVideo
    Created on : Jun 6, 2025, 5:02:55 PM
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
        <link rel="stylesheet" href="courseMaterial/AddVideo.css"/>

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
                        <input type="file" name="video" accept="video/*" required />
                    </div>


                </form>
            </div>          
    </body>
</html>