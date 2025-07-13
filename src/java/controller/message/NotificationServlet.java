package controller.message;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.course.Course;
import model.notification.Notification;
import model.user.User;
import service.course.CourseService;
import service.course.CourseServiceImpl;
import service.notification.INotificationService;
import service.notification.NotificationServiceImpl;
import service.user.IUserService;
import service.user.UserServiceImpl;
import ws.NotificationSocket;

@WebServlet("/notification")
public class NotificationServlet extends HttpServlet {

    private final INotificationService notificationService = new NotificationServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();
  private final IUserService userService = new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("requestValidation".equals(action)) {
            try {
                Long courseId = Long.valueOf(request.getParameter("id"));
                Course course = courseService.findCourse(courseId); // gi? ??nh ph??ng th?c n�y c�

                // L?y instructor hi?n t?i t? session
                User instructor = (User) request.getSession().getAttribute("user");

                // T?o n?i dung th�ng b�o
                String message = "Instructor " + instructor.getFullname() + " request validation for course: " + course.getTitle();

                Notification notification = new Notification();
                notification.setMessage(message);
                notification.setSender(instructor);
                notification.setReceiver(userService.findById(1L));
                notificationService.saveNotification(notification);
                NotificationSocket.sendNotificationToUser(999L, message+"Link: http://localhost:9999/EduBridge/view-course?id="+courseId);
//                response.sendRedirect("CourseInfor.jsp?id=" + courseId);
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Request failed: " + e.getMessage());
                e.printStackTrace();
//                response.sendRedirect("CourseInfor.jsp?id=" + request.getParameter("id"));
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
        }
    }
}
