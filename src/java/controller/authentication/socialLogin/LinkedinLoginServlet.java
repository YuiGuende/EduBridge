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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import model.user.Learner;
import model.user.User;
import service.user.IUserService;
import service.user.UserServiceImpl;

/**
 *
 * @author DELL
 */
public class LinkedinLoginServlet extends HttpServlet {

    private static final String CLIENT_ID = "867yqxfh8u77hs";
    private static final String CLIENT_SECRET = "";
    private static final String REDIRECT_URI = "http://localhost:9999/EduBridge/login-linkedin";

    private IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendRedirect("login");
            return;
        }

        // Step 1: Get access token from LinkedIn
        String tokenUrl = "https://www.linkedin.com/oauth/v2/accessToken";
        URL url = new URL(tokenUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String params = "grant_type=authorization_code"
                + "&code=" + URLEncoder.encode(code, "UTF-8")
                + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "UTF-8")
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET;

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes());
        }

        StringBuilder tokenResponse = new StringBuilder();
        try (Scanner scanner = new Scanner(conn.getInputStream())) {
            while (scanner.hasNext()) {
                tokenResponse.append(scanner.nextLine());
            }
        }

        String accessToken = JsonParser.parseString(tokenResponse.toString())
                .getAsJsonObject().get("access_token").getAsString();

        // Step 2: Get user profile
        String profileUrl = "https://api.linkedin.com/v2/me";
        HttpURLConnection profileConn = (HttpURLConnection) new URL(profileUrl).openConnection();
        profileConn.setRequestProperty("Authorization", "Bearer " + accessToken);

        StringBuilder profileJson = new StringBuilder();
        try (Scanner scanner = new Scanner(profileConn.getInputStream())) {
            while (scanner.hasNext()) {
                profileJson.append(scanner.nextLine());
            }
        }

        JsonObject profileObj = JsonParser.parseString(profileJson.toString()).getAsJsonObject();
        String firstName = profileObj.getAsJsonObject("localizedFirstName").getAsString();
        String lastName = profileObj.getAsJsonObject("localizedLastName").getAsString();
        String name = firstName + " " + lastName;

        // Step 3: Get user email
        String emailUrl = "https://api.linkedin.com/v2/emailAddress?q=members&projection=(elements*(handle~))";
        HttpURLConnection emailConn = (HttpURLConnection) new URL(emailUrl).openConnection();
        emailConn.setRequestProperty("Authorization", "Bearer " + accessToken);

        StringBuilder emailJson = new StringBuilder();
        try (Scanner scanner = new Scanner(emailConn.getInputStream())) {
            while (scanner.hasNext()) {
                emailJson.append(scanner.nextLine());
            }
        }

        JsonObject emailObj = JsonParser.parseString(emailJson.toString()).getAsJsonObject();
        String email = emailObj.getAsJsonArray("elements")
                .get(0).getAsJsonObject()
                .getAsJsonObject("handle~")
                .get("emailAddress").getAsString();

        if (email == null) {
            request.setAttribute("error", "LinkedIn account does not provide email.");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }

        String state = request.getParameter("state");
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
}
