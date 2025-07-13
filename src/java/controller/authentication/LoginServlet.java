/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import model.user.User;
import service.user.IUserService;
import service.user.UserServiceImpl;

public class LoginServlet extends HttpServlet {

    private IUserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = new User();
        boolean rememberMeChecked = false;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    user.setEmail(cookie.getValue());
                }
                if ("password".equals(cookie.getName())) {
                    try {
                        user.setPassword(cookie.getValue());
                        rememberMeChecked = true;

                    } catch (IllegalArgumentException e) {
                        user.setPassword("");
                    }
                }
            }
        }

        request.setAttribute("user", user);
        request.setAttribute("rememberMeChecked", rememberMeChecked);

        request.getRequestDispatcher("login/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();
            String remember = request.getParameter("remember");
            System.out.println("email" + email);
            Optional<User> userLogin = userService.login(email, password);
            System.out.println("user" + userLogin.get().getFullname());
            if (userLogin.isPresent()) {
                User u = userLogin.get();

                HttpSession session = request.getSession();
                session.setAttribute("user", u);

                String role = u.getRole();

                Cookie cEmail = new Cookie("email", email);
                Cookie cRole = new Cookie("role", role);
                cEmail.setMaxAge(60 * 60 * 24 * 7);
                cRole.setMaxAge(60 * 60 * 24 * 7);
                cRole.setPath("/");
                cEmail.setPath("/");
                response.addCookie(cEmail);
                response.addCookie(cRole);

                if ("true".equals(remember)) {
                    Cookie cPass = new Cookie("password", password);
                    cPass.setMaxAge(60 * 60 * 24 * 7);
                    cPass.setPath("/");
                    response.addCookie(cPass);
                } else {
                    Cookie cPass = new Cookie("password", "");
                    cPass.setMaxAge(0);
                    response.addCookie(cPass);
                }

                switch (role) {
                    case "learner":
                        request.getRequestDispatcher("home/learner.jsp").forward(request, response);
                        break;
                    case "instructor":
                        request.getRequestDispatcher("home/instructor.jsp").forward(request, response);
                        break;
                    default:
                        request.getRequestDispatcher("home/admin.jsp").forward(request, response);
                        break;
                }
            } else {
                User user = new User();
                user.setEmail(email);
                request.setAttribute("user", user);
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("login/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurs during login");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
