package group5.swp391.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query(value = "SELECT Course.* FROM my_order JOIN order_detail ON my_order.id = order_detail.order_id and my_order.student_id = ?1 JOIN course ON course.id = order_detail.course_id group by course.id", nativeQuery = true)
    public List<Course> getMyCourse(int studentId);
}
