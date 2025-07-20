package model.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.payment.Payment;
import model.payment.PaymentDetail;
import util.DateUtils;

public class PaymentDetailDTO {
    
    private Long id;
    private String userName;
    private Long userId;
    private String paymentMethod;
    private Date paymentDate;
    private double totalAmount;
    private double serviceAmount;
    private String vnpTxnRef;
    private String paymentStatus;
    private List<PaymentCourseDetail> courseDetails;
    
    public PaymentDetailDTO(Payment payment) {
        this.id = payment.getId();
        this.userName = payment.getUser() != null ? payment.getUser().getFullname() : "N/A";
        this.userId = payment.getUser() != null ? payment.getUser().getId() : null;
        this.paymentMethod = payment.getPaymentMethod();
        this.paymentDate = DateUtils.asDate(payment.getPaymentDate());
        this.totalAmount = payment.getAmount();
        this.serviceAmount = payment.getServiceAmount();
        this.vnpTxnRef = payment.getVnp();
        this.paymentStatus = payment.getPaymentStatus().name();
        this.courseDetails = payment.getPaymentDetails().stream()
                .map(PaymentCourseDetail::new)
                .collect(Collectors.toList());
    }

    // Getters
    public Long getId() {
        return id;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public Date getPaymentDate() {
        return paymentDate;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public double getServiceAmount() {
        return serviceAmount;
    }
    
    public String getVnpTxnRef() {
        return vnpTxnRef;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public List<PaymentCourseDetail> getCourseDetails() {
        return courseDetails;
    }

    // Nested DTO for course details within a payment
    public static class PaymentCourseDetail {
        
        private Long courseId;
        private String courseTitle;
        private double amount;
        
        public PaymentCourseDetail(PaymentDetail detail) {
            this.courseId = detail.getCourse() != null ? detail.getCourse().getId() : null;
            this.courseTitle = detail.getCourse() != null ? detail.getCourse().getTitle() : "N/A";
            this.amount = detail.getAmount();
        }

        // Getters
        public Long getCourseId() {
            return courseId;
        }
        
        public String getCourseTitle() {
            return courseTitle;
        }
        
        public double getAmount() {
            return amount;
        }
    }
}
