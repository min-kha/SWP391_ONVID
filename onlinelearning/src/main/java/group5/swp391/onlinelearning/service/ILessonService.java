package group5.swp391.onlinelearning.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.model.teacher.LessonDtoAdd;
import group5.swp391.onlinelearning.model.teacher.LessonDtoEditVideo;

@Service
public interface ILessonService {
    public List<Lesson> getLessonsByCourseId(int courseId);

    public Lesson getLessonById(int lessonId);

    public Lesson addLessonVideo(@Valid LessonDtoAdd lessonDtoAdd, String fileName);

    public Lesson addLessonDocument(@Valid LessonDtoAdd lessonDtoAdd, String document);

    public boolean isLessonVideo(int lessonId);

    public boolean isLessonOfCourse(int lessonId, int courseId);

    public Lesson updateLesson(@Valid Lesson lesson);
}
