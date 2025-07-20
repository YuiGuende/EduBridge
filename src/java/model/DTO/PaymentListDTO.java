package model.DTO;


import java.util.Date;
import model.payment.Payment;
import util.DateUtils;

public class PaymentListDTO {

    private Long id;
    private String userName;
    private String paymentMethod;
    private Date paymentDate;
    private double amount;
    private String vnpTxnRef;
    private String status;

    public PaymentListDTO(Payment payment) {
        this.id = payment.getId();
        this.userName = payment.getUser() != null ? payment.getUser().getFullname() : "N/A";
        this.paymentMethod = payment.getPaymentMethod();
        this.paymentDate = DateUtils.asDate(payment.getPaymentDate());
        this.amount = payment.getAmount();
        this.vnpTxnRef = payment.getVnp();
        this.status = payment.getPaymentStatus().name();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public String getVnpTxnRef() {
        return vnpTxnRef;
    }

    public String getStatus() {
        return status;
    }

    // Setters (optional)
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setVnpTxnRef(String vnpTxnRef) {
        this.vnpTxnRef = vnpTxnRef;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
