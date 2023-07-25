package group5.swp391.onlinelearning.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.repository.FeedbackRepositoty;
import group5.swp391.onlinelearning.service.IFeedbackServive;

@Service
public class FeedbackService implements IFeedbackServive {
    @Autowired
    HttpSession session;
    @Autowired
    FeedbackRepositoty feedbackRepository;

    @Override
    public Feedback createFeedback(Course course, int ratingStar, String comment) {
        User student = (User) session.getAttribute("studentSession");
        Feedback feedback = Feedback.builder().student(student).course(course).ratingStar(ratingStar).comment(comment)
                .build();
        return feedbackRepository.save(feedback);
    }

    @Override
    public Optional<Feedback> getFeedbackDtoRequest(int courseId, int studentId) {
        return feedbackRepository.getFeedbackDtoRequest(courseId, studentId);
    }

    @Override
    @Transactional
    public Optional<Feedback> updateFeedback(int courseId, int ratingStar, String comment) {
        User student = (User) session.getAttribute("studentSession");
        feedbackRepository.updateFeedback(comment, ratingStar, courseId, student.getId());
        return getFeedbackDtoRequest(courseId, student.getId());
    }

    @Override
    public List<Feedback> getFeedbackByCourseId(int courseId) {
        return feedbackRepository.getFeedbackByCourseId(courseId);
    }

    @Override
    public List<Feedback> getFeedbackByTeacherId(int teacherId) {
        return feedbackRepository.getFeedbackByTeacherId(teacherId);
    }
}
