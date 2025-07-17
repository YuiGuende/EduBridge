package service.payment;

import model.course.Course;
import model.payment.Payment;

import java.util.List;

public interface IPaymentService {

    Payment savePayment(Payment payment);

    void updateStatus(String vnpTxnRef, Payment.PaymentStatus status);

    List<Payment> getAllPaymentsByUser(Long userId); // v� d? n?u mu?n hi?n th? l?ch s?

    Payment findByVnp(String vnpTxnRef);

    Payment update(Payment payment);

    Payment save(Payment payment);
    
    int getTotalLearnersByInstructor(Long instructorId);
    
}
