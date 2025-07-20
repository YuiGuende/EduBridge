/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service.revenue;

import java.util.List;
import model.DTO.revenue.BestSellerCourseDTO;
import model.DTO.revenue.DailyRevenueDTO;
import model.DTO.revenue.MonthlyRevenueDTO;
import model.DTO.revenue.TopBuyerDTO;
import model.DTO.revenue.TotalRevenueDTO;
import model.DTO.revenue.WeeklyStatsDTO;

/**
 *
 * @author DELL
 */
public interface IRevenueService {
    TotalRevenueDTO getTotalRevenue(Long instructorId);
    List<BestSellerCourseDTO> getBestSellers(Long instructorId);
    List<MonthlyRevenueDTO> getMonthlyRevenue(Long instructorId);
    List<TopBuyerDTO> getTopBuyers(Long instructorId);
    WeeklyStatsDTO getWeeklyStats(Long instructorId);
    List<DailyRevenueDTO> getDailyRevenue(Long instructorId);
}
