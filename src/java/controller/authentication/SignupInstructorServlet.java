/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;
import model.user.Instructor;
import model.user.InstructorBankInfo;
import model.user.User;
import service.user.IUserService;
import service.user.UserServiceImpl;
import util.AuthenticationUtil;
import util.CloudinaryUtil;

/**
 *
 * @author DELL
 */
@MultipartConfig
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
        String step = request.getParameter("step");
        switch (step) {
            case "1":
                signupStepOne(request, response);
                break;
            case "2":
                signupStepTwo(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid step.");
                break;
        }

    }

    private void signupStepOne(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email").trim();
        String fullname = request.getParameter("fullname").trim();
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");

        boolean hasError = false;
        if (!AuthenticationUtil.isValidEmail(email)) {
            request.setAttribute("emailError", "Invalid email format.");
            hasError = true;
        }
        if (!AuthenticationUtil.isValidFullName(fullname)) {
            request.setAttribute("fullnameError", "Full name must be 3-50 characters.");
            hasError = true;
        }
        if (!AuthenticationUtil.isValidPassword(password)) {
            request.setAttribute("passwordError", "Password must be at least 6 characters.");
            hasError = true;
        }
        if (!AuthenticationUtil.isPasswordConfirmed(password, confirm)) {
            request.setAttribute("confirmError", "Passwords do not match.");
            hasError = true;
        }

        User user = new User(fullname, email, password, "instructor");
        if (userService.isEmailExists(email)) {
            request.setAttribute("error", "Email already exists");
            hasError = true;
        }
        if (!hasError) {
            HttpSession session = request.getSession();
            session.setAttribute("tempUser", user);
            request.setAttribute("successMessage", "Please fill in this form to complete your registration");
            request.getRequestDispatcher("instructorSignup/instructorInforForm.jsp").forward(request, response);
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("instructorSignup/instructorSignup.jsp").forward(request, response);
    }

    private void signupStepTwo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("tempUser");

        if (user == null) {
            response.sendRedirect("signup-instructor");
            return;
        }
        User userSaved = userService.save(user);
        String bio = request.getParameter("bio");
        int experience;
        try {
            experience = Integer.parseInt(request.getParameter("experience"));
        } catch (NumberFormatException e) {
            experience = 0;
        }
        String specialization = request.getParameter("specialization");
        String education = request.getParameter("education_level");
        String linkedin = request.getParameter("linkedin");
        Part filePart = request.getPart("avatarFile");
        String avatarUrl = null;
        if (filePart != null && filePart.getSize() > 0) {
            CloudinaryUtil cloudinary = new CloudinaryUtil();
            avatarUrl = cloudinary.upload(filePart);
        }

        String accountName = request.getParameter("accountName");
        String accountNumber = request.getParameter("accountNumber");
        String bankName = request.getParameter("bankName");
        Instructor instructor = new Instructor(userSaved.getId(), bio, experience, specialization, education, linkedin, avatarUrl);
        InstructorBankInfo bankInfo = new InstructorBankInfo(accountName, accountNumber, bankName);

        try {
            userService.signupForInstructor(user, instructor, bankInfo);
            session.removeAttribute("tempUser");
            request.setAttribute("successMessage", "Signup successful. Please log in.");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        } catch (Exception e) {
//            request.setAttribute("error", "Sign up failed.");
//            request.setAttribute("instructor", instructor);
//            request.getRequestDispatcher("instructorSignup/instructorInforForm.jsp").forward(request, response);
            response.getWriter().println("ERROR: " + e.getMessage());

            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
