package group5.swp391.onlinelearning.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.model.mapper.LessonMapper;
import group5.swp391.onlinelearning.model.teacher.LessonDtoAdd;
import group5.swp391.onlinelearning.repository.LessonRepository;
import group5.swp391.onlinelearning.service.ILessonService;

@Service
public class LessonService implements ILessonService {
    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    LessonMapper lessonMapper;

    @Override
    public List<Lesson> getLessonsByCourseId(int courseId) {
        List<Lesson> list = lessonRepository.findAllByCourseId(courseId);
        return list;
    }

    @Override
    public Lesson getLessonById(int lessonId) {
        return lessonRepository.findById(lessonId).get();
    }

    @Override
    public Lesson addLessonVideo(@Valid LessonDtoAdd lessonDtoAdd, String fileName) {
        Lesson lesson = lessonMapper.lessionDtoAddtoLessonVideo(lessonDtoAdd, fileName);
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson addLessonDocument(@Valid LessonDtoAdd lessonDtoAdd, String document) {
        Lesson lesson = lessonMapper.lessionDtoAddtoLessonDocument(lessonDtoAdd, document);
        return lessonRepository.save(lesson);
    }

}