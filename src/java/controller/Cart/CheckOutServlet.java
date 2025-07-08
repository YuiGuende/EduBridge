/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Cart;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.cart.ShoppingCart;
import service.cart.CartService;
import service.cart.CartServiceImpl;
import service.course.CourseServiceImpl;

/**
 *
 * @author GoniperXComputer
 */
@WebServlet(name="CheckOutServlet", urlPatterns={"/checkout"})
public class CheckOutServlet extends HttpServlet {
   
     private CartService cartService;

    @Override
    public void init() {
        this.cartService = new CartServiceImpl(new CourseServiceImpl(), null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ShoppingCart cart = cartService.getCart(request.getSession());

        if (cart == null || cart.isEmpty()) {
            request.setAttribute("error", "Cart is empty");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/shoppingcart/cart.jsp");
dispatcher.forward(request, response);
            return;
        }

        double total = cartService.calculateTotalWithDiscount(request.getSession());

        // Xử lý logic thanh toán thực (cổng thanh toán, ghi đơn hàng, v.v.)
        // Hiện tại: chỉ mô phỏng thành công

        cartService.clearCart(request.getSession());
        response.sendRedirect("shoppingcart/thankiu.jsp");
    }
}
