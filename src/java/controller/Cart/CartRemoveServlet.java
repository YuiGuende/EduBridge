/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Cart;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.cart.CartService;
import service.cart.CartServiceImpl;
import service.course.CourseServiceImpl;

/**
 *
 * @author GoniperXComputer
 */
@WebServlet(name="CartRemoveServlet", urlPatterns={"/cart/remove"})
public class CartRemoveServlet extends HttpServlet {
   
    private CartService cartService;

    @Override
    public void init() {
        this.cartService = new CartServiceImpl(new CourseServiceImpl(), null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseIdParam = request.getParameter("courseId");
        if (courseIdParam != null) {
            try {
                Long courseId = Long.parseLong(courseIdParam);
                cartService.removeFromCart(request.getSession(), courseId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }
}