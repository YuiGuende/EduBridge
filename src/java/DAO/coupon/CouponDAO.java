package DAO.coupon;

import java.util.Optional;
import model.cart.Coupon;

public interface CouponDAO {

    Optional<Coupon> findByCode(String code);

    boolean isValid(Coupon coupon);
}
