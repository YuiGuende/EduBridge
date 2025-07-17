package DAO.payment;


import model.payment.Payment;

public interface IPaymentDAO {
    Payment save(Payment payment);
    Payment findByVnp(String vnpTxnRef);
    Payment update(Payment payment);
    int getTotalLearnersByInstructor(Long instructorId);
    double getTotalAmountOfCourse(Long courseId);
}
