/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Course;

import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.course.Course;
import service.course.CourseServiceImpl;

/**
 *
 * @author DELL
 */
public class TotalCoursesServlet extends HttpServlet {

    private CourseServiceImpl courseService;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TotalCoursesServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TotalCoursesServlet at " + request.getContextPath() + "</h1>");
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
//        int instructorID = Integer.parseInt(request.getParameter("id"));
        List<Course> courseList;
        String status = request.getParameter("action");
        String keyword = request.getParameter("keyword");
        String sort = request.getParameter("sort");
        if (sort == null) {
            sort = "";
        }
        if (status == null || status.isEmpty()) {
            status = "all";
        }
        int page = 1;
        int limit = 5;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
        } catch (NumberFormatException e) {
            page = 1;
        }
        int offset = (page - 1) * limit;
        int totalRecords;
        totalRecords = courseService.countCoursesOfInstructor(1, status, keyword);
        int totalPages = (int) Math.ceil((double) totalRecords / limit);
        if (keyword == null) {
            courseList = courseService.getCoursesOfInstructorByStatus(1, status, offset, limit);
        } else {
            courseList = courseService.getCourseByKeywordAndStatus(1, keyword, sort, offset, limit);
        }
        courseList = courseService.sortCourses(courseList, sort);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("listCourse", courseList);
        request.setAttribute("action", status);
        request.setAttribute("keyword", keyword);
        request.setAttribute("sort", sort);
        request.getRequestDispatcher("totalCourses/totalCourses.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
