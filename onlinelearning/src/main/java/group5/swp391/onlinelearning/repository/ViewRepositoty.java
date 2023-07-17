package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import group5.swp391.onlinelearning.entity.View;

@Repository
public interface ViewRepositoty extends JpaRepository<View, Integer> {
    
}
