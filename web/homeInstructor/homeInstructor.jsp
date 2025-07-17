<%--     
    Document   : homeInstructor
    Created on : Jul 9, 2025, 8:42:07 AM
    Author     : DELL
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <link rel="stylesheet" href="homeInstructor/HomeInstructorStyles.css">
        <title>Instructor Dashboard</title>
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />
        <!-- Header -->
        <div class="dashboard-header">
            <div class="header-content">
                <h1><i class="fas fa-chart-line"></i> Instructor Dashboard</h1>
                <div class="header-stats">
                    <span class="last-updated">Last updated: <span id="currentTime"></span></span>
                </div>
            </div>
        </div>

        <!-- Main Dashboard Grid -->
        <div class="dashboard-grid">
            <!-- Quick Stats Cards -->
            <div class="stats-section">
                <div class="stats-grid">
                    <div class="stat-card revenue-card">
                        <div class="stat-icon">
                            <i class="fas fa-dollar-sign"></i>
                        </div>
                        <div class="stat-content">
                            <h3>Total Revenue</h3>
                            <p class="stat-value">
                                <fmt:formatNumber value="${totalRevenue.total}" type="currency" currencySymbol="₫" />
                            </p>
                        </div>
                    </div>

                    <div class="stat-card monthly-card">
                        <div class="stat-icon">
                            <i class="fa-solid fa-calendar-days"></i>
                        </div>
                        <div class="stat-content">
                            <h3>This Month</h3>
                            <p class="stat-value">
                                <fmt:formatNumber value="${totalRevenue.monthly}" type="currency" currencySymbol="₫" />
                            </p>
                        </div>
                    </div>

                    <div class="stat-card yearly-card">
                        <div class="stat-icon">
                            <i class="fa-solid fa-calendar"></i>
                        </div>
                        <div class="stat-content">
                            <h3>This Year</h3>
                            <p class="stat-value">
                                <fmt:formatNumber value="${totalRevenue.yearly}" type="currency" currencySymbol="₫" />
                            </p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Charts Section -->
            <div class="charts-section">
                <!-- Revenue Chart -->
                <div class="chart-card">
                    <div class="chart-header">
                        <h3><i class="fas fa-chart-area"></i> Monthly Revenue Trend</h3>
                    </div>
                    <div class="chart-container">
                        <canvas id="monthlyChart"></canvas>
                    </div>
                </div>

                <!-- Course Performance Chart -->
                <div class="chart-card">
                    <div class="chart-header">
                        <h3><i class="fas fa-chart-pie"></i> Course Performance</h3>
                    </div>
                    <div class="chart-container">
                        <canvas id="courseChart"></canvas>
                    </div>
                </div>
            </div>

            <!-- Weekly Analytics Section -->
            <div class="weekly-section">
                <div class="chart-card weekly-chart-card">
                    <div class="chart-header">
                        <h3><i class="fas fa-calendar-week"></i> Weekly Performance</h3>
                    </div>
                    <div class="weekly-stats-grid">
                        <div class="weekly-stat">
                            <div class="weekly-stat-icon">
                                <i class="fas fa-users"></i>
                            </div>
                            <div class="weekly-stat-content">
                                <span class="weekly-stat-value">${weeklyStats.newStudents}</span>
                                <span class="weekly-stat-label">New Students</span>
                            </div>
                        </div>
                        <div class="weekly-stat">
                            <div class="weekly-stat-icon">
                                <i class="fas fa-shopping-cart"></i>
                            </div>
                            <div class="weekly-stat-content">
                                <span class="weekly-stat-value">${weeklyStats.courseSales}</span>
                                <span class="weekly-stat-label">Course Sales</span>
                            </div>
                        </div>
                    </div>
                    <div class="chart-container">
                        <canvas id="weeklyChart"></canvas>
                    </div>
                </div>

                <!-- Performance Metrics -->
                <div class="analytics-card">
                    <div class="analytics-header">
                        <h3><i class="fas fa-chart-bar"></i> Performance Metrics</h3>
                    </div>
                    <div class="metrics-grid">
                        <div class="metric-item">
                            <div class="metric-icon">
                                <i class="fas fa-graduation-cap"></i>
                            </div>
                            <div class="metric-data">
                                <span class="metric-value">${totalLearners}</span>
                                <span class="metric-label">Total Learners</span>
                            </div>
                        </div>
                        <div class="metric-item">
                            <div class="metric-icon">
                                <i class="fa-solid fa-book-open"></i>
                            </div>
                            <div class="metric-data">
                                <span class="metric-value">${totalCourses}</span>
                                <span class="metric-label">Total Courses</span>
                            </div>
                        </div>
                        <div class="metric-item">
                            <div class="metric-icon">
                                <i class="fas fa-book"></i>
                            </div>
                            <div class="metric-data">
                                <span class="metric-value">${activeCourses}</span>
                                <span class="metric-label">Active Courses</span>
                            </div>
                        </div>
                        <div class="metric-item">
                            <div class="metric-icon">
                                <i class="fas fa-star"></i>
                            </div>
                            <div class="metric-data">
                                <span class="metric-value">${avgRate}</span>
                                <span class="metric-label">Avg Rating</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Data Tables Section -->
            <div class="tables-section">
                <!-- Best Selling Courses -->
                <div class="table-card">
                    <div class="table-header">
                        <h3><i class="fas fa-trophy"></i> Best-Selling Courses</h3>
                        <span class="table-count">${bestSellers.size()} courses</span>
                    </div>
                    <div class="table-container">
                        <table class="modern-table">
                            <thead>
                                <tr>
                                    <th>Rank</th>
                                    <th>Course Name</th>
                                    <th>Students</th>
                                    <th>Revenue</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="course" items="${bestSellers}" varStatus="status">
                                    <tr>
                                        <td>
                                            <span class="rank-badge rank-${status.index + 1}">
                                                #${status.index + 1}
                                            </span>
                                        </td>
                                        <td>
                                            <div class="course-info">
                                                <strong>${course.courseName}</strong>
                                            </div>
                                        </td>
                                        <td>
                                            <span class="student-count">
                                                <i class="fas fa-users"></i> ${course.totalSold}
                                            </span>
                                        </td>
                                        <td>
                                            <span class="revenue-amount">
                                                <fmt:formatNumber value="${course.totalAmount}" type="currency" currencySymbol="₫" />
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Top Buyers -->
                <div class="table-card">
                    <div class="table-header">
                        <h3><i class="fas fa-star"></i> Top Buyers</h3>
                        <span class="table-count">${topBuyers.size()} customers</span>
                    </div>
                    <div class="table-container">
                        <table class="modern-table">
                            <thead>
                                <tr>
                                    <th>Learner</th>
                                    <th>Email</th>
                                    <th>Total Spent</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="buyer" items="${topBuyers}" varStatus="status">
                                    <tr>
                                        <td>
                                            <div class="customer-info">
                                                <div class="customer-details">
                                                    <strong>${buyer.buyerName}</strong>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <span class="email">${buyer.email}</span>
                                        </td>
                                        <td>
                                            <span class="amount-spent">
                                                <fmt:formatNumber value="${buyer.totalSpent}" type="currency" currencySymbol="₫" />
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/component/footer.jsp" %>


        <!-- Chart Scripts -->
        <script>
            // Update current time
            document.getElementById('currentTime').textContent = new Date().toLocaleString();
            // Monthly Revenue Chart
            const monthLabels = [];
            const monthData = [];
            <c:forEach var="m" items="${monthlyRevenue}">
            monthLabels.push("<c:out value='${m.month}'/>");
            monthData.push(<c:out value='${m.revenue}'/>);
            </c:forEach>

            const monthlyChart = new Chart(document.getElementById("monthlyChart"), {
            type: "line",
                    data: {
                    labels: monthLabels,
                            datasets: [{
                            label: "Revenue (VNĐ)",
                                    data: monthData,
                                    borderColor: "#667eea",
                                    backgroundColor: "rgba(102, 126, 234, 0.1)",
                                    fill: true,
                                    tension: 0.4,
                                    pointBackgroundColor: "#667eea",
                                    pointBorderColor: "#ffffff",
                                    pointBorderWidth: 2,
                                    pointRadius: 6
                            }]
                    },
                    options: {
                    responsive: true,
                            maintainAspectRatio: false,
                            plugins: {
                            legend: {
                            display: false
                            }
                            },
                            scales: {
                            y: {
                            beginAtZero: true,
                                    grid: {
                                    color: "rgba(0,0,0,0.05)"
                                    }
                            },
                                    x: {
                                    grid: {
                                    display: false
                                    }
                                    }
                            }
                    }
            });
            // Course Performance Pie Chart
            const courseChart = new Chart(document.getElementById("courseChart"), {
            type: "doughnut",
                    data: {
                    labels: [
            <c:forEach var="course" items="${bestSellers}" varStatus="status">
                    "${course.courseName}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                    ],
                            datasets: [{
                            data: [
            <c:forEach var="course" items="${bestSellers}" varStatus="status">
                ${course.totalSold}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                            ],
                                    backgroundColor: [
                                            "#667eea", "#764ba2", "#f093fb", "#f5576c", "#4facfe"
                                    ],
                                    borderWidth: 0
                            }]
                    },
                    options: {
                    responsive: true,
                            maintainAspectRatio: false,
                            plugins: {
                            legend: {
                            position: 'bottom',
                                    labels: {
                                    padding: 20,
                                            usePointStyle: true
                                    }
                            }
                            }
                    }
            });
            // Chart controls
            document.querySelectorAll('.btn-chart').forEach(btn => {
            btn.addEventListener('click', function() {
            document.querySelectorAll('.btn-chart').forEach(b => b.classList.remove('active'));
            this.classList.add('active');
            });
            });
            // Animate progress bars
            setTimeout(() => {
            document.querySelectorAll('.progress-fill').forEach(bar => {
            bar.style.transition = 'width 1.5s ease-in-out';
            });
            }, 500);
            // Weekly Chart
            // Revenue data from server
            const weeklyRevenue = [];
            <c:forEach var="r" items="${dailyRevenue}">
            weeklyRevenue.push(<c:out value="${r.totalRevenue}" />);
            </c:forEach>

            const weeklyChart = new Chart(document.getElementById("weeklyChart"), {
            type: "bar",
                    data: {
                    labels: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
                            datasets: [
                            {
                            label: "Revenue",
                                    data: weeklyRevenue,
                                    backgroundColor: "rgba(102, 126, 234, 0.8)",
                                    borderColor: "#667eea",
                                    borderWidth: 2,
                                    borderRadius: 8
                            }
                            ]
                    },
                    options: {
                    responsive: true,
                            maintainAspectRatio: false,
                            plugins: {
                            legend: {
                            position: 'top',
                                    labels: {
                                    usePointStyle: true,
                                            padding: 20
                                    }
                            },
                                    tooltip: {
                                    backgroundColor: 'rgba(0,0,0,0.8)',
                                            titleColor: 'white',
                                            bodyColor: 'white',
                                            borderColor: '#667eea',
                                            borderWidth: 1,
                                            cornerRadius: 8,
                                            callbacks: {
                                            label: function(context) {
                                            return 'Revenue: ' + new Intl.NumberFormat('vi-VN', {
                                            style: 'currency',
                                                    currency: 'VND'
                                            }).format(context.parsed.y);
                                            }
                                            }
                                    }
                            },
                            scales: {
                            x: {
                            grid: {
                            display: false
                            }
                            },
                                    y: {
                                    beginAtZero: true,
                                            grid: {
                                            color: "rgba(0,0,0,0.05)"
                                            },
                                            ticks: {
                                            callback: function(value) {
                                            return new Intl.NumberFormat('vi-VN', {
                                            style: 'currency',
                                                    currency: 'VND',
                                                    notation: 'compact'
                                            }).format(value);
                                            }
                                            }
                                    }
                            }
                    }
            });
        </script>
    </body>
</html>
