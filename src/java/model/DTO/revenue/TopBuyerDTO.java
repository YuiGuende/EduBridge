package model.DTO.revenue;
public class TopBuyerDTO {
    private String buyerName;
    private String email;
    private Double totalSpent;

    public TopBuyerDTO(String buyerName, String email, Double totalSpent) {
        this.buyerName = buyerName;
        this.email = email;
        this.totalSpent = totalSpent;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getEmail() {
        return email;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    @Override
    public String toString() {
        return "TopBuyerDTO{" + "buyerName=" + buyerName + ", email=" + email + ", totalSpent=" + totalSpent + '}';
    }
    
}
