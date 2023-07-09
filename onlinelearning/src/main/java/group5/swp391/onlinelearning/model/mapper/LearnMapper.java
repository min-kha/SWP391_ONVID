package group5.swp391.onlinelearning.model.mapper;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Learn;
import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.model.dto.LessonDtoDetail;
import group5.swp391.onlinelearning.model.teacher.CourseDTOTeacher;

public class LearnMapper {
    public static LessonDtoDetail lessonToLessonDtoDetail(Lesson lesson, Learn learn, int index) {
        if (learn.isStatus()) {
            LessonDtoDetail lessonDtoDetail = LessonDtoDetail.builder()
                    .id(lesson.getId()).status("done").index(index)
                    .build();
            return lessonDtoDetail;
        } else {
            LessonDtoDetail lessonDtoDetail = LessonDtoDetail.builder()
                    .id(lesson.getId()).status("learning").index(index)
                    .build();
            return lessonDtoDetail;
        }
    }
}
