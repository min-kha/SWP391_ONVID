package group5.swp391.onlinelearning.service;

import java.util.List;

import group5.swp391.onlinelearning.entity.Cart;
import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;

public interface ICartService {

    public Cart createCart(User student);

    public void addCourseToCart(Cart cart, Course course);

    public Cart getCartByStudentId(int studentId);

    public List<Course> getCoursebyCartId(int cartId);

    public void deleteCourseInCartByCourseId(int courseId, int cartId);

}
