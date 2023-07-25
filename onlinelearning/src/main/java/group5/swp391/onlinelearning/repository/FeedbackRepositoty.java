package group5.swp391.onlinelearning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.Feedback;

public interface FeedbackRepositoty extends JpaRepository<Feedback, Integer> {
    @Query(value = "SELECT * FROM feedback WHERE course_id = ?1 and student_id = ?2", nativeQuery = true)
    public Optional<Feedback> getFeedbackDtoRequest(int courseId, int studentId);

    @Modifying
    @Query(value = "UPDATE feedback SET comment = ?1, rating_star = ?2 WHERE course_id = ?3 and student_id = ?4", nativeQuery = true)
    public void updateFeedback(String comment, int ratingStar, int courseId, int studentId);

    @Query(value = "SELECT * FROM feedback WHERE course_id = ?1", nativeQuery = true)
    public List<Feedback> getFeedbackByCourseId(int courseId);

    @Query(value = "SELECT * FROM swp391_onvid.feedback left join swp391_onvid.course on feedback.course_id = course.id where teacher_id = ?1", nativeQuery = true)
    public List<Feedback> getFeedbackByTeacherId(int teacherId);
}
