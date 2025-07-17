package model.DTO;

import java.math.BigDecimal;

public class CartItemDTO {
    private Long courseId;
    private String title;
    private String thumbnailUrl;
    private double originalPrice;
    private double discountedPrice;

    // Constructor, Getters, Setters

    public CartItemDTO() {
    }

    public CartItemDTO(Long courseId, String title, String thumbnailUrl, double originalPrice, double discountedPrice) {
        this.courseId = courseId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    @Override
    public String toString() {
        return "CartItemDTO{" + "courseId=" + courseId + ", title=" + title + ", thumbnailUrl=" + thumbnailUrl + ", originalPrice=" + originalPrice + ", discountedPrice=" + discountedPrice + '}';
    }
    
}