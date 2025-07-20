package service.revenue;

import DAO.revenue.IRevenueDAO;
import DAO.revenue.RevenueDAOImpl;
import java.util.List;
import model.DTO.revenue.BestSellerCourseDTO;
import model.DTO.revenue.MonthlyRevenueDTO;
import model.DTO.revenue.TopBuyerDTO;
import model.DTO.revenue.TotalRevenueDTO;
import model.DTO.revenue.DailyRevenueDTO;
import model.DTO.revenue.WeeklyStatsDTO;

public class RevenueService implements IRevenueService{

    private final IRevenueDAO revenueDAO;

    public RevenueService() {
        this.revenueDAO = new RevenueDAOImpl();
    }

    @Override
    public TotalRevenueDTO getTotalRevenue(Long instructorId) {
        return revenueDAO.getTotalRevenue(instructorId);
    }

    @Override
    public List<BestSellerCourseDTO> getBestSellers(Long instructorId) {
        return revenueDAO.getBestSellers(instructorId);
    }

    @Override
    public List<MonthlyRevenueDTO> getMonthlyRevenue(Long instructorId) {
        return revenueDAO.getMonthlyRevenue(instructorId);
    }

    @Override
    public List<TopBuyerDTO> getTopBuyers(Long instructorId) {
        return revenueDAO.getTopBuyers(instructorId);
    }

    @Override
    public WeeklyStatsDTO getWeeklyStats(Long instructorId) {
        return revenueDAO.getWeeklyStats(instructorId);
    }

    @Override
    public List<DailyRevenueDTO> getDailyRevenue(Long instructorId) {
        return revenueDAO.getDailyRevenue(instructorId);
    }
    public static void main(String[] args) {
        IRevenueDAO dao = new RevenueDAOImpl();
        System.out.println("Total Revenue: " + dao.getTotalRevenue(Long.valueOf("2")));
        System.out.println("Best seller: " + dao.getBestSellers(Long.valueOf("2")));
        System.out.println("MonthlyRe: " + dao.getMonthlyRevenue(Long.valueOf("2")));
        System.out.println("Top Buyers: " + dao.getTopBuyers(Long.valueOf("2")));
        System.out.println("Weekly stats: " + dao.getWeeklyStats(Long.valueOf("2")));
        System.out.println("Weekly re: " + dao.getDailyRevenue(Long.valueOf("2")));
    }
}
