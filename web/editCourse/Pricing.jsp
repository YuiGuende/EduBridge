<%-- 
    Document   : Pricing
    Created on : May 25, 2025, 5:57:58 PM
    Author     : LEGION
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pricing </title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="editCourse/style.css">
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />
        <div class="container-fluid">
            <div class="row">
                <jsp:include page="/component/instructorSideBar/sidebar.jsp" />
                <form class="right-body col-md-8 " action="add-course" method="POST" enctype="multipart/form-data">

                    <div class="row">
                        <div class="row col-md-8">
                            <h4 class="col-md-12">Original price</h4>
                            <span> <input class="title row col-md-6" type="text" name="title" value="" placeholder="Original price"/></span>
                        </div>
                        <div class="row col-md-3 offset-md-1">
                            <h4 class="col-md-12">Currency</h4>
                            <span> <input class="title row col-md-6" type="text" name="title" value="" placeholder="Original price"/></span>
                        </div>

                    </div>


                    <div class="row">
                        <h4 class="col-md-12">Sale(%)</h4>
                        <span> <input class="title row col-md-6" type="text" name="title" value="" placeholder="Requirement"/></span>
                        <!--  them jstl-->
                    </div>


                </form>
            </div>


    </body>
</html>