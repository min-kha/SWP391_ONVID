package group5.swp391.onlinelearning.service;

import group5.swp391.onlinelearning.entity.Feedback;

public interface IFeedbackServive {
    public Feedback createFeedback(int courseId, int rateStar, String comment);
}
