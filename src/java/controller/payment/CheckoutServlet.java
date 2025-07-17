package controller.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.course.Course;
import model.course.CourseLearnerPK;
import model.payment.Payment;
import model.payment.PaymentDetail;
import model.user.User;
import service.cart.CartService;
import service.course.CourseLearnerServiceImpl;
import service.course.CourseService;
import service.course.CourseServiceImpl;
import service.course.ICourseLearnerService;
import service.payment.IPaymentService;
import service.payment.PaymentServiceImpl;
import service.payment.VNPayService;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private final CartService cartService = new CartService();
    private CourseService courseService = new CourseServiceImpl();
    private final VNPayService vnpayService = new VNPayService();
    private final IPaymentService paymentService = new PaymentServiceImpl();
    private ICourseLearnerService courseLearnerService = new CourseLearnerServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            String[] selectedIds = req.getParameterValues("selectedCourseIds");
            
            if (selectedIds == null || selectedIds.length == 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No courses selected");
                return;
            }

            User u = (session != null) ? (User) session.getAttribute("user") : null;
            if(u==null){
                resp.sendRedirect("login");
                return;
            }
            List<Course> selectedCourses = new ArrayList<>();
            List<PaymentDetail> paymentDetails = new ArrayList<>();
            for (String idStr : selectedIds) {
                try {
                    Long id = Long.parseLong(idStr);
                    Course course = courseService.findCourse(id); // l?y t? DB
                    if (courseLearnerService.findCourseLearner(new CourseLearnerPK(id, u.getId()))) {
                        throw new Exception("you already enrolled course " + course.getTitle());
                    }
                    if (course != null) {
                        selectedCourses.add(course);
                        cartService.removeFromCart(course.getId(), session);
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
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", e.getMessage());
            req.setAttribute("cartDTO", cartService.getCartDTO(session));
            req.getRequestDispatcher("cart/cart.jsp").forward(req, resp);
        }

    }
}
