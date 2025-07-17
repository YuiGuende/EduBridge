package model.DTO.revenue;
public class DailyRevenueDTO {
    private String dayName;
    private double totalRevenue;

    public DailyRevenueDTO(String dayName, double totalRevenue) {
        this.dayName = dayName;
        this.totalRevenue = totalRevenue;
    }

    public String getDayName() {
        return dayName;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    @Override
    public String toString() {
        return "DailyRevenueDTO{" + "dayName=" + dayName + ", totalRevenue=" + totalRevenue + '}';
    }
    
}