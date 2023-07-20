package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        User student = User.builder().id(1).name("thai").email("leanthai02@gmail.com").build();

        Cart expectedCart = new Cart();
        when(cartRepository.save(any(Cart.class))).thenReturn(expectedCart);
        Cart createdCart = cartService.createCart(student);

        assertNotNull(createdCart);
        assertSame(expectedCart, createdCart);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testCreateCartInvalid() {
        User student = User.builder().id(2).name("thai").email("leanthai03@gmail.com").build();

        when(cartRepository.save(any(Cart.class))).thenReturn(null);
        Cart createdCart = cartService.createCart(student);

        assertNull(createdCart);
    }

    @Test
    void addCourseToCart() {
        Cart cart = new Cart();
        Course course = new Course();
        course.setName("New Course");
        course.setDescription("Course Description");
        course.setPrice(BigDecimal.valueOf(99.99));
        cart.setCourses(new ArrayList<>());

        cartService.addCourseToCart(cart, course);
        verify(cartRepository, times(1)).save(cart);
        assertEquals(1, cart.getCourses().size());
        assertTrue(cart.getCourses().contains(course));
    }

    @Test
    void getCartByStudentId() {
        int studentId = 1;
        User user = User.builder().id(studentId).build();
        Cart cart = Cart.builder().user(user).build();
        List<Cart> carts = new ArrayList<>();
        carts.add(cart);
        when(cartRepository.findAll()).thenReturn(carts);

        Cart cartResult = cartService.getCartByStudentId(studentId);

        verify(cartRepository, times(1)).findAll();
        assertEquals(cart, cartResult);
    }

    @Test
    void getCartByStudentIdCanNotFindCart() {
        int studentId = 1;
        User user = User.builder().id(studentId).build();
        Cart cart = Cart.builder().user(user).build();
        List<Cart> carts = new ArrayList<>();
        carts.add(cart);
        when(cartRepository.findAll()).thenReturn(carts);
        int nonExistentStudentId = 2;

        Cart nonExistentCart = cartService.getCartByStudentId(nonExistentStudentId);

        assertNull(nonExistentCart);
    }

    @Test
    void testGetCoursebyCartId() {
        int cartId = 123;
        Cart cart = new Cart();
        cart.setId(cartId);
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        Course course2 = new Course();
        courses.add(course1);
        courses.add(course2);
        cart.setCourses(courses);
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        List<Course> resultCourses = cartService.getCoursebyCartId(cartId);

        verify(cartRepository, times(1)).findById(cartId);
        assertEquals(courses, resultCourses);
    }

    @Test
    void testDeleteCourseInCartByCourseId() {
        int courseId = 123;
        int cartId = 456;
        Cart cart = new Cart();
        cart.setId(cartId);
        Course course1 = new Course();
        course1.setId(courseId);
        Course course2 = new Course();
        course2.setId(789);
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        cart.setCourses(courses);
        Optional<Cart> cartOptional = Optional.of(cart);
        when(cartRepository.findById(cartId)).thenReturn(cartOptional);

        cartService.deleteCourseInCartByCourseId(courseId, cartId);

        verify(cartRepository, times(1)).findById(cartId);
        verify(cartRepository, times(1)).save(cart);
        assertEquals(1, cart.getCourses().size());
        assertFalse(cart.getCourses().contains(course1));
        assertTrue(cart.getCourses().contains(course2));
    }
}
