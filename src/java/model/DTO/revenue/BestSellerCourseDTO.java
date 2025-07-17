package model.DTO.revenue;
public class BestSellerCourseDTO {
    private String courseName;
    private Long totalSold;
    private double totalAmount;

    public BestSellerCourseDTO(String courseName, Long totalSold, double totalAmount) {
        this.courseName = courseName;
        this.totalSold = totalSold;
        this.totalAmount = totalAmount;
    }
    
    public String getCourseName() {
        return courseName;
    }

    public Long getTotalSold() {
        return totalSold;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "BestSellerCourseDTO{" + "courseName=" + courseName + ", totalSold=" + totalSold + ", totalAmount=" + totalAmount + '}';
    }
    
}