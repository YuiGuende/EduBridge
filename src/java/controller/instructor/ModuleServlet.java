/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.instructor;

import DAO.module.IModuleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.course.Course;
import service.Module.IModuleService;
import service.Module.ModuleServiceImpl;
import model.course.courseContent.Module;
import service.course.CourseService;
import service.course.CourseServiceImpl;

/**
 *
 * @author LEGION
 */
@WebServlet(name = "ModuleServlet", urlPatterns = {"/module"})
public class ModuleServlet extends HttpServlet {

    private final IModuleService moduleService = new ModuleServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ModuleServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ModuleServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }
        switch (action) {
            case "add":
                getAddModulePage(request, response);
                break;
            default:
                getModulePage(request, response);
                break;
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("POST IS CALLED");

        System.out.println("action is:" + action);
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "add":
                saveModule(request, response);
                break;
            case "update":
                break;
        }
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

    private void saveModule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Lấy dữ liệu từ request
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            int index = Integer.parseInt(request.getParameter("index"));
            int estimatedDuration = request.getParameter("estimatedDuration") != null && !request.getParameter("estimatedDuration").isEmpty()
                    ? Integer.parseInt(request.getParameter("estimatedDuration"))
                    : 0;
            boolean isPreview = request.getParameter("preview") != null;

            // Lấy thông tin khóa học
            Long courseId = Long.valueOf(request.getParameter("courseId"));

            Course course = courseService.findCourse(courseId);
            System.out.println("Course name" + course.getTitle());
            // Tạo module mới
            Module module = new Module();
            module.setCourse(course);
            module.setTitle(title);
            module.setDescription(description);
            module.setIndex(index);
            module.setEstimatedDuration(estimatedDuration);
            module.setPreview(isPreview);

            // Lưu vào database
            moduleService.save(module);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private void getAddModulePage(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long id = Long.valueOf(request.getParameter("courseId"));
            Course course = courseService.findCourse(id);
            if (course != null) {
                request.setAttribute("course", course);
                request.getRequestDispatcher("courseMaterial/AddModule.jsp").forward(request, response);
            } else {
                System.out.println("module not found!");
            }
        } catch (ServletException ex) {
            Logger.getLogger(ModuleServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModuleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getModulePage(HttpServletRequest request, HttpServletResponse response) {

        try {

            Long id = Long.valueOf(request.getParameter("courseId"));
            int index = Integer.parseInt(request.getParameter("index"));
            Module module = moduleService.findByIndexAndCourseId(index, id);
            if (module != null) {
                request.setAttribute("module", module);
                request.getRequestDispatcher("courseMaterial/Module.jsp").forward(request, response);
            } else {
                System.out.println("module not found!");
            }

        } catch (ServletException ex) {
            Logger.getLogger(ModuleServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModuleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
