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

    @Override
    public void updatePaymentStatus(Long paymentId, Payment.PaymentStatus status) {
        Payment payment = paymentDAO.findById(paymentId);
        if (payment != null) {
            payment.setPaymentStatus(status);
            paymentDAO.update(payment); // Use update method from DAO 
        }

    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentDAO.findAll();
    }

    @Override
    public void deletePayment(Long id) {
        paymentDAO.deleteById(id);
    }

    @Override
    public List<Payment> getPaymentsWithPagination(int page, int size) {
        return paymentDAO.findWithPagination(page, size);
    }

    @Override
    public long getTotalPayments() {
        return paymentDAO.count();
    }

    @Override
    public List<Payment> getPaymentsByStatus(Payment.PaymentStatus status) {
        return paymentDAO.findPaymentsByStatus(status);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDAO.findById(id);
    }

}
