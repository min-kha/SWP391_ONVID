package group5.swp391.onlinelearning.service;

import java.util.Optional;

import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.model.dto.FeedbackDtoRequest;

public interface IFeedbackServive {
    public Feedback createFeedback(int courseId, int rateStar, String comment);

    public Optional<Feedback> updateFeedback(int courseId, int ratingStar, String comment);

    public Optional<Feedback> getFeedbackDtoRequest(int courseId, int studentId);
}
