package group5.swp391.onlinelearning.service;


import group5.swp391.onlinelearning.entity.AnswerChoice;
import java.util.List;

public interface IAnswerChoiceService {
    public AnswerChoice getAnswerChoiceByAnswer(String answerChoice, int questionId);
    public AnswerChoice getAnswerById(int answerId);
    AnswerChoice addAnswerChoice(AnswerChoice answerChoiceTrue);
    List<AnswerChoice> addListAnswerChoise(List<AnswerChoice> listOfAnswer);
    void deleteAnswer(List<AnswerChoice> listOfAnswerHistory);
    List<AnswerChoice> getListAnswerChoiseByQuestionId(int questionId);
}
