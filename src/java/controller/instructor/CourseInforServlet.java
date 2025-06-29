/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.instructor;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.course.Course;
import model.course.CourseStatus;
import model.course.Language;
import service.course.CourseServiceImpl;
import service.language.ILanguage;
import service.language.LanguageImpl;
import util.CloudinaryUtil;

/**
 *
 * @author LEGION
 */
@MultipartConfig
@WebServlet(name = "CourseInfor", urlPatterns = {"/course"})
public class CourseInforServlet extends HttpServlet {

    private final CourseServiceImpl courseServiceImpl = new CourseServiceImpl();
    private final ILanguage languageService = new LanguageImpl();

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
            out.println("<title>Servlet CourseInforServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CourseInforServlet at " + request.getContextPath() + "</h1>");
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
            case "viewModule":
                request.getRequestDispatcher("courseMaterial/Module.jsp").forward(request, response);
                break;
            case "edit":
                break;
            case "delete":
                break;
            default:
                Course course = courseServiceImpl.findCourse(Long.valueOf(request.getParameter("id")));
                request.setAttribute("numOfModule", course.getModules().size());
                System.out.println("Primary language" + course.getPrimaryLanguage().getName());
                request.setAttribute("languages", languageService.findALl());
                request.setAttribute("course", course);
                request.getRequestDispatcher("editCourse/CourseInfor.jsp").forward(request, response);
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
            case "update":

                updateCourse(request, response);
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

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) {
        try {

            Long id = Long.valueOf(request.getParameter("id"));
            System.out.println("updatinggggg" + id);
            String title = request.getParameter("title");
            System.out.println("UPDATING " + title);
            String headline = request.getParameter("headline");
            List<String> requirement = Arrays.asList(request.getParameterValues("requirement"));
            List<String> learningOutCome = Arrays.asList(request.getParameterValues("outcome"));
            String description = request.getParameter("description");
            List<String> courseFors = Arrays.asList(request.getParameterValues("courseFors"));
            Part filePart = request.getPart("image");
            String img = request.getParameter("imgUrl");
            CloudinaryUtil coCloudinaryUtil = new CloudinaryUtil();
            if (filePart != null && filePart.getSize() > 0 && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
                img = coCloudinaryUtil.upload(filePart);
            }
            System.out.println("IMAGE TO UPDATE"+img);

            Long languageId = Long.valueOf(request.getParameter("primaryLanguageId"));
            Optional<Language> languageOp = languageService.findById(languageId);//ktra lai dieu kien
            if (languageOp.isEmpty()) {
                System.out.println("Language is empty");
            }
            Language language = languageOp.get();

            Course course = new Course(id, title, headline, description, img, CourseStatus.DRAFT);
            language.setCourses(Arrays.asList(course));
            course.setLanguage(language);
            course.setCourseFor(courseFors);
            course.setRequirements(requirement);
            course.setLearningOutcomes(learningOutCome);
            System.out.println("Learning out come 1"+learningOutCome.get(0));
            courseServiceImpl.addCourse(course);
        } catch (IOException ex) {
            Logger.getLogger(CourseInforServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(CourseInforServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
