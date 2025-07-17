package service.payment;

import DAO.payment.IPaymentDAO;
import DAO.payment.PaymentDAOImpl;
import model.course.Course;
import model.payment.Payment;

import java.util.List;

public class PaymentServiceImpl implements IPaymentService {

    private final IPaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public Payment savePayment(Payment payment) {
        return paymentDAO.save(payment);
    }

    @Override
    public void updateStatus(String vnpTxnRef, Payment.PaymentStatus status) {
        Payment payment = paymentDAO.findByVnp(vnpTxnRef);
        if (payment != null) {
            payment.setPaymentStatus(status);
            paymentDAO.update(payment);
        }
    }

    @Override
    public List<Payment> getAllPaymentsByUser(Long userId) {
        // TODO: b?n c� th? b? sung logic t�m theo ng??i d�ng n?u c?n
        return null;
    }

    @Override
    public Payment findByVnp(String vnpTxnRef) {
        return paymentDAO.findByVnp(vnpTxnRef);
    }

    @Override
    public Payment update(Payment payment) {
        return paymentDAO.update(payment);
    }

    @Override
    public Payment save(Payment payment) {
        return paymentDAO.save(payment);
    }

    @Override
    public int getTotalLearnersByInstructor(Long instructorId) {
        return paymentDAO.getTotalLearnersByInstructor(instructorId);
    }

}
