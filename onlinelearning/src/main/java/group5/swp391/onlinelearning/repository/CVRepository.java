package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.CV;

public interface CVRepository extends JpaRepository<CV, Integer> {
    @Query(value = "SELECT * FROM cv WHERE teacher_id = ?1", nativeQuery = true)
    public CV getCVByTeacherId(int teacherId);
}
