/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication.socialLogin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import model.user.Learner;
import model.user.User;
import service.user.IUserService;
import service.user.UserServiceImpl;

/**
 *
 * @author DELL
 */
public class GoogleLoginServlet extends HttpServlet {

    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String REDIRECT_URI = "http://localhost:9999/EduBridge/login-google";
    private IUserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.userService = new UserServiceImpl();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GoogleLoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GoogleLoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        if (code == null || code.isEmpty()) {
            response.sendRedirect("login");
            return;
        }

        // 1. Lấy access token
        String tokenURL = "https://oauth2.googleapis.com/token";
        String data = "code=" + code
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + REDIRECT_URI
                + "&grant_type=authorization_code";

        HttpURLConnection conn = (HttpURLConnection) new URL(tokenURL).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write(data.getBytes());

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) {
            sb.append(sc.nextLine());
        }

        String accessToken = JsonParser.parseString(sb.toString())
                .getAsJsonObject().get("access_token").getAsString();

        // 2. Lấy user info từ Google
        String userInfoURL = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;
        HttpURLConnection userConn = (HttpURLConnection) new URL(userInfoURL).openConnection();
        Scanner userSc = new Scanner(userConn.getInputStream());
        StringBuilder userJson = new StringBuilder();
        while (userSc.hasNext()) {
            userJson.append(userSc.nextLine());
        }

        JsonObject userObj = JsonParser.parseString(userJson.toString()).getAsJsonObject();
        String email = userObj.get("email").getAsString();
        String name = userObj.get("name").getAsString();

        HttpSession session = request.getSession();
        User existingUser = userService.findUserByEmail(email);

        switch (state) {
            case "login":
                if (existingUser != null) {
                    session.setAttribute("user", existingUser);
                    forwardByRole(existingUser.getRole(), request, response);
                } else {
                    request.setAttribute("error", "Account not found. Please sign up.");
                    request.getRequestDispatcher("login/login.jsp").forward(request, response);
                }
                break;

            case "signup_learner":
                if (existingUser == null) {
                    User user = new User(name, email, "", "learner");
                    Learner learner = new Learner();
                    userService.signup(user, learner);
                    session.setAttribute("user", user);
                    request.getRequestDispatcher("home/learner.jsp").forward(request, response);
                } else if ("learner".equals(existingUser.getRole())) {
                    session.setAttribute("user", existingUser);
                    forwardByRole(existingUser.getRole(), request, response);
                } else {
                    request.setAttribute("error", "This email is already registered as " + existingUser.getRole() + ".");
                    request.getRequestDispatcher("login/login.jsp").forward(request, response);
                }
                break;

            case "signup_instructor":
                if (existingUser == null) {
                    User instructor = new User(name, email, "", "instructor");
                    session.setAttribute("tempUser", instructor);
                    request.setAttribute("successMessage", "Please fill in the form to complete instructor registration.");
                    request.getRequestDispatcher("instructorSignup/instructorInforForm.jsp").forward(request, response);
                } else if ("instructor".equals(existingUser.getRole())) {
                    session.setAttribute("user", existingUser);
                    forwardByRole(existingUser.getRole(), request, response);
                } else {
                    request.setAttribute("error", "This email is already registered as " + existingUser.getRole() + ".");
                    request.getRequestDispatcher("login/login.jsp").forward(request, response);
                }
                break;

            default:
                request.setAttribute("error", "Invalid login state.");
                request.getRequestDispatcher("login/login.jsp").forward(request, response);
                break;
        }
    }

    private void forwardByRole(String role, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (role) {
            case "learner":
                request.getRequestDispatcher("home/learner.jsp").forward(request, response);
                break;
            case "instructor":
                request.getRequestDispatcher("home/instructor.jsp").forward(request, response);
                break;
            case "admin":
                request.getRequestDispatcher("home/admin.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect("login");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
