/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.course.Course;
import model.cart.DiscountType;


/**
 *
 * @author GoniperXComputer
 */
public class ShoppingCart {
     private List<CartItem> cartItems = new ArrayList<>();
         private Discount discount;

    public ShoppingCart() {
        this.discount = discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
         
    
    public void addItem(Course course) {
        for (CartItem item : cartItems) {
            if (item.getCourse().getId().equals(course.getId())) {
                item.incrementQuantity();
                return;
            }
        }
        cartItems.add(new CartItem(course));
    }
    
    public void removeItem(Long courseId) {
        cartItems.removeIf(item -> item.getCourse().getId().equals(courseId));
    }
    
    public void updateQuantity(Long courseId, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getCourse().getId().equals(courseId)) {
                if (quantity <= 0) {
                    removeItem(courseId);
                } else {
                    item.setQuantity(quantity);
                }
                return;
            }
        }
    }
    
    public double getSubtotal() {
        return cartItems.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }
    
    public double getTax() {
        // VAT 10%
        return getSubtotal() * 0.1;
    }
    
    public double getTotal() {
        return getSubtotal() + getTax();
    }
    
    public int getItemCount() {
        return cartItems.size();
    }
    
    public void clear() {
        cartItems.clear();
    }
    
    // Getters and Setters
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
     public void applyDiscount(Discount discount) {
        if (discount != null && discount.isValid()) {
            this.discount = discount;
        }
    }
    
    public void removeDiscount() {
        this.discount = null;
    }
    
public double getDiscountAmount() {
    if (discount == null) return 0;

    double subtotal = getSubtotal();

    switch (discount.getType()) {
        case PERCENTAGE:
            return subtotal * (discount.getValue() / 100);
        case FIXED_AMOUNT:
            return Math.min(discount.getValue(), subtotal);
        default:
            return 0;
    }
}

    
    // Thêm getter cho discount
    public Discount getDiscount() {
        return discount;
    }

    
    public void addItem(Course course, int quantity) {
        findItem(course.getId()).ifPresentOrElse(
            item -> item.setQuantity(item.getQuantity() + quantity),
            () -> cartItems.add(new CartItem(course, quantity))
        );
    }

    
    public Optional<CartItem> findItem(Long courseId) {
        return cartItems.stream()
            .filter(item -> item.getCourse().getId().equals(courseId))
            .findFirst();
    }
    
    public boolean containsCourse(Long courseId) {
        return findItem(courseId).isPresent();
    }
    

    public double getTotalWithDiscount() {
        return getTotal() - getDiscountAmount();
    }
    
    public void recalculateTotals() {
        // ch trien khai duoc paymethod
    }
    
    // Các getter và setter
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    
    public int getTotalQuantity() {
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }
}
    

