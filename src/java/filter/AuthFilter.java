/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import model.user.User;

/**
 *
 * @author DELL
 */
public class AuthFilter implements Filter {

    private static final String LEARNER = "learner";
    private static final String INS = "instructor";
    private static final String AD = "Admin";
    private static final String LOGIN_PAGE = "login";
    private static final String LOGOUT_PAGE = "logout";
    private static final String SINGUP_PAGE = "signup";
    private static final String LOGIN_GOOGLE = "login-google";
    private static final String SINGUP_INS = "signup-instructor";
    private static final String HOME_PAGE = "";
    private static final boolean DEBUG = true;

    private static final Set<String> ADMIN_FUNC = new HashSet<>();
    private static final Set<String> LEARNER_FUNC = new HashSet<>();
    private static final Set<String> INSTRUCTOR_FUNC = new HashSet<>();
    private static final Set<String> GUEST_FUNC = new HashSet<>();
    private static final Set<String> STATIC_RESOURCES = new HashSet<>(Arrays.asList(
            ".css", ".js", ".jpg", ".jpeg", ".png", ".gif", ".woff", ".svg", "webp"
    ));

    private FilterConfig filterConfig = null;

    public AuthFilter() {
        // Các tài nguyên cho admin
        ADMIN_FUNC.add("courses");
        ADMIN_FUNC.add("dashboard");
        ADMIN_FUNC.add("instructors");
        ADMIN_FUNC.add(LOGOUT_PAGE);

        // Các tài nguyên cho learner thông thường
        LEARNER_FUNC.add("home-learner");
        LEARNER_FUNC.add(LOGOUT_PAGE);
        LEARNER_FUNC.add("learn-course");
        LEARNER_FUNC.add("lessons-learner");
        LEARNER_FUNC.add("course-detail");
        LEARNER_FUNC.add("cart");
        LEARNER_FUNC.add("checkout");
        LEARNER_FUNC.add("vnpay-return");
        LEARNER_FUNC.add("report");
        LEARNER_FUNC.add("comment");
        LEARNER_FUNC.add("notification");

        //Các tài nguyên cho instructor
        INSTRUCTOR_FUNC.add(LOGOUT_PAGE);
        INSTRUCTOR_FUNC.add("home-instructor");
        INSTRUCTOR_FUNC.add("total-courses");
        INSTRUCTOR_FUNC.add("view-course");
        INSTRUCTOR_FUNC.add("add-course");
        INSTRUCTOR_FUNC.add("course");
        INSTRUCTOR_FUNC.add("add-lesson");
        INSTRUCTOR_FUNC.add("add-lesson-item");
        INSTRUCTOR_FUNC.add("lesson");
        INSTRUCTOR_FUNC.add("module");
        INSTRUCTOR_FUNC.add("comment");
        INSTRUCTOR_FUNC.add("notification");

        //Các tài nguyên cho guest
        GUEST_FUNC.add("home-learner");
        GUEST_FUNC.add(LOGIN_PAGE);
        GUEST_FUNC.add(SINGUP_PAGE);
        GUEST_FUNC.add("login-google");
        GUEST_FUNC.add("course-detail");
        GUEST_FUNC.add("");
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        if (DEBUG) {
            System.out.println("AuthenFilter initialized.");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String uri = req.getRequestURI();

            // Cho phép truy cập các tài nguyên tĩnh và trang login
            if (isStaticResource(uri) || uri.endsWith(LOGIN_PAGE) || uri.endsWith(SINGUP_PAGE) || uri.endsWith(SINGUP_INS) || uri.endsWith(LOGIN_GOOGLE) || uri.endsWith(LOGOUT_PAGE) || uri.endsWith(HOME_PAGE)) {
                chain.doFilter(request, response);
                return;
            }

            // Lấy tên tài nguyên
            int index = uri.lastIndexOf("/");
            String resource = uri.substring(index + 1);

            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
//                if (GUEST_FUNC.contains(resource)) {
//                    chain.doFilter(request, response);
//                    return;
//                } else {
                    res.sendRedirect(req.getContextPath() + "/login");
                    return;
//                }
            }

            User user = (User) session.getAttribute("user");
            String role = user.getRole();

            // Phân quyền truy cập theo role
            if (LEARNER.equalsIgnoreCase(role) && LEARNER_FUNC.contains(resource)) {
                chain.doFilter(request, response);
            } else if (INS.equalsIgnoreCase(role) && INSTRUCTOR_FUNC.contains(resource)) {
                chain.doFilter(request, response);
            } else if (AD.equalsIgnoreCase(role) && ADMIN_FUNC.contains(resource)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Nên dùng logger thực tế
            ((HttpServletResponse) response).sendRedirect(LOGIN_PAGE);
        }
    }

    @Override
    public void destroy() {
        // Clean up nếu cần
    }

    private boolean isStaticResource(String uri) {
        for (String ext : STATIC_RESOURCES) {
            if (uri.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }


}
