package service.revenue;

import dao.revenue.IRevenueDAO;
import dao.revenue.RevenueDAOImpl;
import java.util.List;
import model.DTO.revenue.BestSellerCourseDTO;
import model.DTO.revenue.MonthlyRevenueDTO;
import model.DTO.revenue.TopBuyerDTO;
import model.DTO.revenue.TotalRevenueDTO;

public class RevenueService {

    private final IRevenueDAO revenueDAO;

    public RevenueService() {
        this.revenueDAO = new RevenueDAOImpl();
    }

    public TotalRevenueDTO getTotalRevenue(Long instructorId) {
        return revenueDAO.getTotalRevenue(instructorId);
    }

    public List<BestSellerCourseDTO> getBestSellers(Long instructorId) {
        return revenueDAO.getBestSellers(instructorId);
    }

    public List<MonthlyRevenueDTO> getMonthlyRevenue(Long instructorId) {
        return revenueDAO.getMonthlyRevenue(instructorId);
    }

    public List<TopBuyerDTO> getTopBuyers(Long instructorId) {
        return revenueDAO.getTopBuyers(instructorId);
    }
}
