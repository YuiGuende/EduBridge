/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import model.course.Course;

/**
 *
 * @author LEGION
 */
@Entity
public class PaymentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Payment payment;

    @ManyToOne
    private Course course;

    private double amount;
    
    

    // Constructor, getter, setter...

    public PaymentDetail() {
    }

    public PaymentDetail(Payment payment, Course course, double amount) {
        this.payment = payment;
        this.course = course;
        this.amount = amount;
    }

    public PaymentDetail(Long id, Payment payment, Course course, double amount) {
        this.id = id;
        this.payment = payment;
        this.course = course;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PaymentDetail{" + "id=" + id + ", payment=" + payment + ", course=" + course + ", amount=" + amount + '}';
    }
    
    
}
