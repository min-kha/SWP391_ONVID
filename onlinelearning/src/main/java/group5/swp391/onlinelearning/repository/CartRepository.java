package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import group5.swp391.onlinelearning.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    // @Transactional
    // @Modifying
    // @Query("DELETE FROM cart_detail WHERE cart_id = :cartId AND course_id =
    // :courseId")
    // void deleteCourseFromCart(int cartId, int courseId);
}
