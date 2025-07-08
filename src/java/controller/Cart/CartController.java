/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Cart;


import DAO.Cart.DiscountDAO;
import DAO.Cart.DiscountDAOImpl;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.cart.CartItem;
import model.cart.ShoppingCart;
import service.cart.CartService;
import service.cart.CartServiceImpl;
import service.cart.DiscountService;
import service.cart.DiscountServiceImpl;
import service.course.CourseService;
import service.course.CourseServiceImpl;

/**
 *
 * @author GoniperXComputer
 */
@WebServlet(name="CartController", urlPatterns={"/cart"})
public class CartController extends HttpServlet {
       private CartService cartService;

    @Override
    public void init() throws ServletException {
        CourseService courseService = new CourseServiceImpl();
          DiscountDAO discountDAO = new DiscountDAOImpl();
    DiscountService discountService = new DiscountServiceImpl(discountDAO);

    cartService = new CartServiceImpl(courseService, discountService);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CartController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if (action == null) {
            showCartPage(request, response, session);
        } else {
            switch (action) {
                case "add":
                    handleAddToCart(request, session);
                    break;
                case "remove":
                    handleRemoveFromCart(request, session);
                    break;
                case "clear":
                    cartService.clearCart(session);
                    break;
                case "applyDiscount":
                    handleApplyDiscount(request, session);
                    break;
                case "checkout":
                    handleCheckout(request, response, session);
                    return;
            }
 RequestDispatcher dispatcher = request.getRequestDispatcher("/shoppingcart/cart.jsp");
dispatcher.forward(request, response);

        }
    
    } 
    private void showCartPage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws ServletException, IOException {

    ShoppingCart cart = cartService.getCart(session);
List<CartItem> purchasedCourses = cart.getCartItems();
request.setAttribute("purchasedCourses", purchasedCourses);

        request.setAttribute("cart", cart);
        request.setAttribute("subtotal", cartService.calculateSubtotal(session));
        request.setAttribute("tax", cartService.calculateTax(session));
        request.setAttribute("total", cartService.calculateTotal(session));
        request.setAttribute("discountCode", cartService.getAppliedDiscountCode(session));
        request.setAttribute("discountAmount", cartService.getDiscountAmount(session));
        request.setAttribute("finalTotal", cartService.calculateTotalWithDiscount(session));

 RequestDispatcher dispatcher = request.getRequestDispatcher("/shoppingcart/cart.jsp");
dispatcher.forward(request, response);
    }
        private void handleAddToCart(HttpServletRequest request, HttpSession session) {
        Long courseId = Long.parseLong(request.getParameter("id"));
        cartService.addToCart(session, courseId);
    }
           private void handleRemoveFromCart(HttpServletRequest request, HttpSession session) {
        Long courseId = Long.parseLong(request.getParameter("id"));
        cartService.removeFromCart(session, courseId);
    }
               private void handleApplyDiscount(HttpServletRequest request, HttpSession session) {
        String code = request.getParameter("discountCode");
        if (code != null && !code.isEmpty()) {
            boolean success = cartService.applyDiscount(session, code);
            request.setAttribute("discountMessage", success ? "Mã đã áp dụng!" : "Mã không hợp lệ hoặc đã hết hạn!");
        }
    }
    private void handleCheckout(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws ServletException, IOException {
   ShoppingCart cart = cartService.getCart(session);
    List<CartItem> purchasedCourses = cart.getCartItems();
        // Xử lý lưu đơn hàng, gửi mail, thanh toán...
        // Sau khi thành công:
        // phương thúc này chưa hoạt động tốt nhất vì thuận ch làm đc phương thức thanh toán 
       cartService.clearCart(session);

    request.setAttribute("purchasedCourses", purchasedCourses);
    request.setAttribute("message", "Thanh toán thành công!");
    request.getRequestDispatcher("shoppingcart/thankyou.jsp").forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    
    }


}
