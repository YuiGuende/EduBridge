/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication.socialLogin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
public class FacebookLoginServlet extends HttpServlet {

    private static final String CLIENT_ID = "YOUR_APP_ID";
    private static final String CLIENT_SECRET = "YOUR_APP_SECRET";
    private static final String REDIRECT_URI = "http://localhost:9999/EduBridge/login-facebook";

    private IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendRedirect("login");
            return;
        }

        String tokenUrl = "https://graph.facebook.com/v12.0/oauth/access_token"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&client_secret=" + CLIENT_SECRET
                + "&code=" + code;

        // Step 1: Get access token
        HttpURLConnection tokenConn = (HttpURLConnection) new URL(tokenUrl).openConnection();
        StringBuilder tokenResponse;
        try (Scanner tokenScanner = new Scanner(tokenConn.getInputStream())) {
            tokenResponse = new StringBuilder();
            while (tokenScanner.hasNext()) {
                tokenResponse.append(tokenScanner.nextLine());
            }
        }

        String accessToken = JsonParser.parseString(tokenResponse.toString())
                .getAsJsonObject().get("access_token").getAsString();

        // Step 2: Get user info
        String infoUrl = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;
        HttpURLConnection infoConn = (HttpURLConnection) new URL(infoUrl).openConnection();
        StringBuilder infoJson;
        try (Scanner infoScanner = new Scanner(infoConn.getInputStream())) {
            infoJson = new StringBuilder();
            while (infoScanner.hasNext()) {
                infoJson.append(infoScanner.nextLine());
            }
        }

        JsonObject userObj = JsonParser.parseString(infoJson.toString()).getAsJsonObject();
        String email = userObj.has("email") ? userObj.get("email").getAsString() : null;
        String name = userObj.get("name").getAsString();
        String state = request.getParameter("state");

        if (email == null) {
            request.setAttribute("error", "Facebook account does not provide email.");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }

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
                    session.setAttribute("user", learner);
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
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
