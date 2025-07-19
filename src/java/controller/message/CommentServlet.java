package controller.message;

import ws.NotificationSocket;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.course.Course;
import model.notification.Comment;
import model.user.Instructor;
import model.user.User;
import service.comment.CommentServiceImpl;
import service.comment.ICommentService;
import service.course.CourseService;
import service.course.CourseServiceImpl;
import service.user.IUserService;
import service.user.UserServiceImpl;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

    private final CourseService courseService = new CourseServiceImpl();
    private final ICommentService commentSerivce = new CommentServiceImpl();
    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            System.out.println("comment is called");
            req.setCharacterEncoding("UTF-8");

            // 1. L?y th�ng tin comment t? request
            String content = req.getParameter("content");
            Long courseId = Long.valueOf(req.getParameter("courseId"));
            double rating = Double.valueOf(req.getParameter("rating"));
            Course c = courseService.findCourse(courseId);
            if (c == null) {
                throw new Exception("course not found!");
            }
            c.getRate().addRate(rating);
            courseService.save(c);
            HttpSession session = req.getSession(false);
            User u = (session != null) ? (User) session.getAttribute("user") : null;
            if (c == null) {
                throw new Exception("user not found!");
            }
            commentSerivce.save(new Comment(u, c, content));

            //gửi thông báo tới từng ins của course
            for (Instructor instructor : c.getInstructors()) {
                String message = "Bạn có bình luận mới từ học viên có id:" + u.getId() + "Nội dung: " + content;
                NotificationSocket.sendNotificationToUser(instructor.getId(), message);
            }

//            resp.sendRedirect("course.jsp?id=" + courseId); // ho?c forward l?i
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("comment is called");
        req.getRequestDispatcher("notification.jsp").forward(req, resp);
    }

}
