package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import group5.swp391.onlinelearning.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
