package controller.instructor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.DTO.revenue.BestSellerCourseDTO;
import model.DTO.revenue.MonthlyRevenueDTO;
import model.DTO.revenue.TopBuyerDTO;
import model.DTO.revenue.TotalRevenueDTO;
import service.revenue.RevenueService;

public class InstructorDashboardServlet extends HttpServlet {

    private final RevenueService revenueService = new RevenueService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Giả sử instructorId đã lưu trong session
            Long instructorId = (Long) request.getSession().getAttribute("instructorId");
            if (instructorId == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Gọi service
            TotalRevenueDTO totalRevenue = revenueService.getTotalRevenue(Long.valueOf("1"));
            List<MonthlyRevenueDTO> monthlyRevenue = revenueService.getMonthlyRevenue(Long.valueOf("1"));
            List<BestSellerCourseDTO> bestSellers = revenueService.getBestSellers(Long.valueOf("1"));
            List<TopBuyerDTO> topBuyers = revenueService.getTopBuyers(Long.valueOf("1"));

            // Gửi dữ liệu lên JSP
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("monthlyRevenue", monthlyRevenue);
            request.setAttribute("bestSellers", bestSellers);
            request.setAttribute("topBuyers", topBuyers);

            request.getRequestDispatcher("homeInstructor/homeInstructor.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Something went wrong while loading dashboard.");
            request.getRequestDispatcher(request.getContextPath() +"/login/login.jsp").forward(request, response);
        }
    }
}