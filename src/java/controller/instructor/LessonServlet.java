/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.instructor;

import model.course.courseContent.Module;
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
import model.course.courseContent.Lesson;
import service.Module.IModuleService;
import service.Module.ModuleServiceImpl;
import service.course.CourseService;
import service.course.CourseServiceImpl;
import service.lesson.ILessonService;
import service.lesson.LessonServiceImpl;

/**
 *
 * @author LEGION
 */
@WebServlet(name = "LessonServlet", urlPatterns = {"/lesson"})
public class LessonServlet extends HttpServlet {

    private final ILessonService lessonService = new LessonServiceImpl();
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
            out.println("<title>Servlet LessonServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LessonServlet at " + request.getContextPath() + "</h1>");
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
                getAddLessonPage(request, response);
                break;
            default:
                request.getRequestDispatcher("courseMaterial/Module.jsp").forward(request, response);
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
                saveLesson(request, response);
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

    private void getAddLessonPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long courseId = Long.valueOf(request.getParameter("courseId"));
            System.out.println("course iD" + courseId);
            Course course = courseService.findCourse(courseId);
            if (course != null) {
                request.setAttribute("course", course);
            } else {
                System.out.println("module not found!");
            }
            Long moduleId = Long.valueOf(request.getParameter("moduleId"));

            Module module = course.getModules().stream()
                    .filter(m -> m.getId() == moduleId)
                    .findFirst()
                    .orElse(null); // Trả về null nếu không tìm thấy

            if (module != null) {
                request.setAttribute("module", module);
            } else {
                System.out.println("Module not found in course!");
            }
            request.getRequestDispatcher("courseMaterial/AddLesson.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(LessonServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LessonServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void saveLesson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Module moduleOp = null;
        try {
            // Lấy dữ liệu từ request
            String title = request.getParameter("title");
            int index = Integer.parseInt(request.getParameter("index"));
            String description = request.getParameter("description");

            Long moduleId = Long.valueOf(request.getParameter("moduleId"));

            // Lấy module từ moduleId
            moduleOp = moduleService.findById(moduleId);
            if (moduleOp==null) {
                request.setAttribute("error", "Module not found!");
                getAddLessonPage(request, response);
                return;
            }
            Lesson existedLesson = moduleOp.getLessons().stream()
                    .filter(m -> m.getIndex() == index)
                    .findFirst()
                    .orElse(null); // Trả về null nếu không tìm thấy
            if (existedLesson != null) {
                request.setAttribute("error", "Index is existed, please choose another one");
                getAddLessonPage(request, response);
            }
            // Tạo lesson và gán thông tin
            Lesson lesson = new Lesson();
            lesson.setTitle(title);
            lesson.setIndex(index);
            lesson.setDescription(description); // Nếu có
            lesson.setModule(moduleOp); // Quan trọng: gán module

            // Lưu lesson
            lessonService.save(lesson);

            // Chuyển hướng về danh sách bài học của module hoặc trang chi tiết khóa học
//        response.sendRedirect("lessonList?moduleId=" + moduleId + "&courseId=" + courseId);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error saving lesson: " + e.getMessage());
            
            getAddLessonPage(request, response);
        }
    }

}
