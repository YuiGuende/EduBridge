/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author LEGION
 */
@Embeddable
public class Rate {

    @Column(nullable = true)
    private double rate;
    @Column(nullable = true)
    private int rateQuantity;

    public Rate() {
        this.rate = 0;
        this.rateQuantity = 0;
    }

    public Rate(double rate, int rateQuantity) {
        this.rate = rate;
        this.rateQuantity = rateQuantity;
    }

    public void addRate(double rate) {
        this.rate = (this.rate * rateQuantity + rate) / (rateQuantity + 1);
        rateQuantity++;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getRateQuantity() {
        return rateQuantity;
    }

    public void setRateQuantity(int rateQuantity) {
        this.rateQuantity = rateQuantity;
    }

}
