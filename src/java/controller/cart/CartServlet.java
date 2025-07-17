package controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.cart.CartService;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Chỉ dùng GET để hiển thị giỏ hàng
        String action = req.getParameter("action");
        switch (action) {
            case "add":
                req.getRequestDispatcher("cart/add-to-cart.jsp").forward(req, resp);
                break;

            default:
                HttpSession session = req.getSession();
                req.setAttribute("cartDTO", cartService.getCartDTO(session));
                req.getRequestDispatcher("cart/cart.jsp").forward(req, resp);
                return;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Dùng POST để xử lý action: add, remove
        String action = req.getParameter("action");
        String idParam = req.getParameter("id");
        HttpSession session = req.getSession();
        System.out.println("action:" + action);
        if (action == null || idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action or course ID.");
            return;
        }

        Long courseId;
        try {
            courseId = Long.valueOf(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid course ID format.");
            return;
        }

        switch (action) {
            case "add":
                cartService.addToCart(courseId, session);
                break;
            case "remove":
                cartService.removeFromCart(courseId, session);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                return;
        }

        req.setAttribute("cartDTO", cartService.getCartDTO(session));
        req.getRequestDispatcher("cart/cart.jsp").forward(req, resp);

        // Sau khi xử lý xong thì redirect về trang cart
    }
}
