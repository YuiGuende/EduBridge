/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.cart;

/**
 *
 * @author GoniperXComputer
 */
import model.course.Course;

public class CartItem {
    private Course course;
    private int quantity;
    
    public CartItem() {
    }
    
    public CartItem(Course course) {
        this.course = course;
        this.quantity = 1;
    }
    
    public CartItem(Course course, int quantity) {
        this.course = course;
        this.quantity = quantity;
    }
    
    public double getSubtotal() {
        return course.isPaid() ? course.getPrice() * quantity : 0;
    }
    
    // Getters and Setters
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void incrementQuantity() {
        this.quantity++;
    }
    
    public void decrementQuantity(int price) {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }
}
