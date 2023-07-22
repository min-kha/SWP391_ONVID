package group5.swp391.onlinelearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.Learn;

public interface LearnRepository extends JpaRepository<Learn, Integer> {
    @Query(value = "SELECT * FROM learn WHERE lesson_id = ?1 and student_id = ?2", nativeQuery = true)
    public Learn getListLearnByLessonIdAndStudentId(int lessonId, int studentId);

    @Modifying
    @Query(value = "UPDATE learn SET status = ?1 WHERE lesson_id = ?2 and student_id = ?3", nativeQuery = true)
    public void changeLearnStatus(boolean status, int lessonId, int studentId);

}
