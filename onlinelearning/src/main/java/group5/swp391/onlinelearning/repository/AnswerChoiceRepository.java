package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.AnswerChoice;

public interface AnswerChoiceRepository extends JpaRepository<AnswerChoice, Integer> {
    @Query(value = "SELECT * FROM answer_choice WHERE answer = ?1 AND question_id = ?2", nativeQuery = true)
    public AnswerChoice getIdByAnswer(String answer, int questionId);
}