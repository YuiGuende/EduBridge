/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Cart;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import model.cart.Discount;

/**
 *
 * @author GoniperXComputer
 */
public class DiscountDAOImpl implements DiscountDAO {
   @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Discount save(Discount discount) {
        if (discount.getId() == null) {
            entityManager.persist(discount);
            return discount;
        } else {
            return entityManager.merge(discount);
        }
    }

    @Override
    public Optional<Discount> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Discount.class, id));
    }
    @Override
    public Optional<Discount> findByCode(String code) {
         TypedQuery<Discount> query = entityManager.createQuery(
            "SELECT d FROM Discount d WHERE d.code = :code", Discount.class);
        query.setParameter("code", code);
        
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    @Override
    public List<Discount> findAllActiveDiscounts() {
       LocalDateTime now = LocalDateTime.now();
        return entityManager.createQuery(
            "SELECT d FROM Discount d WHERE d.active = true AND " +
            "d.startDate <= :now AND d.endDate >= :now", Discount.class)
            .setParameter("now", now)
            .getResultList();
    }

    @Override
    public List<Discount> findAll() {
       return entityManager.createQuery("SELECT d FROM Discount d", Discount.class)
            .getResultList();
    }

    @Override
    public boolean deactivate(Long id) {
         int updated = entityManager.createQuery(
            "UPDATE Discount d SET d.active = false WHERE d.id = :id")
            .setParameter("id", id)
            .executeUpdate();
        return updated > 0;
    }

    @Override
    public int countUses(String code) {
        return entityManager.createQuery(
            "SELECT d.currentUses FROM Discount d WHERE d.code = :code", Integer.class)
            .setParameter("code", code)
            .getSingleResult();
    }
    
}
