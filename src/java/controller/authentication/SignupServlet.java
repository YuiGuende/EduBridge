/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.user.Learner;
import model.user.User;
import service.user.IUserService;
import service.user.UserServiceImpl;
import util.AuthenticationUtil;

/**
 *
 * @author DELL
 */
public class SignupServlet extends HttpServlet {

    private IUserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("signup/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email").trim();
        String fullname = request.getParameter("fullname").trim();
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");

        boolean hasError = false;

        User user = new User(fullname, email, password, "learner");
        request.setAttribute("user", user);

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

        if (userService.isEmailExists(email)) {
            request.setAttribute("error", "Email already exists");
            hasError = true;
        }
        if (hasError) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("signup/signup.jsp").forward(request, response);
            return;
        }
        try {
            Learner learner = new Learner();
            userService.signup(user, learner);
            request.setAttribute("successMessage", "Sign up successfully. Please log in.");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            request.setAttribute("error", "Sign up failed");
            request.setAttribute("user", user);
            request.getRequestDispatcher("signup/signup.jsp").forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
