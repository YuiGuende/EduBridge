package model.payment;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToMany;

import jakarta.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;
import model.user.User;

@Entity
public class Payment {

    public enum PaymentStatus {
        PENDING,
        CONFIRMED,
        CANCELED,
        EXPIRED
    }

    @Id
    @SequenceGenerator(
            name = "payment_sequence",
            sequenceName = "payment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_sequence"
    )
    private Long id;

    private String paymentMethod; // Ph??ng th?c thanh to�n (Card, Cash, Online)
    private LocalDate paymentDate; // Ng�y thanh to�n
    private double amount; // S? ti?n thanh to�n
    private double serviceAmount;

    private String vnp;//m� giao d?ch c?a VNpay
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<PaymentDetail> paymentDetails = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Payment() {
    }

    public Payment(String paymentMethod, LocalDate paymentDate, double amount, double serviceAmount, String vnp, PaymentStatus paymentStatus) {
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.serviceAmount = serviceAmount;
        this.vnp = vnp;
        this.paymentStatus = paymentStatus;
    }

    public Payment(String paymentMethod, LocalDate paymentDate, double amount, double serviceAmount, String vnp, PaymentStatus paymentStatus, List<PaymentDetail> paymentDetails) {
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.serviceAmount = serviceAmount;
        this.vnp = vnp;
        this.paymentStatus = paymentStatus;
        this.paymentDetails = paymentDetails;
    }

    // public Host getHost(){//ki?m tra l?i
    //     return getBooking().getBookingRooms().get(0).getRoom().getHotel().getHost();
    // }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public List<PaymentDetail> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getVnp() {
        return vnp;
    }

    public void setVnp(String vnp) {
        this.vnp = vnp;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    @Override
    public String toString() {
        return "Payment [id=" + id + ", paymentMethod=" + paymentMethod + ", paymentDate=" + paymentDate + ", amount="
                + amount + ", serviceAmount=" + serviceAmount + ", vnp=" + vnp + ", paymentStatus=" + paymentStatus
                + "]";
    }

}
