/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.revenue;

import dao.BaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;
import model.DTO.revenue.BestSellerCourseDTO;
import model.DTO.revenue.MonthlyRevenueDTO;
import model.DTO.revenue.TopBuyerDTO;
import model.DTO.revenue.TotalRevenueDTO;

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
        try (EntityManager em = getEntityManager();) {
            String jpql = """
            SELECT new model.revenue.TotalRevenueDTO(
                SUM(CASE WHEN o.status = 'PAID' THEN od.price ELSE 0 END),
                SUM(CASE WHEN o.status = 'PAID' AND MONTH(o.date) = MONTH(CURRENT_DATE) THEN od.price ELSE 0 END),
                SUM(CASE WHEN o.status = 'PAID' AND YEAR(o.date) = YEAR(CURRENT_DATE) THEN od.price ELSE 0 END)
            )
            FROM Order o JOIN o.orderDetails od JOIN od.course c
            WHERE c.instructor.id = :iid
        """;

            TypedQuery<TotalRevenueDTO> query = em.createQuery(jpql, TotalRevenueDTO.class);
            query.setParameter("iid", instructorId);
            return query.getSingleResult();
        }
    }

    @Override
    public List<BestSellerCourseDTO> getBestSellers(Long instructorId) {
        try (EntityManager em = getEntityManager();) {
            String jpql = """
            SELECT new model.revenue.BestSellerCourseDTO(c.name, COUNT(e))
            FROM Enrollment e JOIN e.course c
            WHERE c.instructor.id = :iid
            GROUP BY c.name ORDER BY COUNT(e) DESC
        """;
            TypedQuery<BestSellerCourseDTO> query = em.createQuery(jpql, BestSellerCourseDTO.class);
            query.setParameter("iid", instructorId);
            query.setMaxResults(5);
            return query.getResultList();
        }

    }

    @Override
    public List<MonthlyRevenueDTO> getMonthlyRevenue(Long instructorId) {
        try (EntityManager em = getEntityManager();) {
            String sql = """
            SELECT MONTH(o.date), SUM(od.price)
            FROM [Order] o JOIN OrderDetail od ON o.id = od.order_id
            JOIN Course c ON od.course_id = c.id
            WHERE c.instructor_id = :iid AND o.status = 'PAID'
            GROUP BY MONTH(o.date)
            ORDER BY MONTH(o.date)
        """;

            Query query = em.createNativeQuery(sql);
            query.setParameter("iid", instructorId);

            @SuppressWarnings("unchecked")
            List<Object[]> rows = query.getResultList();
            return rows.stream()
                    .map(row -> new MonthlyRevenueDTO("Tháng " + row[0], ((Number) row[1]).doubleValue()))
                    .toList();
        }

    }

    @Override
    public List<TopBuyerDTO> getTopBuyers(Long instructorId) {
        try (EntityManager em = getEntityManager();) {
            String sql = """
            SELECT u.fullname, u.email, SUM(od.price) AS totalSpent
            FROM [Order] o
            JOIN Users u ON o.user_id = u.id
            JOIN OrderDetail od ON o.id = od.order_id
            JOIN Course c ON od.course_id = c.id
            WHERE c.instructor_id = :iid AND o.status = 'PAID'
            GROUP BY u.fullname, u.email
            ORDER BY totalSpent DESC
        """;

            Query query = em.createNativeQuery(sql);
            query.setParameter("iid", instructorId);

            @SuppressWarnings("unchecked")
            List<Object[]> rows = query.getResultList();
            return rows.stream()
                    .map(row -> new TopBuyerDTO(
                    row[0] != null ? (String) row[0] : "",
                    row[1] != null ? (String) row[1] : "",
                    row[2] != null ? ((Number) row[2]).doubleValue() : 0.0
            ))
                    .toList();
        }
    }

}
