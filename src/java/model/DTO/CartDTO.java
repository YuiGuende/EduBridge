package model.DTO;

import java.util.List;

public class CartDTO {

    private List<CartItemDTO> cartItems;
    private double subtotal;
    private double tax;
    private DiscountDTO discount;
    private double discountAmount;
    private double totalWithDiscount;

    public CartDTO() {
    }

    public CartDTO(List<CartItemDTO> cartItems, double subtotal, double tax, DiscountDTO discount, double discountAmount, double totalWithDiscount) {
        this.cartItems = cartItems;
        this.subtotal = subtotal;
        this.tax = tax;
        this.discount = discount;
        this.discountAmount = discountAmount;
        this.totalWithDiscount = totalWithDiscount;
    }

    // Getters and Setters
    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public DiscountDTO getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountDTO discount) {
        this.discount = discount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getTotalWithDiscount() {
        return totalWithDiscount;
    }

    public void setTotalWithDiscount(double totalWithDiscount) {
        this.totalWithDiscount = totalWithDiscount;
    }
}
