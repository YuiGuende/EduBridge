/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.homeLearner;

import com.google.gson.Gson;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.course.Course;
import model.course.Tag;
import model.course.TagType;
import service.course.CourseService;
import service.course.CourseServiceImpl;
import service.tag.ITagService;
import service.tag.TagServiceImpl;

/**
 *
 * @author DELL
 */

public class HomeLearnerServlet extends HttpServlet {

    private CourseService courseService;
    private ITagService tagService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        courseService = new CourseServiceImpl();
        tagService = new TagServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tagName = request.getParameter("tag");
        String tagTypeParam = request.getParameter("type");

        if (tagName != null && tagTypeParam != null) {
            TagType tagType = TagType.valueOf(tagTypeParam.toUpperCase());
            System.out.println("=== DEBUG ===");
            System.out.println("param tag = [" + tagName + "]");
            System.out.println("param type = [" + tagTypeParam + "]");
            System.out.println("Enum TagType = [" + tagType + "]");
            Tag tag = tagService.getTagByNameAndType(tagName, tagType);
//            if (tag == null) {
//                System.out.println("Không tìm thấy Tag [" + tagName + "] type [" + tagType + "]");
//                response.sendError(404, "Tag not found");
//                return;
//            }
            List<Course> courses = courseService.getCoursesByTag(tag);
            request.setAttribute("courses", courses);
            if (courses.isEmpty()) {
                request.setAttribute("error", "null");
            }
            if (tagType == TagType.ROLE) {
                request.getRequestDispatcher("/homeLearner/roleCoursesFragment.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/homeLearner/skillCoursesFragment.jsp").forward(request, response);
            }
            return;
        }

        // Lần đầu load trang
        List<Course> trendingCourses = courseService.getTrendingCourses();
        List<Tag> roleTags = tagService.getTagsByType(TagType.ROLE);
        List<Tag> skillTags = tagService.getTagsByType(TagType.SKILL);

        request.setAttribute("trendingCourses", trendingCourses);
        request.setAttribute("roles", roleTags);
        request.setAttribute("skills", skillTags);

        request.getRequestDispatcher("/homeLearner/homeLearner.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
