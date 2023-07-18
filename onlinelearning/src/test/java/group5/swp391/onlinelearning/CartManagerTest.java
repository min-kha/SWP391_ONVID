package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import group5.swp391.onlinelearning.entity.Cart;
import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.repository.CartRepository;
import group5.swp391.onlinelearning.service.impl.CartService;

@SpringBootTest
public class CartManagerTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCart() {
        User student = new User(); // Tạo một đối tượng User giả định

        Cart expectedCart = new Cart(); // Tạo một đối tượng Cart giả định
        when(cartRepository.save(any(Cart.class))).thenReturn(expectedCart); // Giả lập phương thức save của
                                                                             // cartRepository
        Cart createdCart = cartService.createCart(student); // Gọi phương thức createCart

        assertNotNull(createdCart); // Kiểm tra xem cart được tạo ra không phải là null
        assertSame(expectedCart, createdCart); // Kiểm tra xem cart trả về có phải là cart giả định không
        verify(cartRepository, times(1)).save(any(Cart.class)); // Kiểm tra xem phương thức save đã được gọi đúng 1 lần
                                                                // với đối tượng cart
    }

    @Test
    void addCourseToCart() {
        Cart cart = new Cart();
        Course course = new Course();
        cart.setCourses(new ArrayList<>());

        // when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cartService.addCourseToCart(cart, course);
        verify(cartRepository, times(1)).save(cart);
        assertEquals(1, cart.getCourses().size());
        assertTrue(cart.getCourses().contains(course));
    }

    // @Test
    // void getCartByStudentId() {
    // int studentId = 1;
    // Cart cart = new Cart();
    // when(cartService.getCartByStudentId(studentId)).thenReturn(cart);

    // cartService.getCartByStudentId(studentId);
    // verify(cartRepository, times(1)).save(cart);
    // assertEquals(1, cart.getCourses().size());
    // assertTrue(cart.getCourses().contains(course));
    // }
}
