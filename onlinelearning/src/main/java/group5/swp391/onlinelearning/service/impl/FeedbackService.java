package group5.swp391.onlinelearning.service.impl;

import javax.servlet.http.HttpSession;

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
    CourseService coursesService;
    @Autowired
    FeedbackRepositoty feedbackRepository;

    @Override
    public Feedback createFeedback(int courseId, int ratingStar, String comment) {
        User student = (User) session.getAttribute("studentSession");
        Course course = coursesService.getCourseByCourseId(courseId);
        Feedback feedback = Feedback.builder().student(student).course(course).ratingStar(ratingStar).comment(comment)
                .build();
        return feedbackRepository.save(feedback);
    }

}
