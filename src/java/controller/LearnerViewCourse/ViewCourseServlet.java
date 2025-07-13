package controller.LearnerViewCourse;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.cart.Coupon;
import model.course.Course;
import model.course.Language;
import model.course.Rate;
import model.course.courseContent.Lesson;
import model.user.Instructor;
import service.course.CourseDetailService;
import service.course.CourseServiceImpl;

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

    private CourseDetailService courseDetailService;

    @Override
    public void init() {
        this.courseDetailService = new CourseDetailService(new CourseServiceImpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long courseId = Long.valueOf(request.getParameter("id"));
            Course course = courseDetailService.getCourseWithDetails(courseId);

            if (course == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
                return;
            }

            Instructor primaryInstructor = courseDetailService.getPrimaryInstructor(course);
            List<model.course.courseContent.Module> module = courseDetailService.getCourseModule(course);
            List<Course> relatedCourses = courseDetailService.getRelatedCourses(course, 3);
            request.setAttribute("relatedcourse", relatedCourses);
            // Tính rating
            double averageRating = 0.0; // M?c ??nh
            int ratingCount = 0; // M?c ??nh
            Rate rate = course.getRate();
            if (rate != null) {
                averageRating = rate.getRate();
                ratingCount = rate.getRateQuantity();
            }
            averageRating = Math.round(averageRating * 10.0) / 10.0;

            System.out.println("course title" + course.getTitle());
            request.setAttribute("course", course);
            request.setAttribute("learningoutcomes", course.getLearningOutcomes());
            request.setAttribute("description", course.getDescription());
            request.setAttribute("imagescourse", course.getThumbnailUrl());
            request.setAttribute("lesson", course.getTotalLessons());
            request.setAttribute("totaltime", course.getEstimatedTime());
            request.setAttribute("price", course.getPrice());
            request.setAttribute("totalmodule", course.getTotalModules());
            request.setAttribute("primaryInstructor", primaryInstructor);
            if (primaryInstructor != null && primaryInstructor.getUser() != null) {
                request.setAttribute("instructorName", primaryInstructor.getUser().getFullname());
            }
            request.setAttribute("avatarmentor", primaryInstructor.getAvatarUrl());
            request.setAttribute("linkedin_profile", primaryInstructor.getLinkedinProfile());
            request.setAttribute("mentorname", primaryInstructor.getUser().getFullname());
            request.setAttribute("special", primaryInstructor.getSpecialization());
            request.setAttribute("education_level", primaryInstructor.getEducationLevel());
            request.setAttribute("descriptionInstructor", primaryInstructor.getBio());
            request.setAttribute("experienced", primaryInstructor.getExperienceYears());
            Language primaryLanguage = course.getLanguage();
            request.setAttribute("language", primaryLanguage);
            request.setAttribute("module", module);
            request.setAttribute("relatedCourses", relatedCourses);
            request.setAttribute("averageRating", averageRating);
            request.setAttribute("ratingCount", ratingCount);
            Coupon coupon = new Coupon();
            request.setAttribute("coupon", coupon);
            request.setAttribute("percentageofcounpon", coupon.getDiscountPercent());
            request.getRequestDispatcher("learnercourse/learnercourse.jsp").forward(request, response);
            Lesson lesson = new Lesson();
            request.setAttribute("lessontitle", lesson.getTitle());
            request.setAttribute("lessontime", lesson.getEstimatedDuration());

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid course ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}