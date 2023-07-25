package group5.swp391.onlinelearning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query(value = "SELECT * FROM question WHERE course_id = ?1", nativeQuery = true)
    public List<Question> getQuestionsByCourseId(int courseId);

    @Query(value = "SELECT * FROM question WHERE id = ?1 AND course_id = ?2", nativeQuery = true)
    public Optional<Question> getQuestionsByCourseId(int questionId, int courseId);
}
