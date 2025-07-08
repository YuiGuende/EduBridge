/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.cart;

import DAO.Cart.DiscountDAO;
import java.util.List;
import java.util.Optional;
import model.cart.Discount;

/**
 *
 * @author GoniperXComputer
 */
public class DiscountServiceImpl implements DiscountService {
       private final DiscountDAO discountDAO;
    
    public DiscountServiceImpl(DiscountDAO discountDAO) {
        this.discountDAO = discountDAO;
    }

    @Override
    public Discount createDiscount(Discount discount) {
           // Validate discount code uniqueness
        if (discountDAO.findByCode(discount.getCode()).isPresent()) {
            throw new IllegalArgumentException("Discount code already exists");
        }
        
        // Set default values
        if (discount.getCurrentUses() == null) {
            discount.setCurrentUses(0);
        }
        
        return discountDAO.save(discount);
    }

    @Override
    public Optional<Discount> getDiscountById(Long id) {
            return discountDAO.findById(id);
    }

    @Override
    public Optional<Discount> getDiscountByCode(String code) {
        return discountDAO.findByCode(code);
    }

    @Override
    public List<Discount> getAllActiveDiscounts() {
          return discountDAO.findAllActiveDiscounts();
    }

    @Override
    public List<Discount> getAllDiscounts() {
        return discountDAO.findAll();
    }

    @Override
    public boolean deactivateDiscount(Long id) {
         return discountDAO.deactivate(id);
    }

    @Override
    public boolean validateAndApplyDiscount(String code) {
          Optional<Discount> discountOpt = discountDAO.findByCode(code);
        if (discountOpt.isPresent()) {
            Discount discount = discountOpt.get();
            if (discount.isValid()) {
                discount.incrementUses();
                discountDAO.save(discount);
                return true;
            }
        }
        return false;
    }
    @Override
    public double calculateDiscountAmount(Discount discount, double subtotal) {
           if (discount == null || !discount.isValid()) {
            return 0;
        }
        
        switch (discount.getType()) {
            case PERCENTAGE:
                return subtotal * (discount.getValue() / 100);
            case FIXED_AMOUNT:
                return Math.min(discount.getValue(), subtotal);
            default:
                return 0;
        }
    }
    
    
}
