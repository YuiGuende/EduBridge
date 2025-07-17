package DAO.payment;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.payment.Payment;
import model.payment.Payment.PaymentStatus;

public class PaymentDAOImpl extends GenericDAO<Payment> implements IPaymentDAO {

    public PaymentDAOImpl() {
        super(Payment.class);
    }

    @Override
    public Payment findByVnp(String vnpTxnRef) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Payment> query = em.createQuery(
                    "SELECT p FROM Payment p WHERE p.vnp = :vnp",
                    Payment.class
            );
            query.setParameter("vnp", vnpTxnRef);
            return query.getResultStream().findFirst().orElse(null);
        }
    }

    @Override
    public int getTotalLearnersByInstructor(Long instructorId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = """
            SELECT COUNT(DISTINCT p.user.id)
            FROM PaymentDetail pd
            JOIN pd.payment p
            JOIN pd.course c
            WHERE c.createdBy.id = :instructorId
            AND p.paymentStatus = :status
        """;
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("instructorId", instructorId);
            query.setParameter("status", PaymentStatus.CONFIRMED);

            Long result = query.getSingleResult();
            return result != null ? result.intValue() : 0;
        }
    }

    @Override
    public double getTotalAmountOfCourse(Long courseId) {
        try (EntityManager em = getEntityManager()) {
            String jpql = """
            SELECT SUM(pd.amount)
            FROM PaymentDetail pd
            WHERE pd.course.id = :courseId
        """;
            TypedQuery<Double> q = em.createQuery(jpql, Double.class);
            q.setParameter("courseId", courseId);
            Double total = q.getSingleResult();
            return total != null ? total : 0.0;
        }
    }

}
