package controller.message;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.notification.Report;
import model.notification.ReportType;
import model.notification.ReportTarget;
import model.user.User;
import service.report.IReportService;
import service.report.ReportServiceImpl;
import service.user.UserServiceImpl;
import ws.NotificationSocket;

import java.io.IOException;
import java.util.List;
import model.course.Course;
import service.course.CourseService;
import service.course.CourseServiceImpl;
import service.user.IUserService;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    private final IUserService userService = new UserServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();
    private IReportService reportService;

    @Override
    public void init() {
        reportService = new ReportServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("viewAll".equals(action)) {
            List<Report> reports = reportService.findAll();
            request.setAttribute("reports", reports);
            request.getRequestDispatcher("admin/list.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Hành động không hợp lệ.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            handleCreate(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hành động không hợp lệ.");
        }
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            request.setCharacterEncoding("UTF-8");

//            User currentUser = (User) request.getSession().getAttribute("user");
//            if (currentUser == null) {
//                response.sendRedirect("login.jsp");
//                return;
//            }
            Long learnerId = Long.valueOf(request.getParameter("learnerId"));

            User currentUser = userService.findById(learnerId);
            if (currentUser == null) {
                throw new Exception("user not found!");
            }
            Long targetId = Long.valueOf(request.getParameter("targetId"));
            String reason = request.getParameter("reason");
            ReportType type = ReportType.valueOf(request.getParameter("type"));
            System.out.println("targetid" + targetId);
            Course course = courseService.findCourse(targetId);  // lấy Course từ DB
            System.out.println("course " + course.getTitle());
            if (course == null) {
                throw new Exception("Course not found!");
            }
            Report report = new Report(currentUser, course, reason, type);  // vì Course extends ReportTarget

            reportService.save(report);

            // Gửi thông báo đến admin id = 999
            String message = "Bạn có báo cáo mới từ người dùng ID: " + currentUser.getId()
                    + " - Loại: " + type.name();
            NotificationSocket.sendNotificationToUser(999L, message);

            response.sendRedirect("report?action=viewAll");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Không thể tạo báo cáo.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
