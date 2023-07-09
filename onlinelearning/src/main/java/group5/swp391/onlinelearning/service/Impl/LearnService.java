package group5.swp391.onlinelearning.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Learn;
import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.repository.LearnRepository;
import group5.swp391.onlinelearning.service.ILearnService;
import group5.swp391.onlinelearning.service.ILessonService;

@Service
public class LearnService implements ILearnService {
    @Autowired
    LearnRepository learnRepository;

    @Autowired
    ILessonService lessonService;

    @Override
    public void setLearnDefault(List<Course> courses, User student) {
        for (Course course : courses) {
            List<Lesson> lessons = lessonService.getLessonsByCourseId(course.getId());
            for (Lesson lesson : lessons) {
                Learn learn = Learn.builder().lesson(lesson).student(student).status(false).build();
                learnRepository.save(learn);
            }
        }
    }

    @Override
    public List<Learn> getListLearnByLessonIdAndStudentId(List<Lesson> lessons, User student) {
        List<Learn> learns = new ArrayList<>();
        for (Lesson lesson : lessons) {
            Learn l = learnRepository.getListLearnByLessonIdAndStudentId(lesson.getId(), student.getId());
            learns.add(l);
        }
        return learns;
    }
}
