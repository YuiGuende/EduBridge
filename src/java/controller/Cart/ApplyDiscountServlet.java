/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Cart;

import DAO.Cart.DiscountDAO;
import DAO.Cart.DiscountDAOImpl;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.cart.CartService;
import service.cart.CartServiceImpl;
import service.cart.DiscountServiceImpl;
import service.course.CourseServiceImpl;

/**
 *
 * @author GoniperXComputer
 */
@WebServlet(name="ApplyDiscountServlet", urlPatterns={"/apply-discount"})
public class ApplyDiscountServlet extends HttpServlet {
   
   
    private CartService cartService;

    @Override
    public void init() {
        DiscountDAO discountDAO = new DiscountDAOImpl(); // Đảm bảo lớp này đã được tạo
        this.cartService = new CartServiceImpl(new CourseServiceImpl(), new DiscountServiceImpl(discountDAO));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("discountCode");

        boolean success = cartService.applyDiscount(request.getSession(), code);

        if (success) {
            request.setAttribute("discountSuccess", "Discount applied successfully!");
        } else {
            request.setAttribute("discountError", "Invalid or expired discount code.");
        }

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}