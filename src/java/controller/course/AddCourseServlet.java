/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.course;

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
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import model.course.Tag;
import model.course.TagType;
import model.user.Instructor;
import model.user.User;
import service.language.ILanguage;
import service.language.LanguageImpl;
import service.tag.ITagService;
import service.tag.TagServiceImpl;

/**
 *
 * @author LEGION
 */
@MultipartConfig
public class AddCourseServlet extends HttpServlet {

    private final CourseServiceImpl courseService = new CourseServiceImpl();
    private final ILanguage languageService = new LanguageImpl();
    private final ITagService tagService = new TagServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            List<Language> languages = languageService.findALl();
            request.setAttribute("languages", languages);

            List<String> tagTypes = Arrays.stream(TagType.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            request.setAttribute("tagTypes", tagTypes);

            Map<String, List<Tag>> tagMap = new HashMap<>();
            for (TagType type : TagType.values()) {
                List<Tag> tagsByType = tagService.getTagsByType(type);
                tagMap.put(type.name(), tagsByType);
            }
            request.setAttribute("tagMap", tagMap);

            request.getRequestDispatcher("/addCourse/AddCourse.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot load Add Course page.");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (!"Instructor".equalsIgnoreCase(user.getRole())) {
            request.setAttribute("error", "Only instructors can create courses.");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }

        Instructor instructor = user.getInstructor();
        if (instructor == null) {
            request.setAttribute("error", "Instructor information is missing.");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }
        try {

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
            if (languageOp.isEmpty()) {
                System.out.println("Language is empty");
            }
            Language language = languageOp.get();

            Course course = new Course(title, headline, description, coCloudinaryUtil.upload(filePart), CourseStatus.DRAFT);
            language.setCourses(Arrays.asList(course));
            course.setLanguage(language);
            course.setCourseFor(courseFors);
            course.setRequirements(requirement);
            course.setLearningOutcomes(learningOutCome);
            course.setCreatedBy(instructor);
            List<String> tagTypes = Arrays.stream(TagType.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());

            for (String typeName : tagTypes) {
                String selectedTag = request.getParameter("tagName_" + typeName);
                String newTag = request.getParameter("newTagName_" + typeName);
                String tagName = (selectedTag != null && !selectedTag.isBlank()) ? selectedTag : newTag;
                System.out.println("Tag name: " + tagName);
                if (tagName != null && !tagName.isBlank()) {
                    System.out.println("Tag name to be search "+tagName);
                    Tag tag = tagService.getTagByNameAndType(tagName, TagType.valueOf(typeName));
                    System.out.println("Tag found by service "+tag.toString());
                    if (tag == null) {
                        tag = new Tag(); // Dùng lại biến tag
                        tag.setName(tagName);
                        tag.setType(TagType.valueOf(typeName));
                        tag = tagService.save(tag);
                    }

                    course.addTag(tag); // Bây giờ tag luôn != null
                }
            }
            courseService.addCourse(course);
            response.sendRedirect("total-courses");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("ERROR: " + e.getMessage());
            request.setAttribute("error", "An unexpected error occurred.");
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

}
