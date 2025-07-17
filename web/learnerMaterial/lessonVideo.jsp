<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title>${courseTitle} - ${lessonTitle}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background-color: #f9fbfc;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        }

        .breadcrumb-bar {
            padding: 1rem 2rem;
            background-color: white;
            border-bottom: 1px solid #ddd;
            font-size: 0.95rem;
        }

        .video-container {
            display: flex;
            justify-content: center;
            margin-top: 2rem;
        }

        .video-wrapper {
            background: #fff;
            padding: 1rem;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.08);
            max-width: 960px;
            width: 100%;
        }

        video {
            width: 100%;
            border-radius: 12px;
        }

        .nav-links {
            display: flex;
            justify-content: space-between;
            margin-top: 1rem;
        }

        .nav-links a {
            font-weight: 600;
            color: #0d6efd;
            text-decoration: none;
        }

        .nav-links a:hover {
            text-decoration: underline;
        }

        .report {
            margin-top: 1rem;
            font-size: 0.9rem;
            color: #0d6efd;
            text-decoration: none;
        }

        .report:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <!-- Breadcrumb -->
    <div class="breadcrumb-bar">
        <a href="#">${courseTitle}</a> >
        <a href="#">${moduleTitle}</a> >
        <span>${lessonTitle}</span>
        <span class="float-end"><a href="#" class="text-primary">Next <i class="fas fa-chevron-right"></i></a></span>
    </div>

    <!-- Video section -->
    <div class="video-container">
        <div class="video-wrapper">
            <video controls>
                <source src="${videoURL}" type="video/mp4">
                Your browser does not support the video tag.
            </video>
            <div class="nav-links">
                <a href="#">&#8592; Previous</a>
                <a href="#">Next &#8594;</a>
            </div>
            <a href="#" class="report">Report an issue</a>
        </div>
    </div>

</body>
</html>
