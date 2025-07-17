package controller.admin;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;
import model.DTO.DashboardStatsDTO;
import model.DTO.PendingApprovalDTO;
import model.DTO.RecentActivityDTO;
import model.notification.Report;
import service.course.CourseService;
import service.course.CourseServiceImpl;
import service.notification.INotificationService;
import service.notification.NotificationServiceImpl;
import service.report.IReportService;
import service.report.ReportServiceImpl;
import service.user.IUserService;
import service.user.UserServiceImpl;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {
    
    private CourseService courseService;
    private IUserService userService;
    private IReportService reportService;
    private INotificationService notificationService;
    
    @Override
    public void init() throws ServletException {
        // Initialize services - assume you have service implementations
        this.courseService = new CourseServiceImpl();
        this.userService = new UserServiceImpl();
        this.reportService = new ReportServiceImpl();
        this.notificationService = new NotificationServiceImpl();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Get dashboard statistics
            DashboardStatsDTO stats = getDashboardStats();
            request.setAttribute("dashboardStats", stats);
            
            // Get recent activities
            List<RecentActivityDTO> recentActivities = getRecentActivities();
            request.setAttribute("recentActivities", recentActivities);
            
            // Get pending approvals
            List<PendingApprovalDTO> pendingApprovals = getPendingApprovals();
            request.setAttribute("pendingApprovals", pendingApprovals);
            
            // Get recent reports
            List<Report> recentReports = reportService.getRecentReports(5);
            request.setAttribute("recentReports", recentReports);
            
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        }
    }
    
    private DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        
        stats.setTotalCourses(courseService.getTotalCoursesCount());
        stats.setActiveInstructors(userService.getActiveInstructorsCount());
        stats.setTotalStudents(userService.getTotalStudentsCount());
        stats.setPendingReports(reportService.getPendingReportsCount());
        
        return stats;
    }
    
    private List<RecentActivityDTO> getRecentActivities() {
        // Implementation to get recent activities
        return courseService.getRecentActivities(10);
    }
    
    private List<PendingApprovalDTO> getPendingApprovals() {
        // Implementation to get pending course approvals
        return courseService.getPendingApprovals(5);
    }
}
