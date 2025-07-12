package controller.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.course.Course;
import model.payment.Payment;
import model.payment.PaymentDetail;
import service.course.CourseService;
import service.course.CourseServiceImpl;
import service.payment.IPaymentService;
import service.payment.PaymentServiceImpl;
import service.payment.VNPayService;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private CourseService courseService = new CourseServiceImpl();
    private final VNPayService vnpayService = new VNPayService();
    private final IPaymentService paymentService = new PaymentServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] selectedIds = req.getParameterValues("selectedCourseIds");

        if (selectedIds == null || selectedIds.length == 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No courses selected");
            return;
        }

        List<Course> selectedCourses = new ArrayList<>();
        List<PaymentDetail> paymentDetails = new ArrayList<>();
        for (String idStr : selectedIds) {
            try {
                Long id = Long.parseLong(idStr);
                Course course = courseService.findCourse(id); // l?y t? DB
                if (course != null) {
                    selectedCourses.add(course);
                }
            } catch (NumberFormatException ignored) {
            }
        }

        if (selectedCourses.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No valid courses found");
            return;
        }

        if (selectedCourses == null || selectedCourses.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No courses selected");
            return;
        }

        String paymentUrl = vnpayService.createPaymentUrl(selectedCourses, req);
        System.out.println("paymenturl " + paymentUrl);

        resp.sendRedirect(paymentUrl);

    }
}
