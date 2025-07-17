package model.DTO.revenue;
public class MonthlyRevenueDTO {
    private String month;
    private Double revenue;

    public MonthlyRevenueDTO(String month, Double revenue) {
        this.month = month;
        this.revenue = revenue;
    }

    public String getMonth() {
        return month;
    }

    public Double getRevenue() {
        return revenue;
    }

    @Override
    public String toString() {
        return "MonthlyRevenueDTO{" + "month=" + month + ", revenue=" + revenue + '}';
    }
    
}
