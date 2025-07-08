/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Course;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LEGION
 */
@WebServlet(name = "AddLessonItem", urlPatterns = {"/add-lesson-item"})
public class AddLessonItem extends HttpServlet {

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
        switch (type) {
            case "video":
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

    private void addVideo(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void addReading(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void getAddVideoPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("courseMaterial/AddVideo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(AddLessonItem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddLessonItem.class.getName()).log(Level.SEVERE, null, ex);
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
