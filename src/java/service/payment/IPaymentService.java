package service.payment;

import model.course.Course;
import model.payment.Payment;

import java.util.List;

public interface IPaymentService {

    Payment savePayment(Payment payment);
    
    Payment getPaymentById(Long id);

    void updateStatus(String vnpTxnRef, Payment.PaymentStatus status);

    List<Payment> getAllPaymentsByUser(Long userId); // v� d? n?u mu?n hi?n th? l?ch s?

    Payment findByVnp(String vnpTxnRef);

    Payment update(Payment payment);

    Payment save(Payment payment);

    int getTotalLearnersByInstructor(Long instructorId);

    void updatePaymentStatus(Long paymentId, Payment.PaymentStatus status);

    List<Payment> getAllPayments();

    void deletePayment(Long id);

    List<Payment> getPaymentsWithPagination(int page, int size);

    long getTotalPayments();

    List<Payment> getPaymentsByStatus(Payment.PaymentStatus status); // Added method to get payments by status

}
