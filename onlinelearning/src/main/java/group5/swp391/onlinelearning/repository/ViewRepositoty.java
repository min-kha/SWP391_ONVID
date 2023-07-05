package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.entity.View;

public interface ViewRepositoty extends JpaRepository<View, Integer> {

}
