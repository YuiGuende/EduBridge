package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import model.course.Course;
import service.course.CourseService;
import service.course.CourseServiceImpl;

@WebFilter("/module") // ? Ch? �p d?ng v?i URL: /module
public class CourseModuleFilter implements Filter {

    private final CourseService courseService = new CourseServiceImpl();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String courseIdParam = request.getParameter("courseId");
        if (courseIdParam != null) {
            try {
                Long courseId = Long.valueOf(courseIdParam);
                Course course = courseService.findCourse(courseId);
                int numOfModule = course.getModules().size();
                request.setAttribute("courseId", courseId);
                request.setAttribute("numOfModule", numOfModule);
            } catch (Exception e) {
                request.setAttribute("numOfModule", 0); // fallback n?u l?i
            }
        }

        chain.doFilter(request, response);
    }
}
