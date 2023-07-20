package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import group5.swp391.onlinelearning.entity.View;

@Repository
public interface ViewRepositoty extends JpaRepository<View, Integer> {

    @Query(value = "SELECT * FROM view WHERE course_id = ?1 order by id desc limit 1", nativeQuery = true)
    public View getViewNumberByCourseId(int courseId);

}
