package model.DTO.revenue;

public class TotalRevenueDTO {

    private Double total, monthly, yearly;

    public TotalRevenueDTO(Double total, Double monthly, Double yearly) {
        this.total = total;
        this.monthly = monthly;
        this.yearly = yearly;
    }

    public Double getTotal() {
        return total;
    }

    public Double getMonthly() {
        return monthly;
    }

    public Double getYearly() {
        return yearly;
    }

    @Override
    public String toString() {
        return "TotalRevenueDTO{" + "total=" + total + ", monthly=" + monthly + ", yearly=" + yearly + '}';
    }

}
