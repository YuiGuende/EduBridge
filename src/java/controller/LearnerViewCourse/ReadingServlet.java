//package controller.LearnerViewCourse;
///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
//
//import java.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import model.course.Course;
//import model.course.courseContent.Lesson;
//import model.course.courseContent.LessonItem;
//import model.course.courseContent.Reading;
//import model.course.courseContent.Video;
//import model.user.Learner;
//import service.lesson.ILessonService;
//import service.lesson.LessonServiceImpl;
//import service.lessonItem.ILessonItemService;
//import service.lessonItem.LessonItemServiceImpl;
//
///**
// *
// * @author GoniperXComputer
// */
//@WebServlet(name = "ReadingServlet", urlPatterns = {"/reading-lesson"})
//public class ReadingServlet extends HttpServlet {
//
//    private final ILessonItemService lessonItemService = new LessonItemServiceImpl();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
////        Long lessonItemId = Long.valueOf(request.getParameter("lessonItem"));
////
////        LessonItem item = lessonItemService.findById(lessonItemId);
////
////        if (item instanceof Video video) {
////            request.setAttribute("videoURL", video.getVideoURL());
////            request.setAttribute("courseTitle", video.getLesson().getModule().getCourse().getTitle());
////            request.setAttribute("moduleTitle", video.getLesson().getModule().getTitle());
////            request.setAttribute("lessonTitle", video.getLesson().getTitle());
////
////            request.getRequestDispatcher("/learnerMaterial/lessonVideo.jsp").forward(request, response);
////        } else {
////            // redirect to reading or quiz page
////            response.sendRedirect(request.getContextPath() + "/lesson-reading?lessonItem=" + lessonItemId);
////        }
//    }
//
//}