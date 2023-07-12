package group5.swp391.onlinelearning.model.mapper;

import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.model.dto.FeedbackDtoRequest;

public class FeedbackMapper {
    public static FeedbackDtoRequest feedbackToFeedbackDtoRequest(Feedback feedback) {
        FeedbackDtoRequest feedbackDtoRequest = FeedbackDtoRequest.builder()
                .comment(feedback.getComment()).ratingStar(feedback.getRatingStar())
                .build();
        return feedbackDtoRequest;
    }
}
