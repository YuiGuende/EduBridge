package controller.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.payment.Payment;

import java.io.IOException;
import service.course.CourseLearnerServiceImpl;
import service.course.ICourseLearnerService;
import service.payment.IPaymentService;
import service.payment.PaymentServiceImpl;

@WebServlet("/vnpay-return")
public class VNPayReturnServlet extends HttpServlet {

    private IPaymentService paymentService = new PaymentServiceImpl();
    private ICourseLearnerService courseLearnerService = new CourseLearnerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String vnp_TransactionStatus = req.getParameter("vnp_TransactionStatus");
        String vnp_TxnRef = req.getParameter("vnp_TxnRef");

        Payment payment = paymentService.findByVnp(vnp_TxnRef);

        if (payment == null) {
            resp.getWriter().println("không tìm thấy giao dịch.");
            return;
        }

        if ("00".equals(vnp_TransactionStatus)) {
            payment.setPaymentStatus(Payment.PaymentStatus.CONFIRMED);
            paymentService.update(payment);

            // 🟢 Enroll learner vào các khóa học
            Long learnerId = payment.getUser().getId();
            payment.getPaymentDetails().forEach(detail -> {
                Long courseId = detail.getCourse().getId();
                courseLearnerService.enrollLearnerToCourse(learnerId, courseId);
            });

            resp.getWriter().println("Thanh toán thành công!");
        } else {
            payment.setPaymentStatus(Payment.PaymentStatus.CANCELED);
            resp.getWriter().println("Thanh toán thất bại hoặc bị hủy.");
        }

        paymentService.update(payment);
    }
}
