package group5.swp391.onlinelearning.model.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.model.teacher.LessonDtoAdd;
import group5.swp391.onlinelearning.service.impl.CourseService;

@Component
public class LessonMapper {
    @Autowired
    CourseService courseService;

    public Lesson lessionDtoAddtoLessonVideo(LessonDtoAdd lessonDtoAdd, String video) {
        Course course = courseService.getCourseById(lessonDtoAdd.getCourseId());
        Lesson lesson = Lesson.builder()
                .name(lessonDtoAdd.getName())
                .title(lessonDtoAdd.getTitle())
                .course(course)
                .document("")
                .video(video)
                .status(true)
                .build();
        return lesson;
    }

    public Lesson lessionDtoAddtoLessonDocument(LessonDtoAdd lessonDtoAdd, String document) {
        Course course = courseService.getCourseById(lessonDtoAdd.getCourseId());
        Lesson lesson = Lesson.builder()
                .name(lessonDtoAdd.getName())
                .title(lessonDtoAdd.getTitle())
                .course(course)
                .document(document)
                .status(true)
                .build();
        return lesson;
    }

    public LessonDtoAdd lessonToLessonDtoAdd(Lesson lesson) {
        LessonDtoAdd lessonDtoAdd = LessonDtoAdd.builder()
                .title(lesson.getTitle())
                .name(lesson.getName())
                .courseId(lesson.getCourse().getId())
                .build();
        return lessonDtoAdd;
    }

}
