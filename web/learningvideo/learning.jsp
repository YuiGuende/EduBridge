<%-- 
    Document   : learning
    Created on : 7 thg 7, 2025, 15:31:45
    Author     : GoniperXComputer
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta content="width=device-width, initial-scale=1" name="viewport" />
  <title>EDUBRIDGE - ${course.title}</title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
<link rel="stylesheet" href="learningvideo/learningStyles.css"/>
</head>
<body>
  <!-- Top header -->
  
  <header class="header">
    <div class="header-left">
      <img alt="Course" class="logo-coursera" src="${pageContext.request.contextPath}/assets/images/logocap.jpg" />
      <div class="divider"></div>
      <img alt="Banner" class="logo-vanderbilt" src="${pageContext.request.contextPath}/assets/images/banner-login.jpg" />
    </div>
    <div class="name">
      <h3> E D U B R I G D E</h3>
    </div>
    <div class="header-right">
      <button aria-label="Language selector" class="icon-button">
        <i class="fas fa-globe"></i>
      </button>
      <img alt="Avatar user" class="avatar" src="${pageContext.request.contextPath}/assets/images/black-goku-dragon-ball-super-os-2560x1600.jpg" />
      <img alt="avamentor" class="avatar" src="${pageContext.request.contextPath}/assets/images/avamentor.jpg" />
      <button aria-label="User menu dropdown" class="icon-button">
        <i class="fas fa-chevron-down"></i>
      </button>
    </div>
  </header>
  <main class="main">
    <!-- Left sidebar menu -->
    <nav class="sidebar">
      <button class="hide-menu-btn">
        <i class="fas fa-bars"></i>
        <span>Hide menu</span>
      </button>
      <ul class="menu-list">
        <c:forEach items="${course.modules[0].lessons}" var="lesson" varStatus="loop">
          <li class="menu-item ${lesson.active ? 'active' : ''}">
            <div class="border-left"></div>
            <div class="menu-content ${lesson.active ? 'active-content' : ''}">
              <div class="menu-title">
                <i class="fas ${lesson.type == 'video' ? 'fa-play-circle' : 
                                 lesson.type == 'reading' ? 'fa-book-open' : 'fa-code'}"></i>
                <strong><c:out value="${lesson.type == 'video' ? 'Video:' : 
                                      lesson.type == 'reading' ? 'Reading:' : 'Ungraded Plugin:'}"/></strong> 
                <c:out value="${lesson.title}"/>
              </div>
              <c:if test="${not empty lesson.subtitle}">
                <div class="menu-subtitle"><c:out value="${lesson.subtitle}"/></div>
              </c:if>
              <div class="menu-duration"><c:out value="${lesson.duration}"/> min</div>
            </div>
          </li>
        </c:forEach>
      </ul>
    </nav>
    <!-- Main content -->
    <section class="content">
      <div class="breadcrumb">
        <a href="#">${course.title}</a>
        <span>&gt;</span>
        <a href="#">Module 1</a>
        <span>&gt;</span>
        <a href="#">Introduction</a>
      </div>
      <div class="video-container">
        <video controls poster="${pageContext.request.contextPath}/assets/images/${course.bannerImage}" preload="metadata">
          <source src="${pageContext.request.contextPath}/assets/videos/preview1.mp4" type="video/webm" />
          Your browser does not support the video tag.
        </video>
        <div class="video-caption">JAVA FOR NEW BIE.</div>
      </div>
      <a class="report-issue" href="#"><i class="fas fa-flag"></i> Report an issue</a>
      <h2>Introduction</h2>
      <a href="#">NOTE</a>
      <div class="coach-box">
        <div class="coach-title">coach</div>
        <p>Let me know if you have any questions about this material. I'm here to help!</p>
        <div class="coach-buttons">
          <button><i class="fas fa-star"></i> Give me practice questions</button>
          <button><i class="fas fa-star"></i> Explain this topic in simple terms</button>
          <button><i class="fas fa-star"></i> Give me a summary</button>
          <button><i class="fas fa-star"></i> Give me real-life examples</button>
        </div>
      </div>
      <div class="top">
        <div class="top-1">
          <a href="#">DESCRIPTION</a>
          <p style="display: none;"><c:out value="${course.description}"/></p>
        </div>

        <div class="top-1">
          <a href="#">NOTE</a>
          <p style="display: none;">3:40 - lưu ý cách java hoạt động</p>
        </div>

        <div class="top-1">
          <a href="#">HOMEWORK</a>
          <p style="display: none;"> Tìm hiểu về mô hình MVC java</p>
        </div>

        <div class="top-1">
          <a href="#">REVIEW</a>
          <div style="display: none;" class="review-container">
            <div class="rating-summary">
              <h2>Learner reviews</h2>
              <div class="average-rating">
                <span class="score"><fmt:formatNumber value="${averageRating}" pattern="#.##"/></span>
                <span class="total">(${totalReviews} reviews)</span>
              </div>

              <div class="rating-bars">
                <c:forEach begin="5" end="1" step="-1" var="stars">
                  <div class="bar">
                    <span>${stars} stars</span>
                    <div class="progress">
                      <div style="width: ${(ratingDistribution[stars-1]/totalReviews)*100}%"></div>
                    </div> 
                    <span><fmt:formatNumber value="${(ratingDistribution[stars-1]/totalReviews)*100}" pattern="#.##"/>%</span>
                  </div>
                </c:forEach>
              </div>
            </div>
            <div class="user-reviews">
              <p class="review-count">Showing Review</p>

              <c:forEach items="${reviews}" var="review" end="2">
                <div class="review-box">
                  <div class="avatar">${review.userName}</div>
                  <div class="content">
                    <p>${review.rating} <i class="fa-solid fa-star"></i> ・ Reviewed on ${review.date}</p>
                    <p><c:out value="${review.comment}"/></p>
                  </div>
                </div>
              </c:forEach>

              <a href="#" class="view-more">View more reviews</a>
            </div>
          </div>
        </div>
      </div>

      <div class="react">
        <div data-testid="like-button" class="css-e40v4">
          <div class="rc-LikeContent">
            <div>
              <button class="cds-322 cds-button-disableElevation cds-button-ghost css-11hc1hy" tabindex="0" type="button" aria-pressed="false">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M47.6 300.4L228.3 469.1c7.5 7 17.4 10.9 27.7 10.9s20.2-3.9 27.7-10.9L464.4 300.4c30.4-28.3 47.6-68 47.6-109.5v-5.8c0-69.9-50.5-129.5-119.4-141C347 36.5 300.6 51.4 268 84L256 96 244 84c-32.6-32.6-79-47.5-124.6-39.9C50.5 55.6 0 115.2 0 185.1v5.8c0 41.5 17.2 81.2 47.6 109.5z"/></svg>
                Like
              </button>
            </div>
          </div>
        </div>

        <div data-testid="dislike-button" class="css-e40v4">
          <div class="rc-LikeContent">
            <div>
              <button class="cds-322 cds-button-disableElevation cds-button-ghost css-11hc1hy" tabindex="0" type="button" aria-pressed="false">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                  <path d="M119.4 44.1c23.3-3.9 46.8-1.9 68.6 5.3l49.8 77.5-75.4 75.4c-1.5 1.5-2.4 3.6-2.3 5.8s1 4.2 2.6 5.7l112 104c2.9 2.7 7.4 2.9 10.5 .3s3.8-7 1.7-10.4l-60.4-98.1 90.7-75.6c2.6-2.1 3.5-5.7 2.4-8.8L296.8 61.8c28.5-16.7 62.4-23.2 95.7-17.6C461.5 55.6 512 115.2 512 185.1l0 5.8c0 41.5-17.2 81.2-47.6 109.5L283.7 469.1c-7.5 7-17.4 10.9-27.7 10.9s-20.2-3.9-27.7-10.9L47.6 300.4C17.2 272.1 0 232.4 0 190.9l0-5.8c0-69.9 50.5-129.5 119.4-141z"/></svg>
                Dislike
              </button>
            </div>
          </div>
        </div>

        <div class="rc-ShareButtonWithModal" data-e2e="universal-share-cta">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
            <path d="M307 34.8c-11.5 5.1-19 16.6-19 29.2l0 64-112 0C78.8 128 0 206.8 0 304C0 417.3 81.5 467.9 100.2 478.1c2.5 1.4 5.3 1.9 8.1 1.9c10.9 0 19.7-8.9 19.7-19.7c0-7.5-4.3-14.4-9.8-19.5C108.8 431.9 96 414.4 96 384c0-53 43-96 96-96l96 0 0 64c0 12.6 7.4 24.1 19 29.2s25 3 34.4-5.4l160-144c6.7-6.1 10.6-14.7 10.6-23.8s-3.8-17.7-10.6-23.8l-160-144c-9.4-8.5-22.9-10.6-34.4-5.4z"/></svg>
          <a class="text-label css-16bb3oj">Share</a>        
        </div>
      </div>
    </section>
  </main>
  
  <script>
    document.addEventListener("DOMContentLoaded", function () {
      const hideMenuBtn = document.querySelector(".hide-menu-btn");
      const menuList = document.querySelector(".menu-list");

      hideMenuBtn.addEventListener("click", function () {
        menuList.classList.toggle("hidden");
      });
      
      const topItems = document.querySelectorAll(".top-1");

      topItems.forEach((item) => {
        const link = item.querySelector("a");
        const content = item.querySelector("p, .review-container"); 

        link.addEventListener("click", function (e) {
          e.preventDefault();

          topItems.forEach((i) => {
            const c = i.querySelector("p, .review-container");
            if (c) c.style.display = "none";
          });

          if (content) {
            content.style.display = "block";
          }
        });
      });

      document.querySelectorAll('.react button').forEach(btn => {
        btn.addEventListener('click', function () {
          btn.classList.toggle('active');
        });
      });
    });
  </script>
</body>
</html>
