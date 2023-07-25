package group5.swp391.onlinelearning.service;

import java.util.List;

import group5.swp391.onlinelearning.entity.AnswerChoice;

public interface IAnswerChoiceService {

    AnswerChoice addAnswerChoice(AnswerChoice answerChoiceTrue);

    List<AnswerChoice> addListAnswerChoise(List<AnswerChoice> listOfAnswer);

    void deleteAnswer(List<AnswerChoice> listOfAnswerHistory);

    List<AnswerChoice> getListAnswerChoiseByQuestionId(int questionId);

}
