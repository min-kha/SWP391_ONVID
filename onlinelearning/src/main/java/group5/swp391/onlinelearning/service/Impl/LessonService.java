package group5.swp391.onlinelearning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.repository.LessonRepository;
import group5.swp391.onlinelearning.service.ILessonService;

@Service
public class LessonService implements ILessonService {
    @Autowired
    LessonRepository lessonRepository;

    @Override
    public List<Lesson> getLessonsByCourseId(int courseId) {
        List<Lesson> list = lessonRepository.findAllByCourseId(courseId);
        return list;
    }

    @Override
    public Lesson getLessonById(int lessonId) {
        return lessonRepository.findById(lessonId).get();
    }

}
