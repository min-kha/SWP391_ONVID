package group5.swp391.onlinelearning.service;

import group5.swp391.onlinelearning.entity.AnswerChoice;

public interface IAnswerChoiceService {
    public AnswerChoice getAnswerChoiceByAnswer(String answerChoice, int questionId);

    public AnswerChoice getAnswerById(int answerId);

}
