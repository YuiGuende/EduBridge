/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.course;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.course.courseContent.Lesson;
import model.course.courseContent.Reading;
import model.course.courseContent.Video;
import service.lesson.ILessonService;
import service.lesson.LessonServiceImpl;
import service.reading.IReadingService;
import service.reading.ReadingServiceImpl;
import service.video.IVideoService;
import service.video.VideoServiceImpl;
import util.CloudinaryUtil;

/**
 *
 * @author LEGION
 */

@MultipartConfig
@WebServlet(name = "AddLessonItem", urlPatterns = {"/add-lesson-item"})
public class AddLessonItem extends HttpServlet {

    private final IReadingService readingService = new ReadingServiceImpl();

    private final IVideoService videoService = new VideoServiceImpl();
    private final ILessonService lessonService = new LessonServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type = request.getParameter("type");
        if (type == null) {
            type = "";
        }
        switch (type) {
            case "video":
                getAddVideoPage(request, response);
                break;
            case "quiz":
                getAddQuizPage(request, response);
                break;
            case "reading":
                getAddReadingPage(request, response);
                break;
            default:

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
        String type = request.getParameter("type");
        if (type == null) {
            type = "";
        }

        System.out.println("post is called");
        switch (type) {
            case "video":
                System.out.println("add video is called");
                addVideo(request, response);
                break;
            case "reading":
                addReading(request, response);
                break;
            default:

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


    private void addVideo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            String title = request.getParameter("title");
            int duration = Integer.parseInt(request.getParameter("duration"));
            int index = Integer.parseInt(request.getParameter("index"));

            Part filePart = request.getPart("video");
            String fileName = filePart.getSubmittedFileName();
            CloudinaryUtil coCloudinaryUtil = new CloudinaryUtil();
            // Upload video to Cloudinary
            String videoUrl = coCloudinaryUtil.uploadVideo(filePart);

            // Tạo Video entity
            Video video = new Video();
            video.setTitle(title);
            video.setEstimatedDuration(duration);
            video.setIndex(index);
            video.setVideoURL(videoUrl);

            // Gán lesson nếu cần (nếu bạn có lessonId từ form)
            long lessonId = Long.parseLong(request.getParameter("lessonId"));
            Optional<Lesson> lessonOp = lessonService.findById(lessonId);
            if (lessonOp.isEmpty()) {
                throw new Exception("lesson not found!");
            }
            video.setLesson(lessonOp.get());
            // Gọi Service để lưu video
            videoService.saveVideo(video);

            response.sendRedirect("success.jsp");

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Upload video failed.");
            request.getRequestDispatcher("courseMaterial/AddVideo.jsp").forward(request, response);
        }
    }

    private void addReading(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            String title = request.getParameter("title");
            String description = request.getParameter("description");
            int duration = Integer.parseInt(request.getParameter("duration"));
            int index = Integer.parseInt(request.getParameter("index"));

            long lessonId = Long.parseLong(request.getParameter("lessonId"));
            Optional<Lesson> lessonOp = lessonService.findById(lessonId);
            if (lessonOp.isEmpty()) {
                throw new Exception("Lesson not found");
            }

            // Tạo entity
            Reading reading = new Reading();
            reading.setTitle(title);
            reading.setContent(description);
            reading.setEstimatedDuration(duration);
            reading.setIndex(index);
            reading.setLesson(lessonOp.get());

            // Gọi service để lưu
            readingService.saveReading(reading);

            response.sendRedirect("success.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to add reading material.");
            request.getRequestDispatcher("courseMaterial/AddReading.jsp").forward(request, response);
        }
    }

    private void getAddVideoPage(HttpServletRequest request, HttpServletResponse response) {
        try {

            long lessonId = Long.parseLong(request.getParameter("lessonId"));
            Optional<Lesson> lessonOp = lessonService.findById(lessonId);
            if (lessonOp.isEmpty()) {
                throw new Exception("lesson not found!");
            }
            request.setAttribute("lessonTile", lessonOp.get().getTitle());
            request.setAttribute("lessonId", lessonOp.get().getId());
            request.getRequestDispatcher("courseMaterial/AddVideo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(AddLessonItem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddLessonItem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AddLessonItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getAddReadingPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            long lessonId = Long.parseLong(request.getParameter("lessonId"));
            Optional<Lesson> lessonOp = lessonService.findById(lessonId);
            if (lessonOp.isEmpty()) {
                throw new Exception("lesson not found!");
            }
            request.setAttribute("lessonTile", lessonOp.get().getTitle());
            request.setAttribute("lessonId", lessonOp.get().getId());
            request.getRequestDispatcher("courseMaterial/AddReading.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getAddQuizPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("courseMaterial/AddQuiz.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(AddLessonItem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddLessonItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
