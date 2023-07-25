package group5.swp391.onlinelearning.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
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

    @Autowired
    CourseService courseService;

    @Override
    public List<Lesson> getLessonsByCourseId(int courseId) {
        List<Lesson> list = lessonRepository.findAllByCourseId(courseId);
        return list;
    }

    @Override
    public Lesson getLessonById(int lessonId) {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (lesson.isPresent()) {
            return lesson.get();
        } else {
            return null;
        }
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

    @Override
    public boolean isLessonVideo(int lessonId) {
        Lesson lesson = getLessonById(lessonId);
        if (lesson != null) {
            if (lesson.getVideo() == null || lesson.getVideo().equals("")) {
                return false;
            }
            return true;
        } else {
            return false;

        }
    }

    @Override
    public boolean isLessonOfCourse(int lessonId, int courseId) {
        Lesson lesson = getLessonById(lessonId);
        if (lesson != null) {
            if (lesson.getCourse().getId() == courseId) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public Lesson updateLesson(@Valid Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson deleteLessonById(Integer lessonId) {
        Lesson lesson = getLessonById(lessonId);
        lesson.setStatus(false);
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson deleteLessonByIdOfTeacher(Integer lessonId, Integer courseId) {
        Course course = courseService.getCourseById(courseId);
        // check published course, submited, progeressing
        if (course.getStatus() != 3 || course.getStatus() != 0 || course.getStatus() != 1) {
            return deleteLessonById(lessonId);
        }
        return null;
    }

}
