package group5.swp391.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query(value = "SELECT * FROM course where teacher_id = ?1", nativeQuery = true)
    public List<Course> findAllByTeacherId(int teacherId);
}
