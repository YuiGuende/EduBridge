/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Course;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import model.course.Course;
import model.course.CourseStatus;
import model.course.Language;
import service.course.CourseServiceImpl;
import util.CloudinaryUtil;
import jakarta.servlet.annotation.MultipartConfig;
import java.util.Optional;
import service.language.ILanguage;
import service.language.LanguageImpl;

/**
 *
 * @author LEGION
 */
@MultipartConfig
public class AddCourseServlet extends HttpServlet {

    private final CourseServiceImpl courseService = new CourseServiceImpl();
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
        request.setAttribute("languages", languageService.findALl());
//        System.out.println("Language"+languageService.findALl().get(0));
        request.getRequestDispatcher("addCourse/AddCourse.jsp").forward(request, response);

    }

    /**
     * "C:\Users\LEGION\Documents\NetBeansProjects\EduBridge\web\" Handles the
     * HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String headline = request.getParameter("headline");
        List<String> requirement = Arrays.asList(request.getParameterValues("requirements"));
        List<String> learningOutCome = Arrays.asList(request.getParameterValues("outcome"));
        String description = request.getParameter("description");
        List<String> courseFors = Arrays.asList(request.getParameterValues("courseFors"));
        Part filePart = request.getPart("image");
        CloudinaryUtil coCloudinaryUtil = new CloudinaryUtil();
        Long languageId = Long.valueOf(request.getParameter("primaryLanguageId"));
        Optional<Language> languageOp = languageService.findById(languageId);//ktra lai dieu kien
        if(languageOp.isEmpty()){
            System.out.println("Language is empty");
        }
        Language language=languageOp.get();
        

        Course course = new Course(title, headline, description , coCloudinaryUtil.upload(filePart), CourseStatus.DRAFT);
        language.setCourses(Arrays.asList(course));
        course.setLanguage(language);
        course.setCourseFor(courseFors);
        course.setRequirements(requirement);
        course.setLearningOutcomes(learningOutCome);
        courseService.addCourse(course);
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
