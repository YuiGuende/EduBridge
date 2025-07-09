/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.Cart;

import java.util.List;
import java.util.Optional;
import model.cart.Discount;

/**
 *
 * @author GoniperXComputer
 */
public interface DiscountDAO {
   Discount save(Discount discount);
    Optional<Discount> findById(Long id);
    Optional<Discount> findByCode(String code);
    List<Discount> findAllActiveDiscounts();
    List<Discount> findAll();
    boolean deactivate(Long id);
    int countUses(String code);
    
}
