package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.DTO.CourseListDTO;
import model.course.Course;
import model.course.CourseStatus;
import service.course.CourseService;
import service.course.CourseServiceImpl;
import service.notification.INotificationService;
import service.notification.NotificationServiceImpl;

@WebServlet(name = "AdminCourseServlet", urlPatterns = {"/admin/courses"})
public class AdminCourseServlet extends HttpServlet {

    private CourseService courseService;
    private INotificationService notificationService;

    @Override
    public void init() throws ServletException {
        this.courseService = new CourseServiceImpl();
        this.notificationService = new NotificationServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("view".equals(action)) {
            viewCourseDetails(request, response);
        } else {
            listCourses(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("update status is called");
        String action = request.getParameter("action");

        if ("updateStatus".equals(action)) {
            updateCourseStatus(request, response);
        }
    }

    private void listCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get search parameters
            String title = request.getParameter("title");
            String status = request.getParameter("status");
            String instructor = request.getParameter("instructor");

            // Pagination
            int page = 1;
            int pageSize = 10;

            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }

            // Get courses with filters
            List<CourseListDTO> courses = courseService.getCoursesList(title, status, instructor, page, pageSize);
            int totalCourses = courseService.getCoursesCount(title, status, instructor);
            int totalPages = (int) Math.ceil((double) totalCourses / pageSize);

            request.setAttribute("courses", courses);
            request.setAttribute("totalCourses", totalCourses);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);

            request.getRequestDispatcher("/admin/courses.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading courses: " + e.getMessage());
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        }
    }

    private void viewCourseDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Course course = courseService.findCourse(Long.valueOf(id));

        if (course == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
            return;
        }

        request.setAttribute("course", course);
        request.getRequestDispatcher("/admin/viewCourse.jsp").forward(request, response);
    }

    private void updateCourseStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        try {
            Long courseId = Long.parseLong(request.getParameter("courseId"));
            String statusStr = request.getParameter("status");
            String reason = request.getParameter("reason");

            CourseStatus newStatus = CourseStatus.valueOf(statusStr);

            // Update course status
            boolean success = courseService.updateCourseStatus(courseId, newStatus, reason);

            if (success) {
                // Send notification to instructor
                Course course = courseService.findById(courseId);
                if (course != null) {
                    String message = createStatusUpdateMessage(newStatus, reason);
                    notificationService.sendNotificationToInstructor(
                            course.getCreatedBy().getId(),
                            message,
                            "/instructor/courses?id=" + courseId
                    );
                }

                out.print(gson.toJson(new ApiResponse(true, "Course status updated successfully")));
            } else {
                out.print(gson.toJson(new ApiResponse(false, "Failed to update course status")));
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print(gson.toJson(new ApiResponse(false, "Error: " + e.getMessage())));
        }
    }

    private String createStatusUpdateMessage(CourseStatus status, String reason) {
        return switch (status) {
            case PUBLIC ->
                "Your course has been approved and is now live!";
            case REJECTED ->
                "Your course has been rejected. Reason: " + (reason != null ? reason : "No reason provided.");
            case ARCHIVE ->
                "Your course has been disabled by admin.";
            case REQUESTING ->
                "Your course has been submitted for review.";
            default ->
                "Your course status has been updated to " + status;
        };

    }

    // Helper class for JSON responses
    private static class ApiResponse {

        private boolean success;
        private String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        // Getters
        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    private void getCourseInfor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
