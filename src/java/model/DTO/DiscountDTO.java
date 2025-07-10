package model.DTO;
public class DiscountDTO {
    private String code;

    public DiscountDTO() {}

    public DiscountDTO(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
