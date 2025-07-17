package DAO.coupon;

import DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.Optional;
import model.cart.Coupon;

public class CouponDAOImpl extends GenericDAO<Coupon> implements CouponDAO {

    public CouponDAOImpl() {
        super(Coupon.class);
    }

    @Override
    public Optional<Coupon> findByCode(String code) {
        EntityManager em = getEntityManager();
        try {
            return Optional.of(em.createQuery("SELECT c FROM Coupon c WHERE c.code = :code", Coupon.class)
                    .setParameter("code", code)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isValid(Coupon coupon) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
