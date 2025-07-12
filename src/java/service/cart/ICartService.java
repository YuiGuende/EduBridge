package service.cart;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.DTO.CartDTO;
import model.DTO.CartItemDTO;
import model.course.Course;

public interface ICartService {
    
    void addToCart(Long courseId, HttpSession session);
    
    void removeFromCart(Long courseId, HttpSession session);

    void clearCart(HttpSession session);

    List<CartItemDTO> getCartItemsFromSession(HttpSession session);

    CartDTO getCartDTO(HttpSession session);

    public CartItemDTO convertToCartItemDTO(Course course);
}
