/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.Cart;

import DAO.GenericDAO;
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
public class DiscountDAOImpl extends GenericDAO implements DiscountDAO {

    public DiscountDAOImpl() {
        super(Discount.class);
    }

    @Override
    public Discount save(Discount discount) {
           return (Discount) super.save(discount);
    }

    @Override
    public Optional<Discount> findById(Long id) {
          return super.findByIdReturnOptional(id);
    }

    @Override
    public Optional<Discount> findByCode(String code) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<Discount> query = em.createQuery(
                "SELECT d FROM Discount d WHERE d.code = :code", Discount.class);
            query.setParameter("code", code);
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Discount> findAllActiveDiscounts() {
       try (EntityManager em = getEntityManager()) {
            LocalDateTime now = LocalDateTime.now();
            return em.createQuery(
                "SELECT d FROM Discount d WHERE d.active = true AND d.startDate <= :now AND d.endDate >= :now",
                Discount.class)
                .setParameter("now", now)
                .getResultList();
        }
    }

    @Override
    public boolean deactivate(Long id) {
        try (EntityManager em = getEntityManager()) {
            em.getTransaction().begin();
            int updated = em.createQuery(
                "UPDATE Discount d SET d.active = false WHERE d.id = :id")
                .setParameter("id", id)
                .executeUpdate();
            em.getTransaction().commit();
            return updated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int countUses(String code) {
        try (EntityManager em = getEntityManager()) {
            return em.createQuery(
                "SELECT d.currentUses FROM Discount d WHERE d.code = :code", Integer.class)
                .setParameter("code", code)
                .getSingleResult();
        }
    }
  
    
}
