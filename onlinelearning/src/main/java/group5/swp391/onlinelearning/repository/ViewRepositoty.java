package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.entity.View;

@Repository
public interface ViewRepositoty extends JpaRepository<View, Integer> {

    @Query(value = "SELECT * FROM view WHERE course_id = ?1 order by id desc limit 1", nativeQuery = true)
    public View getViewNumberByCourseId(int courseId);

    @Query(value = "SELECT SUM(max_view) AS total_max_view \n" + //
            "FROM (\n" + //
            "    SELECT MAX(view_number) AS max_view\n" + //
            "    FROM swp391_onvid.view\n" + //
            "    LEFT JOIN swp391_onvid.course ON view.course_id = course.id\n" + //
            "    WHERE course.teacher_id = ?\n" + //
            "    GROUP BY course_id\n" + //
            ") AS subquery;", nativeQuery = true)
    public Long getViewNumberByTeacherIdInToday(int teacherId);

    @Query(value = "SELECT SUM(max_view) AS total_max_view \n" + //
            "FROM (\n" + //
            "    SELECT MAX(view_number) AS max_view\n" + //
            "    FROM swp391_onvid.view\n" + //
            "    LEFT JOIN swp391_onvid.course ON view.course_id = course.id\n" + //
            "    WHERE course.teacher_id = 70 and DATE(view.date) = DATE_SUB(CURDATE(), INTERVAL 1 DAY)\n" + //
            "    GROUP BY course_id\n" + //
            ") AS subquery;", nativeQuery = true)
    public Long getViewNumberByTeacherIdInYesterday(int teacherId);

}
