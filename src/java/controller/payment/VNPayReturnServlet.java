package controller.payment;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.payment.Payment;


import java.io.IOException;
import service.payment.IPaymentService;
import service.payment.PaymentServiceImpl;

@WebServlet("/vnpay-return")
public class VNPayReturnServlet extends HttpServlet {
    private IPaymentService paymentService = new PaymentServiceImpl();
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
            resp.getWriter().println("Thanh toán thành công!");
        } else {
            payment.setPaymentStatus(Payment.PaymentStatus.CANCELED);
            resp.getWriter().println("Thanh toán thất bại hoặc bị hủy.");
        }

        paymentService.update(payment);
    }
}
