package model.cart;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private double discountPercent; // e.g., 0.2 = 20%
    private boolean active;
    private LocalDateTime expiryDate;

    // Getters, Setters
    public Coupon() {
    }

    public Coupon(Long id, String code, double discountPercent, boolean active, LocalDateTime expiryDate) {
        this.id = id;
        this.code = code;
        this.discountPercent = discountPercent;
        this.active = active;
        this.expiryDate = expiryDate;
    }

    public Coupon(String code, double discountPercent, boolean active, LocalDateTime expiryDate) {
        this.code = code;
        this.discountPercent = discountPercent;
        this.active = active;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

}
