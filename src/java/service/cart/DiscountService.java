/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service.cart;

import java.util.List;
import java.util.Optional;
import model.cart.Discount;

/**
 *
 * @author GoniperXComputer
 */
public interface DiscountService {
 Discount createDiscount(Discount discount);
    Optional<Discount> getDiscountById(Long id);
    Optional<Discount> getDiscountByCode(String code);
    List<Discount> getAllActiveDiscounts();
    List<Discount> getAllDiscounts();
    boolean deactivateDiscount(Long id);
    boolean validateAndApplyDiscount(String code);
    double calculateDiscountAmount(Discount discount, double subtotal);
    
}
