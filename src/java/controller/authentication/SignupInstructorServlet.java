/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.user.Instructor;
import model.user.User;
import service.user.IUserService;
import service.user.UserServiceImpl;
import util.CloudinaryUtil;

/**
 *
 * @author DELL
 */
public class SignupInstructorServlet extends HttpServlet {

    private IUserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("instructorSignup/instructorSignup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String email = request.getParameter("email").trim();
            String fullname = request.getParameter("fullname").trim();
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");

            if (!password.equals(confirm)) {
                throw new IllegalArgumentException("Passwords do not match");
            }

            User user = new User(fullname, email, password, "instructor");

            String bio = request.getParameter("bio").trim();
            int experience = Integer.parseInt(request.getParameter("experience"));
            String specialization = request.getParameter("specialization").trim();
            String education = request.getParameter("education_level");
            String linkedin = request.getParameter("linkedin");
            Part filePart = request.getPart("avatarFile");
            String avatarUrl = null;
            if (filePart != null && filePart.getSize() > 0) {
                CloudinaryUtil cloudinary = new CloudinaryUtil();
                avatarUrl = cloudinary.upload(filePart);
            }
            Instructor instructor = new Instructor(bio, experience, specialization, education, linkedin, avatarUrl);

            // Gọi service xử lý
            boolean success = userService.signupForInstructor(user, instructor);

            if (success) {
                request.setAttribute("successMessage", "Sign up successfully. Please log in.");
                request.getRequestDispatcher("login/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Email already exists");
                request.setAttribute("user", user);
                request.setAttribute("instructor", instructor);
                request.getRequestDispatcher("instructorSignup/instructorSignup.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error occurs during sign up");
            request.getRequestDispatcher("instructorSignup/instructorSignup.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
