package controller.LearnerViewCourse;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import model.cart.Coupon;
import model.course.Course;
import model.course.CourseLearnerPK;
import model.course.Language;
import model.notification.Comment;
import model.user.Instructor;
import model.user.User;
import service.comment.CommentServiceImpl;
import service.course.CourseDetailService;
import service.course.CourseLearnerServiceImpl;
import service.course.CourseService;
import service.course.CourseServiceImpl;
import service.course.ICourseLearnerService;

/**
 *
 * @author GoniperXComputer
 * <c:choose>
 * <c:when test="${not empty primaryInstructor.studentCount}">
 * ${primaryInstructor.studentCount} Students
 * </c:when>
 * </c:choose>
 */
@WebServlet(name = "ViewCourseServlet", urlPatterns = {"/course-detail"})
public class ViewCourseServlet extends HttpServlet {

    private CourseServiceImpl courseService;
    private final ICourseLearnerService courseLearnerService = new CourseLearnerServiceImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewCourse</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewCourse at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        courseService = new CourseServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Course course = courseService.findCourse(Long.valueOf(id));

        if (course == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
            return;
        }
        HttpSession session = request.getSession();
        User u = (session != null) ? (User) session.getAttribute("user") : null;
        if(u!=null){
            if (courseLearnerService.findCourseLearner(new CourseLearnerPK(Long.valueOf(id), u.getId()))) {
                request.setAttribute("isEnrolled", true);
            }
        }
        
        request.setAttribute("studentNum", courseLearnerService.countStudentInCourse(Long.valueOf(id)));

        request.setAttribute("course", course);
        request.getRequestDispatcher("learningCourse/learnerCourse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
