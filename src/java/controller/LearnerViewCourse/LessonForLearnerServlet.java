/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.LearnerViewCourse;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.course.courseContent.LessonItem;
import model.course.courseContent.Reading;
import model.course.courseContent.Video;
import service.lessonItem.ILessonItemService;
import service.lessonItem.LessonItemServiceImpl;

/**
 *
 * @author LEGION
 */
@WebServlet(name = "LessonForLearnerServlet", urlPatterns = {"/lessons-learner"})
public class LessonForLearnerServlet extends HttpServlet {

    private final ILessonItemService lessonItemService = new LessonItemServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long lessonItemId = Long.valueOf(request.getParameter("lessonItem"));

        LessonItem item = lessonItemService.findById(lessonItemId);

        if (item instanceof Video) {//lỗi ở dòng này vì khi tôi cmt thì hết lỗi
            Video video = (Video) item;
            request.setAttribute("videoURL", video.getVideoURL());
            request.setAttribute("courseTitle", video.getLesson().getModule().getCourse().getTitle());
            request.setAttribute("moduleTitle", video.getLesson().getModule().getTitle());
            request.setAttribute("lessonTitle", video.getLesson().getTitle());

            request.getRequestDispatcher("learnerMaterial/lessonVideo.jsp").forward(request, response);
        }
        else if (item instanceof Reading) {
            Reading reading = (Reading) item;

            request.setAttribute("readingTitle", reading.getTitle());
            request.setAttribute("readingContent", reading.getContent());
            request.setAttribute("courseTitle", reading.getLesson().getModule().getCourse().getTitle());
            request.setAttribute("moduleTitle", reading.getLesson().getModule().getTitle());
            request.setAttribute("lessonTitle", reading.getLesson().getTitle());

            request.getRequestDispatcher("/learnerMaterial/lessonReading.jsp").forward(request, response);
        }else {
            // redirect to reading or quiz page
            response.sendRedirect(request.getContextPath() + "/lesson-reading?lessonItem=" + lessonItemId);
        }
    }
}
