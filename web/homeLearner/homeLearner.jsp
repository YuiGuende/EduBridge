<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="homeLearner/HomeLearnerStyles.css">
        <title>EduBridge - Online Learning Platform</title>
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />

        <!-- Hero Banner -->
        <section class="hero-banner">
            <div class="hero-content">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-lg-8">
                            <h1 class="hero-title">Learn without limits</h1>
                            <p class="hero-subtitle">Start, switch, or advance your career with thousands of courses, Professional Certificates, and degrees from world-class universities and companies.</p>
                        </div>
                        <div class="col-lg-4 text-center">
                            <img src="resource/images/home-banner1.webp" alt="Learning Banner" class="hero-image"/>
                        </div>                        
                    </div>
                </div>
            </div>
        </section>

        <!-- Browse by Roles Section -->
        <section class="browse-section">
            <div class="container">
                <div class="section-header">
                    <h2 class="section-title">Choose Your Role</h2>
                    <p class="section-subtitle">Find courses tailored to your career path</p>
                </div>
                <div class="tab-slider-container">
                    <button class="slider-arrow slider-arrow-left" id="role-tab-left">
                        <i class="fas fa-chevron-left"></i>
                    </button>
                    <div class="tab-slider-wrapper">
                        <ul class="custom-tabs" id="role-tabs">
                            <c:forEach var="roleTag" items="${roles}" varStatus="loop">
                                <li class="tab-item">
                                    <a href="#" class="tab-link role-tab ${loop.index == 0 ? 'active' : ''}" data-role="${roleTag.name}">
                                        ${roleTag.name}
                                    </a>
                                </li>
                            </c:forEach>    </ul>
                    </div>
                    <button class="slider-arrow slider-arrow-right" id="role-tab-right">
                        <i class="fas fa-chevron-right"></i>
                    </button>
                </div>

                <div class="course-slider-container">
                    <button class="slider-arrow slider-arrow-left" id="role-left">
                        <i class="fas fa-chevron-left"></i>
                    </button>
                    <div class="course-slider-wrapper">
                        <div id="role-courses" class="course-slider"></div>
                    </div>
                    <button class="slider-arrow slider-arrow-right" id="role-right">
                        <i class="fas fa-chevron-right"></i>
                    </button>
                </div>
            </div>
        </section>

        <!-- Browse by Skills Section -->
        <section class="browse-section skills-section">
            <div class="container">
                <div class="section-header">
                    <h2 class="section-title">Category Based on Skills</h2>
                    <p class="section-subtitle">Master the skills that matter most</p>
                </div>
                <div class="tab-slider-container">
                    <button class="slider-arrow slider-arrow-left" id="skill-tab-left">
                        <i class="fas fa-chevron-left"></i>
                    </button>
                    <div class="tab-slider-wrapper">
                        <ul class="custom-tabs" id="skill-tabs">
                            <c:forEach var="skillTag" items="${skills}" varStatus="loop">
                                <li class="tab-item">
                                    <a href="#" class="tab-link skill-tab ${loop.index == 0 ? 'active' : ''}" data-skill="${skillTag.name}">
                                        ${skillTag.name}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <button class="slider-arrow slider-arrow-right" id="skill-tab-right">
                        <i class="fas fa-chevron-right"></i>
                    </button>
                </div>

                <div class="course-slider-container">
                    <button class="slider-arrow slider-arrow-left" id="skill-left">
                        <i class="fas fa-chevron-left"></i>
                    </button>
                    <div class="course-slider-wrapper">
                        <div id="skill-courses" class="course-slider"></div>
                    </div>
                    <button class="slider-arrow slider-arrow-right" id="skill-right">
                        <i class="fas fa-chevron-right"></i>
                    </button>
                </div>
            </div>
        </section>

        <!-- Secondary Banner -->
        <section class="secondary-banner">
            <div class="container">
                <img src="resource/images/home-banner3.png" alt="Learning Journey" class="secondary-banner-img"/>
            </div>
        </section>

        <!-- Trending Courses Section -->
        <section class="trending-section">
            <div class="container">
                <div class="section-header">
                    <h2 class="section-title">
                        Trending Courses
                    </h2>
                    <p class="section-subtitle">Most popular courses in the community</p>
                </div>
                <div class="course-slider-container">
                    <button class="slider-arrow slider-arrow-left" id="trending-left">
                        <i class="fas fa-chevron-left"></i>
                    </button>
                    <div class="course-slider-wrapper">
                        <div id="trending-courses" class="course-slider">
                            <c:forEach var="course" items="${trendingCourses}">
                                <a href="course-detail?id=${course.id}" class="course-card">
                                    <div class="course-image-container">
                                        <img src="${course.thumbnailUrl}" class="course-image" alt="${course.title}">
                                    </div>
                                    <div class="course-content">
                                        <h3 class="course-title">${course.title}</h3>
                                        <p class="course-instructor">
                                            ${course.createdBy.user.fullname}
                                            <c:if test="${not empty course.instructors}">
                                                ,
                                                <c:forEach var="ins" items="${course.instructors}" varStatus="loop">
                                                    ${ins.user.fullname}<c:if test="${!loop.last}">, </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </p>

                                        <div class="course-rating">
                                            <span class="rating-score">

                                                <c:out value="${course.rate.rate}" default="0.0"/>
                                            </span>
                                            <div class="stars">
                                                <c:set var="fullStars" value="${course.rate.rate - (course.rate.rate % 1)}" />
                                                <c:set var="halfStar" value="${course.rate.rate % 1 >= 0.5}" />
                                                <c:set var="emptyStars" value="${5 - fullStars - (halfStar ? 1 : 0)}" />

                                                <c:forEach begin="1" end="${fullStars}" var="i">
                                                    <i class="fas fa-star"></i>
                                                </c:forEach>
                                                <c:if test="${halfStar}">
                                                    <i class="fas fa-star-half-alt"></i>
                                                </c:if>
                                                <c:forEach begin="1" end="${emptyStars}" var="i">
                                                    <i class="far fa-star"></i>
                                                </c:forEach>
                                            </div>
                                            <span class="rating-count">
                                                (<c:out value="${course.rate.rateQuantity}" default="0"/>)
                                            </span>
                                        </div>

                                        <div class="course-footer">
                                            <span class="course-price">${course.price} VND</span>
                                        </div>
                                    </div>
                                </a>
                            </c:forEach>
                        </div>
                    </div>
                    <button class="slider-arrow slider-arrow-right" id="trending-right">
                        <i class="fas fa-chevron-right"></i>
                    </button>
                </div>
            </div>
        </section>

        <%@ include file="/component/footer.jsp" %>

        <script>
            document.addEventListener('DOMContentLoaded', () => {
                function initTabSlider(wrapperId, leftId, rightId) {
                    const wrapper = document.getElementById(wrapperId);
                    const leftBtn = document.getElementById(leftId);
                    const rightBtn = document.getElementById(rightId);

                    function updateButtons() {
                        const maxScroll = wrapper.scrollWidth - wrapper.clientWidth;
                        leftBtn.style.display = wrapper.scrollLeft <= 0 ? 'none' : 'flex';
                        rightBtn.style.display = wrapper.scrollLeft >= maxScroll - 1 ? 'none' : 'flex';
                    }

                    leftBtn.addEventListener('click', () => {
                        wrapper.scrollBy({left: -200, behavior: 'smooth'});
                    });
                    rightBtn.addEventListener('click', () => {
                        wrapper.scrollBy({left: 200, behavior: 'smooth'});
                    });

                    wrapper.addEventListener('scroll', updateButtons);
                    window.addEventListener('resize', updateButtons);

                    updateButtons();
                }
                initTabSlider('role-tabs', 'role-tab-left', 'role-tab-right');
                initTabSlider('skill-tabs', 'skill-tab-left', 'skill-tab-right');
                function initBlockSlider(trackId, leftId, rightId, blockSize) {
                    const track = document.getElementById(trackId);
                    const leftBtn = document.getElementById(leftId);
                    const rightBtn = document.getElementById(rightId);

                    let currentScroll = 0;

                    function updateButtons() {
                        const maxScroll = track.scrollWidth - track.clientWidth;
                        leftBtn.style.display = currentScroll <= 0 ? 'none' : 'flex';
                        rightBtn.style.display = currentScroll >= maxScroll ? 'none' : 'flex';
                    }

                    leftBtn.addEventListener('click', () => {
                        currentScroll -= blockSize;
                        if (currentScroll < 0)
                            currentScroll = 0;
                        track.scrollTo({left: currentScroll, behavior: 'smooth'});
                        updateButtons();
                    });

                    rightBtn.addEventListener('click', () => {
                        const maxScroll = track.scrollWidth - track.clientWidth;
                        currentScroll += blockSize;
                        if (currentScroll > maxScroll)
                            currentScroll = maxScroll;
                        track.scrollTo({left: currentScroll, behavior: 'smooth'});
                        updateButtons();
                    });

                    // Initial check
                    updateButtons();
                }
                // Card: 300px + 24px gap = ~324px mỗi card
                // 4 cards -> 1296px
                initBlockSlider('trending-courses', 'trending-left', 'trending-right', 1296);

                // Tab: Mỗi tab giả sử 150px + 20px, 6 tabs ~1020px
                initBlockSlider('role-courses', 'role-left', 'role-right', 1020);
                initBlockSlider('skill-courses', 'skill-left', 'skill-right', 1020);

                function checkArrowVisibility(trackId, leftId, rightId) {
                    const track = document.getElementById(trackId);
                    const leftBtn = document.getElementById(leftId);
                    const rightBtn = document.getElementById(rightId);

                    if (track.scrollWidth <= track.clientWidth) {
                        leftBtn.style.display = 'none';
                        rightBtn.style.display = 'none';
                    }
                }

                checkArrowVisibility('trending-courses', 'trending-left', 'trending-right');
                checkArrowVisibility('role-courses', 'role-left', 'role-right');
                checkArrowVisibility('skill-courses', 'skill-left', 'skill-right');

                // Role tabs functionality
                document.querySelectorAll('.role-tab').forEach(tab => {
                    tab.addEventListener('click', function (e) {
                        e.preventDefault();
                        document.querySelectorAll('.role-tab').forEach(t => t.classList.remove('active'));
                        this.classList.add('active');

                        const tag = encodeURIComponent(this.dataset.role);
                        const url = `home-learner?tag=` + tag + `&type=ROLE`;

                        fetch(url)
                                .then(res => res.text())
                                .then(html => {
                                    document.getElementById('role-courses').innerHTML = html;
                                })
                                .catch(error => console.error('Error:', error));
                    });
                });

                // Skill tabs functionality
                document.querySelectorAll('.skill-tab').forEach(tab => {
                    tab.addEventListener('click', function (e) {
                        e.preventDefault();
                        document.querySelectorAll('.skill-tab').forEach(t => t.classList.remove('active'));
                        this.classList.add('active');

                        const tag = encodeURIComponent(this.dataset.skill);
                        const url = `home-learner?tag=` + tag + `&type=SKILL`;

                        fetch(url)
                                .then(res => res.text())
                                .then(html => {
                                    document.getElementById('skill-courses').innerHTML = html;
                                })
                                .catch(error => console.error('Error:', error));
                    });
                });

                // Initialize first tabs
                const firstRoleTab = document.querySelector('.role-tab');
                const firstSkillTab = document.querySelector('.skill-tab');
                if (firstRoleTab)
                    firstRoleTab.click();
                if (firstSkillTab)
                    firstSkillTab.click();
            });
        </script>
    </body>
</html>