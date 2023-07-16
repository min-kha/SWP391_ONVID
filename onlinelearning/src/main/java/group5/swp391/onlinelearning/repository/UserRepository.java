package group5.swp391.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.MyOrder;
import group5.swp391.onlinelearning.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query(value = "SELECT * FROM user where email = ?1", nativeQuery = true)
    public User login(String email);
}
