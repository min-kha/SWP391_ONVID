package group5.swp391.onlinelearning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import group5.swp391.onlinelearning.entity.Cart;
import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.repository.CartRepository;
import group5.swp391.onlinelearning.service.ICartService;

@Service
public class CartService implements ICartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CourseService courseService;

    @Override
    @Transactional
    public Cart createCart(User student) {
        Cart cart = Cart.builder().user(student).courses(new ArrayList()).build();
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void addCourseToCart(Cart cart, Course course) {
        cart.getCourses().add(course);
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartByStudentId(int studentId) {
        List<Cart> carts = cartRepository.findAll();
        for (Cart c : carts) {
            if (c.getUser().getId() == studentId) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Course> getCoursebyCartId(int cartId) {
        return (List<Course>) cartRepository.findById(cartId).get().getCourses();
    }

    @Override
    public void deleteCourseInCartByCourseId(int courseId, int cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            List<Course> courses = (List<Course>) cart.getCourses();
            Course courseToRemove = getCourseByCourseId(courses, courseId);
            if (courseToRemove != null) {
                cart.removeCourse(courseToRemove);
                cartRepository.save(cart);
            }
        }
    }

    public Course getCourseByCourseId(List<Course> courses, int courseId) {
        for (Course c : courses) {
            if (c.getId() == courseId) {
                return c;
            }
        }
        return null;
    }
}
