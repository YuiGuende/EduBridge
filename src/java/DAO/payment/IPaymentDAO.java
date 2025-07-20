package DAO.payment;

import java.util.List;
import model.payment.Payment;

public interface IPaymentDAO {

    Payment findById(Long id);

    Payment save(Payment payment);

    long count();

    void deleteById(Long id);

    Payment findByVnp(String vnpTxnRef);

    Payment update(Payment payment);

    int getTotalLearnersByInstructor(Long instructorId);

    double getTotalAmountOfCourse(Long courseId);

    List<Payment> findAll();

    List<Payment> findWithPagination(int page, int size);

    List<Payment> getAllPaymentsByUser(Long userId);

    List<Payment> findPaymentsByStatus(Payment.PaymentStatus status); // Added method to find by status

    long countAllPayments(); // Added method to count all payments

    List<Payment> findPaginatedPayments(int offset, int limit); // Added method for paginated payments
}
