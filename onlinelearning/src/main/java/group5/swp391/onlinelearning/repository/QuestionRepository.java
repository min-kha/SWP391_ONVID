package group5.swp391.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import group5.swp391.onlinelearning.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query(value = "SELECT * FROM question WHERE course_id = ?1", nativeQuery = true)
    public List<Question> getQuestionsByCourseId(int courseId);
}
