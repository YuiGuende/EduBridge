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
import model.user.User;
import service.user.IUserService;
import service.user.UserServiceImpl;

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
        try {
            String email = request.getParameter("email").trim();
            String fullname = request.getParameter("fullname").trim();
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");
            if (!password.equals(confirm)) {
                throw new IllegalArgumentException("Passwords do not match");
            }
            User user = new User(fullname, email, password, "learner");
            if (userService.signup(user)) {
                request.setAttribute("successMessage", "Sign up successfully. Please log in.");
                request.getRequestDispatcher("login/login.jsp").forward(request, response);
            } else {
                throw new IllegalArgumentException("Email already exists");
            }
        } catch (ServletException | IOException | IllegalArgumentException e) {
            User user = new User();
            user.setFullname(request.getParameter("fullname"));
            user.setEmail(request.getParameter("email"));
            request.setAttribute("user", user);

            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("signup/signup.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
