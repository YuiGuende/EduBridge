/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service.cart;

import jakarta.servlet.http.HttpSession;

/**
 *
 * @author GoniperXComputer
 */


import jakarta.servlet.http.HttpSession;
import model.cart.ShoppingCart;
import model.course.Course;

public interface CartService {
    // Các phương thức cơ bản
    ShoppingCart getCart(HttpSession session);
    ShoppingCart getOrCreateCart(HttpSession session);
    void saveCart(HttpSession session, ShoppingCart cart);
    
    // Quản lý mục giỏ hàng
    void addToCart(HttpSession session, Long courseId);
    void addToCart(HttpSession session, Course course);
    void removeFromCart(HttpSession session, Long courseId);
    void updateCartItem(HttpSession session, Long courseId, int quantity);
    void clearCart(HttpSession session);
    
    // Quản lý giảm giá
    boolean applyDiscount(HttpSession session, String discountCode);
    void removeDiscount(HttpSession session);
    Double getDiscountAmount(HttpSession session);
    String getAppliedDiscountCode(HttpSession session);
    
    // Kiểm tra và thông tin
    boolean containsCourse(HttpSession session, Long courseId);
    boolean isEmpty(HttpSession session);
    int getCartItemCount(HttpSession session);
    int getTotalQuantity(HttpSession session);
    
    // Tính toán
    double calculateSubtotal(HttpSession session);
    double calculateTax(HttpSession session);
    double calculateTotal(HttpSession session);
    double calculateTotalWithDiscount(HttpSession session);
    
    // Xử lý đặc biệt
    void mergeCarts(HttpSession sourceSession, HttpSession targetSession);
    ShoppingCart reloadCartPrices(HttpSession session);
}
    

