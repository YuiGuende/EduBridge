/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.revenue;

import DAO.BaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.DTO.revenue.BestSellerCourseDTO;
import model.DTO.revenue.MonthlyRevenueDTO;
import model.DTO.revenue.TopBuyerDTO;
import model.DTO.revenue.TotalRevenueDTO;
import model.DTO.revenue.DailyRevenueDTO;
import model.DTO.revenue.WeeklyStatsDTO;
import model.payment.Payment;

/**
 *
 * @author DELL
 */
public class RevenueDAOImpl extends BaseDAO implements IRevenueDAO {

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public TotalRevenueDTO getTotalRevenue(Long instructorId) {
        try (EntityManager em = getEntityManager()) {
            Double total = em.createQuery("""
            SELECT SUM(pd.amount)
            FROM Payment p JOIN p.paymentDetails pd JOIN pd.course c
            WHERE p.paymentStatus = :status AND c.createdBy.id = :iid
            """, Double.class)
                    .setParameter("status", Payment.PaymentStatus.CONFIRMED)
                    .setParameter("iid", instructorId)
                    .getSingleResult();
            Double monthly = em.createQuery("""
            SELECT SUM(pd.amount)
            FROM Payment p JOIN p.paymentDetails pd JOIN pd.course c
            WHERE p.paymentStatus = :status
              AND FUNCTION('MONTH', p.paymentDate) = FUNCTION('MONTH', CURRENT_DATE)
              AND c.createdBy.id = :iid
            """, Double.class)
                    .setParameter("status", Payment.PaymentStatus.CONFIRMED)
                    .setParameter("iid", instructorId)
                    .getSingleResult();

            Double yearly = em.createQuery("""
            SELECT SUM(pd.amount)
            FROM Payment p JOIN p.paymentDetails pd JOIN pd.course c
            WHERE p.paymentStatus = :status
              AND FUNCTION('YEAR', p.paymentDate) = FUNCTION('YEAR', CURRENT_DATE)
              AND c.createdBy.id = :iid
            """, Double.class)
                    .setParameter("status", Payment.PaymentStatus.CONFIRMED)
                    .setParameter("iid", instructorId)
                    .getSingleResult();
            return new TotalRevenueDTO(
                    total != null ? total : 0,
                    monthly != null ? monthly : 0,
                    yearly != null ? yearly : 0
            );
        }
    }

    @Override
    public List<BestSellerCourseDTO> getBestSellers(Long instructorId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = """
                SELECT new model.DTO.revenue.BestSellerCourseDTO(
                    c.title, COUNT(pd.id), SUM(pd.amount)
                )
                FROM PaymentDetail pd
                JOIN pd.course c
                JOIN pd.payment p
                WHERE c.createdBy.id = :iid
                AND p.paymentStatus = :status
                GROUP BY c.title
                ORDER BY COUNT(pd.id) DESC
            """;
            TypedQuery<BestSellerCourseDTO> query = em.createQuery(jpql, BestSellerCourseDTO.class);
            query.setParameter("iid", instructorId);
            query.setParameter("status", Payment.PaymentStatus.CONFIRMED);
            query.setMaxResults(5);
            return query.getResultList();
        }
    }

    @Override
    public List<MonthlyRevenueDTO> getMonthlyRevenue(Long instructorId) {
        try (EntityManager em = getEntityManager()) {
            String sql = """
                SELECT MONTH(p.PAYMENTDATE), COALESCE(SUM(pd.AMOUNT), 0)
                FROM PAYMENT p
                JOIN PAYMENTDETAIL pd ON p.ID = pd.PAYMENT_ID
                JOIN courses c ON pd.COURSE_ID = c.ID
                WHERE c.created_by = ? AND p.PAYMENTSTATUS = ?
                GROUP BY MONTH(p.PAYMENTDATE)
                ORDER BY MONTH(p.PAYMENTDATE)
            """;

            Query query = em.createNativeQuery(sql);
            query.setParameter(1, instructorId);
            query.setParameter(2, "CONFIRMED");

            @SuppressWarnings("unchecked")
            List<Object[]> rows = query.getResultList();
            return rows.stream()
                    .map(row -> new MonthlyRevenueDTO(
                    "Month " + row[0],
                    ((Number) row[1]).doubleValue()
            ))
                    .toList();
        }
    }

    @Override
    public List<TopBuyerDTO> getTopBuyers(Long instructorId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = """
                SELECT p.user.fullname, p.user.email, COALESCE(SUM(pd.amount), 0)
                FROM Payment p JOIN p.paymentDetails pd
                JOIN pd.course c
                WHERE c.createdBy.id = :iid AND p.paymentStatus = :status
                GROUP BY p.user.fullname, p.user.email
                ORDER BY SUM(pd.amount) DESC
            """;
            Query query = em.createQuery(jpql);
            query.setParameter("status", Payment.PaymentStatus.CONFIRMED);
            query.setParameter("iid", instructorId);

            @SuppressWarnings("unchecked")
            List<Object[]> rows = query.getResultList();
            return rows.stream()
                    .map(row -> new TopBuyerDTO(
                    (String) row[0],
                    (String) row[1],
                    ((Number) row[2]).doubleValue()
            ))
                    .toList();
        }
    }

    @Override
    public WeeklyStatsDTO getWeeklyStats(Long instructorId) {
        try (EntityManager em = getEntityManager()) {

            // 1. New Students trong tuần này
            String newStudentsSQL = """
            SELECT COUNT(DISTINCT u.id)
            FROM courses c
            JOIN REPORTTARGET rt ON rt.ID = c.id AND rt.DTYPE = 'Course'
            JOIN PAYMENTDETAIL pd ON pd.COURSE_ID = rt.ID
            JOIN PAYMENT p ON p.ID = pd.PAYMENT_ID
            JOIN [user] u ON u.id = p.user_id
            WHERE c.created_by = ?
            AND p.PAYMENTSTATUS = 'CONFIRMED'
            AND p.PAYMENTDATE >= DATEADD(DAY, 1 - DATEPART(WEEKDAY, GETDATE()), CAST(GETDATE() AS DATE))
            AND p.PAYMENTDATE <  DATEADD(DAY, 8 - DATEPART(WEEKDAY, GETDATE()), CAST(GETDATE() AS DATE))
            AND DATEPART(YEAR, p.PAYMENTDATE) = DATEPART(YEAR, GETDATE())
        """;
            Query q1 = em.createNativeQuery(newStudentsSQL);
            q1.setParameter(1, instructorId);
            Number newStudents = (Number) q1.getSingleResult();

            // 2. Course Sales trong tuần này
            String courseSalesSQL = """
            SELECT COUNT(pd.ID)
            FROM PAYMENTDETAIL pd
            JOIN PAYMENT p ON pd.PAYMENT_ID = p.ID
            JOIN COURSES c ON pd.COURSE_ID = c.ID
            WHERE c.created_by = ?
              AND p.PAYMENTSTATUS = 'CONFIRMED'
              AND p.PAYMENTDATE >= DATEADD(DAY, 1 - DATEPART(WEEKDAY, GETDATE()), CAST(GETDATE() AS DATE))
              AND p.PAYMENTDATE <  DATEADD(DAY, 8 - DATEPART(WEEKDAY, GETDATE()), CAST(GETDATE() AS DATE))
              AND DATEPART(YEAR, p.PAYMENTDATE) = DATEPART(YEAR, GETDATE())
        """;
            Query q2 = em.createNativeQuery(courseSalesSQL);
            q2.setParameter(1, instructorId);
            Number courseSales = (Number) q2.getSingleResult();

            return new WeeklyStatsDTO(
                    newStudents != null ? newStudents.intValue() : 0,
                    courseSales != null ? courseSales.intValue() : 0
            );
        }
    }

    @Override
    public List<DailyRevenueDTO> getDailyRevenue(Long instructorId) {
        try (EntityManager em = getEntityManager()) {
            String sql = """
                SELECT 
                    LEFT(DATENAME(WEEKDAY, p.PAYMENTDATE), 3) AS DayName,
                    COALESCE(SUM(pd.AMOUNT), 0) AS TotalRevenue
                FROM PAYMENT p
                JOIN PAYMENTDETAIL pd ON p.ID = pd.PAYMENT_ID
                JOIN COURSES c ON pd.COURSE_ID = c.ID
                WHERE 
                    c.CREATED_BY = ? 
                    AND p.PAYMENTSTATUS = 'CONFIRMED'
                    AND DATEPART(WEEK, p.PAYMENTDATE) = DATEPART(WEEK, GETDATE())
                    AND DATEPART(YEAR, p.PAYMENTDATE) = DATEPART(YEAR, GETDATE())
                GROUP BY DATENAME(WEEKDAY, p.PAYMENTDATE)
            """;
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, instructorId);

            @SuppressWarnings("unchecked")
            List<Object[]> rows = query.getResultList();
            Map<String, Double> revenueMap = new HashMap<>();
            for (Object[] row : rows) {
                String day = row[0].toString();
                double amount = ((Number) row[1]).doubleValue();
                revenueMap.put(day, amount);
            }
            List<String> days = List.of("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
            List<DailyRevenueDTO> fullWeekRevenue = new ArrayList<>();
            for (String day : days) {
                double amount = revenueMap.getOrDefault(day, 0.0);
                fullWeekRevenue.add(new DailyRevenueDTO(day, amount));
            }
            return fullWeekRevenue;
        }

    }

}
