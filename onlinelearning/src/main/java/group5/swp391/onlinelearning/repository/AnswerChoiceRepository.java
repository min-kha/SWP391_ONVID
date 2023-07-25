
package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.AnswerChoice;

public interface AnswerChoiceRepository extends JpaRepository<AnswerChoice, Integer> {
    @Query(value = "SELECT * FROM answer_choice WHERE answer = ?1 AND question_id = ?2", nativeQuery = true)
    public AnswerChoice getIdByAnswer(String answer, int questionId);

package group5.swp391.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.AnswerChoice;

public interface AnswerChoiceRepository extends JpaRepository<AnswerChoice, Integer> {
    @Modifying
    @Query(value = "DELETE FROM swp391_onvid.answer_choice WHERE id = ?1", nativeQuery = true)
    public void deleteById(int id);

    @Query(value = "Select * from swp391_onvid.answer_choice WHERE question_id = ?1", nativeQuery = true)
    public List<AnswerChoice> getAsByQuestionId(int id);


}