package controller.instructor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.DTO.revenue.BestSellerCourseDTO;
import model.DTO.revenue.DailyRevenueDTO;
import model.DTO.revenue.MonthlyRevenueDTO;
import model.DTO.revenue.TopBuyerDTO;
import model.DTO.revenue.TotalRevenueDTO;
import model.DTO.revenue.WeeklyStatsDTO;
import model.user.Instructor;
import model.user.User;
import service.course.CourseService;
import service.course.CourseServiceImpl;
import service.payment.IPaymentService;
import service.payment.PaymentServiceImpl;
import service.revenue.RevenueService;
import service.user.IUserService;
import service.user.UserServiceImpl;

public class InstructorDashboardServlet extends HttpServlet {

    private final RevenueService revenueService = new RevenueService();
    private final IPaymentService paymentService = new PaymentServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();
    private final IUserService userService = new UserServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (!"Instructor".equalsIgnoreCase(user.getRole())) {
            request.setAttribute("error", "Only instructors can be in this page.");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }

        Instructor instructor = user.getInstructor();
        if (instructor == null) {
            request.setAttribute("error", "Instructor information is missing.");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }

            // Gọi service
            TotalRevenueDTO totalRevenue = revenueService.getTotalRevenue(instructor.getId());
            List<MonthlyRevenueDTO> monthlyRevenue = revenueService.getMonthlyRevenue(instructor.getId());
            List<BestSellerCourseDTO> bestSellers = revenueService.getBestSellers(instructor.getId());
            List<TopBuyerDTO> topBuyers = revenueService.getTopBuyers(instructor.getId());
            WeeklyStatsDTO weeklyStats = revenueService.getWeeklyStats(instructor.getId());
            List<DailyRevenueDTO> dailyRevenue = revenueService.getDailyRevenue(instructor.getId());
            int totalLearners = paymentService.getTotalLearnersByInstructor(instructor.getId());
            int activeCourses = courseService.countCoursesOfInstructor(instructor.getId(), "PUBLIC", "");
            int totalCourses = courseService.countCoursesOfInstructor(instructor.getId(), "all", "");
            double avgRate = userService.getAverageRateByInstructor(instructor.getId());
            // Gửi dữ liệu lên JSP
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("monthlyRevenue", monthlyRevenue);
            request.setAttribute("bestSellers", bestSellers);
            request.setAttribute("topBuyers", topBuyers);
            request.setAttribute("weeklyStats", weeklyStats);
            request.setAttribute("dailyRevenue", dailyRevenue);
            request.setAttribute("totalLearners", totalLearners);
            request.setAttribute("activeCourses", activeCourses);
            request.setAttribute("totalCourses", totalCourses);
            request.setAttribute("avgRate", avgRate);
            request.getRequestDispatcher("homeInstructor/homeInstructor.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("ERROR: " + e.getMessage());
//            request.setAttribute("error", "Something went wrong while loading dashboard.");
//            request.getRequestDispatcher("/login").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
}