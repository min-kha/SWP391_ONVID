package group5.swp391.onlinelearning.service;

import java.util.List;
import java.util.Optional;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Feedback;

public interface IFeedbackServive {
    public Feedback createFeedback(Course course, int rateStar, String comment);

    public Optional<Feedback> updateFeedback(int courseId, int ratingStar, String comment);

    public Optional<Feedback> getFeedbackDtoRequest(int courseId, int studentId);

    public List<Feedback> getFeedbackByCourseId(int courseId);

    public List<Feedback> getFeedbackByTeacherId(int teacherId);
}
