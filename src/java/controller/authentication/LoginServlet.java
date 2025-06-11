/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.login.LoginServiceImpl;

public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet LoginServlet</title>");  
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet LoginServlet at " + request.getContextPath () + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    } 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String savedEmail = "";
        String savedPassword = "";

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    savedEmail = cookie.getValue();
                }
                if ("password".equals(cookie.getName())) {
                    savedPassword = cookie.getValue();
                }
            }
        }

        request.setAttribute("savedEmail", savedEmail);
        request.setAttribute("savedPassword", savedPassword);

        request.getRequestDispatcher("login/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginServiceImpl service = new LoginServiceImpl();
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String remember = request.getParameter("remember");
            User user = service.login(email, password);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            if ("on".equals(remember)) {
                try {
//                    String encodedUsername = URLEncoder.encode(email, "UTF-8");
//                    String encodedPassword = URLEncoder.encode(password, "UTF-8");

                    Cookie cEmail = new Cookie("email", email);
                    Cookie cPass = new Cookie("password", password);

                    cEmail.setMaxAge(60 * 60 * 24 * 7);
                    cPass.setMaxAge(60 * 60 * 24 * 7);

                    response.addCookie(cEmail);
                    response.addCookie(cPass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            switch (user.getRole()) {
                case "learner":
                    request.getRequestDispatcher("signup/signup.jsp").forward(request, response);
                    break;
                case "instructor":
                    request.getRequestDispatcher("home/instructor.jsp").forward(request, response);
                    break;
                default:
                    request.getRequestDispatcher("home/admin.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
