package service.cart;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.DTO.CartDTO;
import model.DTO.CartItemDTO;
import model.DTO.DiscountDTO;
import model.course.Course;
import service.course.CourseService;
import service.course.CourseServiceImpl;

public class CartService implements ICartService {

    private final CourseService courseService = new CourseServiceImpl();

    @Override
    public CartItemDTO convertToCartItemDTO(Course course) {
        return new CartItemDTO(
                course.getId(),
                course.getTitle(),
                course.getThumbnailUrl(),
                course.getPrice(),
                course.getDiscountPrice()
        );
    }

    @Override
    public List<CartItemDTO> getCartItemsFromSession(HttpSession session) {
        List<CartItemDTO> cart = (List<CartItemDTO>) session.getAttribute("cart");
        return cart == null ? new ArrayList<>() : cart;
    }

    @Override
    public void addToCart(Long courseId, HttpSession session) {
        Course course = courseService.findCourse(courseId);
        System.out.println("course name"+course.getTitle());
        List<CartItemDTO> cart = getCartItemsFromSession(session);
        boolean exists = cart.stream().anyMatch(item -> item.getCourseId().equals(courseId));
        if (!exists) {
            cart.add(convertToCartItemDTO(course));
        }
        session.setAttribute("cart", cart);
    }

    @Override
    public void removeFromCart(Long courseId, HttpSession session) {
        List<CartItemDTO> cart = getCartItemsFromSession(session);
        cart = cart.stream()
                .filter(item -> !item.getCourseId().equals(courseId))
                .collect(Collectors.toList());
        session.setAttribute("cart", cart);
    }

    @Override
    public void clearCart(HttpSession session) {
        session.setAttribute("cart", new ArrayList<>());
    }

    @Override
    public CartDTO getCartDTO(HttpSession session) {
        List<CartItemDTO> items = getCartItemsFromSession(session);
       
        // Tính subtotal (trước thuế, chưa discount)
        double subtotal = items.stream()
                .mapToDouble(item -> item.getDiscountedPrice() != 0 ? item.getDiscountedPrice() : item.getOriginalPrice())
                .sum();

        // Tính thuế giả lập: 10% + instructor bonus 5% = 15%
        double tax = subtotal * 0.15;

        // Giảm giá nếu có (giả lập đọc từ session)
        DiscountDTO discount = (DiscountDTO) session.getAttribute("discount"); // có thể null
        double discountAmount = 0.0;

        if (discount != null) {
            // giả lập mã nào cũng giảm 20% subtotal
            discountAmount = subtotal * 0.2;
        }

        // Tổng sau giảm giá
        double totalWithDiscount = subtotal + tax - discountAmount;

        // Trả về DTO mới
        CartDTO dto = new CartDTO();
        dto.setCartItems(items);
        dto.setSubtotal(round(subtotal));
        dto.setTax(round(tax));
        dto.setDiscount(discount);
        dto.setDiscountAmount(round(discountAmount));
        dto.setTotalWithDiscount(round(totalWithDiscount));

        return dto;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
