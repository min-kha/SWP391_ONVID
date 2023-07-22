package group5.swp391.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.CourseReview;

public interface CourseReviewRepository extends JpaRepository<CourseReview, Integer> {
    // Select top 5 course review
    @Query(value = "SELECT cr FROM swp391_onvid.course_review cr WHERE (cr.course_id, cr.time) IN (SELECT cr2.course_id, MAX(cr2.time) FROM CourseReview cr2 GROUP BY cr2.course_id)", nativeQuery = true)
    public List<CourseReview> findDistinctByMaxTime();
}
