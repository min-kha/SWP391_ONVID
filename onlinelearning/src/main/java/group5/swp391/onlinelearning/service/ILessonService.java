package group5.swp391.onlinelearning.service;

import java.util.List;

import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Lesson;

@Service
public interface ILessonService {
    public List<Lesson> getLessonsByCourseId(int courseId);

    public Lesson getLessonById(int lessonId);

}
