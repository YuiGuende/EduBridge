package DAO.payment;


import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.payment.Payment;

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
}
