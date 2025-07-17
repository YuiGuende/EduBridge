/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.revenue;

import java.util.List;
import model.DTO.revenue.*;

/**
 *
 * @author DELL
 */
public interface IRevenueDAO {

    TotalRevenueDTO getTotalRevenue(Long instructorId);

    List<BestSellerCourseDTO> getBestSellers(Long instructorId);

    List<MonthlyRevenueDTO> getMonthlyRevenue(Long instructorId);

    List<TopBuyerDTO> getTopBuyers(Long instructorId);

    WeeklyStatsDTO getWeeklyStats(Long instructorId);
    
    List<DailyRevenueDTO> getDailyRevenue(Long instructorId);
}
