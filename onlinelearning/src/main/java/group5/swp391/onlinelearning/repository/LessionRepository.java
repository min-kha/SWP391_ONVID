package group5.swp391.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import group5.swp391.onlinelearning.entity.Lesson;

@Repository
public interface LessionRepository extends JpaRepository<Lesson, Integer> {
    @Query(value = "SELECT * FROM Lession where course_id = ?1", nativeQuery = true)
    public List<Lesson> findAllByCourseId(int courseId);
}
