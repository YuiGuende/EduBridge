package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.DTO.PaymentDetailDTO;
import model.DTO.PaymentListDTO;
import model.payment.Payment;
import service.payment.IPaymentService;
import service.payment.PaymentServiceImpl;
import util.JSPUtils;

@WebServlet("/admin/payments")
public class AdminPaymentServlet extends HttpServlet {

    private IPaymentService paymentService;

    @Override
    public void init() throws ServletException {
        super.init();
        paymentService = new PaymentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Default action
        }

        switch (action) {
            case "list":
                listPayments(request, response);
                break;
            case "view":
                viewPayment(request, response);
                break;
            // Removed direct GET handling for delete/updateStatus as POST is preferred and already handled
            default:
                listPayments(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "updateStatus":
                updatePaymentStatus(request, response);
                break;
            case "delete":
                deletePayment(request, response);
                break;
            default:
                listPayments(request, response);
                break;
        }
    }

    private void listPayments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int size = 10; // Default page size

        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                // Log error or set default page
                page = 1;
            }
        }
        if (request.getParameter("size") != null) {
            try {
                size = Integer.parseInt(request.getParameter("size"));
            } catch (NumberFormatException e) {
                // Log error or set default size
                size = 10;
            }
        }

        List<Payment> payments = paymentService.getPaymentsWithPagination(page, size);
        List<PaymentListDTO> paymentDTOs = payments.stream()
                .map(PaymentListDTO::new)
                .collect(Collectors.toList());

        long totalPayments = paymentService.getTotalPayments();
        int totalPages = (int) Math.ceil((double) totalPayments / size);
        List<String> pageUrls = new ArrayList<>();
        for (int p = 1; p <= totalPages; p++) {
            pageUrls.add(JSPUtils.buildPaginationUrl(request, p));
        }
        request.setAttribute("pageUrls", pageUrls);
        request.setAttribute("payments", paymentDTOs);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", size); // Ensure pageSize is set as an attribute

        request.getRequestDispatcher("/admin/payments.jsp").forward(request, response);
    }

    private void viewPayment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/payments?error=" + JSPUtils.htmlEncode("Invalid payment ID provided."));
            return;
        }

        Payment payment = paymentService.getPaymentById(id); // Assuming getPaymentById returns Payment directly or Optional
        if (payment != null) { // Check if payment exists
            PaymentDetailDTO paymentDetail = new PaymentDetailDTO(payment);
            request.setAttribute("payment", paymentDetail);
            request.getRequestDispatcher("/admin/payment-details.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/payments?error=" + JSPUtils.htmlEncode("Payment not found."));
        }
    }

    private void updatePaymentStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long paymentId = null;
        String statusStr = request.getParameter("status");
        try {
            paymentId = Long.parseLong(request.getParameter("paymentId"));
            Payment.PaymentStatus newStatus = Payment.PaymentStatus.valueOf(statusStr.toUpperCase());
            paymentService.updatePaymentStatus(paymentId, newStatus);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"message\": \"Status updated successfully\"}");
        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"" + JSPUtils.htmlEncode("Invalid payment ID format.") + "\"}");
        } catch (IllegalArgumentException e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"" + JSPUtils.htmlEncode("Invalid status provided: " + statusStr) + "\"}");
        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"" + JSPUtils.htmlEncode("Error updating status: " + e.getMessage()) + "\"}");
        }
    }

    private void deletePayment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(request.getParameter("paymentId"));
        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"" + JSPUtils.htmlEncode("Invalid payment ID format.") + "\"}");
            return;
        }

        try {
            paymentService.deletePayment(id);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"message\": \"Payment deleted successfully\"}");
        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"" + JSPUtils.htmlEncode("Error deleting payment: " + e.getMessage()) + "\"}");
        }
    }
}
