/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.cart;

import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import model.cart.CartItem;
import model.cart.Discount;
import model.cart.ShoppingCart;
import model.course.Course;
import model.user.User;
import service.course.CourseService;

/**
 *
 * @author GoniperXComputer
 */
public class CartServiceImpl implements CartService {
      private static final String CART_SESSION_KEY = "shoppingCart";
    private static final String USER_SESSION_KEY = "currentUser";
     private static final String USER_CART_MAP_KEY = "userCartMap";
    private final CourseService courseService ;
    private final DiscountService discountService;

    public CartServiceImpl(CourseService courseService, DiscountService discountService) {
        this.courseService = courseService;
        this.discountService = discountService;
    } 
    
    @Override
    public ShoppingCart getCart(HttpSession session) {
           ShoppingCart cart = (ShoppingCart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    @Override
    public ShoppingCart getOrCreateCart(HttpSession session) { 
    ShoppingCart cart = (ShoppingCart) session.getAttribute(CART_SESSION_KEY);
    if (cart == null) {
        cart = new ShoppingCart();
        session.setAttribute(CART_SESSION_KEY, cart);
    }
    return cart;
}

    @Override
    public void saveCart(HttpSession session, ShoppingCart cart) { 
          session.setAttribute(CART_SESSION_KEY, cart);
    }

    @Override
    public void addToCart(HttpSession session, Long courseId) {
          Course course = courseService.findCourse(courseId);
        if (course != null) {
            addToCart(session, course);
        }
    }

    @Override
    public void addToCart(HttpSession session, Course course) { 
     ShoppingCart cart = getOrCreateCart(session);
        cart.addItem(course);
        saveCart(session, cart);
    }

    @Override
    public void removeFromCart(HttpSession session, Long courseId) {
        ShoppingCart cart = getOrCreateCart(session);
        cart.removeItem(courseId);
        saveCart(session, cart);
    }

    @Override
    public void updateCartItem(HttpSession session, Long courseId, int quantity) {
        if (quantity <= 0) {
            removeFromCart(session, courseId);
            return;
        }
      ShoppingCart cart = getOrCreateCart(session);
        cart.updateQuantity(courseId, quantity);
        saveCart(session, cart);
    }

    @Override
    public void clearCart(HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        cart.clear();
        saveCart(session, cart);
    }

    @Override
    public boolean applyDiscount(HttpSession session, String discountCode) {
        ShoppingCart cart = getCart(session);
        Optional<Discount> discountOpt = discountService.getDiscountByCode(discountCode);
        
        if (discountOpt.isPresent()) {
            Discount discount = discountOpt.get();
            if (discountService.validateAndApplyDiscount(discountCode)) {
                cart.applyDiscount(discount);
                return true;
            }
        }
        return false;
    }


    @Override
    public void removeDiscount(HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        cart.removeDiscount();
        saveCart(session, cart);
    }

    @Override
    public Double getDiscountAmount(HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        return cart.getDiscountAmount();
    }

    @Override
    public String getAppliedDiscountCode(HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        return cart.getDiscount() != null ? cart.getDiscount().getCode() : null;
    }

    @Override
    public boolean containsCourse(HttpSession session, Long courseId) {
        ShoppingCart cart = getCart(session);
        return cart != null && cart.containsCourse(courseId);
    }

    @Override
    public boolean isEmpty(HttpSession session) {
        ShoppingCart cart = getCart(session);
        return cart == null || cart.isEmpty();
    }

    @Override
    public int getCartItemCount(HttpSession session) {
        ShoppingCart cart = getCart(session);
        return cart != null ? cart.getItemCount() : 0;
    }

    @Override
    public int getTotalQuantity(HttpSession session) {
        ShoppingCart cart = getCart(session);
        return cart != null ? cart.getTotalQuantity() : 0;
    }

    @Override
    public double calculateSubtotal(HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        return cart.getSubtotal();
    }
    @Override
    public double calculateTax(HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        return cart.getTax();
    }
    @Override
    public double calculateTotal(HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        return cart.getTotal();
    }

    @Override
    public double calculateTotalWithDiscount(HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        return cart.getTotalWithDiscount();
    }
    @Override
    public void mergeCarts(HttpSession sourceSession, HttpSession targetSession) {
        ShoppingCart sourceCart = getCart(sourceSession);
        if (sourceCart == null || sourceCart.isEmpty()) {
            return;
        }

        ShoppingCart targetCart = getOrCreateCart(targetSession);
        
        // Merge ra item
        for (CartItem sourceItem : sourceCart.getCartItems()) {
            Optional<CartItem> existingItem = targetCart.findItem(sourceItem.getCourse().getId());
            
            if (existingItem.isPresent()) {
                existingItem.get().setQuantity(existingItem.get().getQuantity() + sourceItem.getQuantity());
            } else {
                targetCart.addItem(sourceItem.getCourse(), sourceItem.getQuantity());
            }
        }

        // Merge discount neu ko ra 
        if (targetCart.getDiscount() == null && sourceCart.getDiscount() != null) {
            targetCart.applyDiscount(sourceCart.getDiscount());
        }

        saveCart(targetSession, targetCart);
        clearCart(sourceSession);
    }

    @Override
    public ShoppingCart reloadCartPrices(HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        
        cart.getCartItems().forEach(item -> {
            Course updatedCourse = courseService.findCourse(item.getCourse().getId());
            if (updatedCourse != null) {
                item.decrementQuantity(updatedCourse.getPrice());
            }
        });
        
        cart.recalculateTotals();
        saveCart(session, cart);
        return cart;
    }
}
    

