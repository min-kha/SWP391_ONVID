package group5.swp391.onlinelearning.service.impl;


import java.util.Optional;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import group5.swp391.onlinelearning.entity.AnswerChoice;
import group5.swp391.onlinelearning.repository.AnswerChoiceRepository;
import group5.swp391.onlinelearning.service.IAnswerChoiceService;

@Service
public class AnswerChoiceService implements IAnswerChoiceService {
    @Autowired
    private AnswerChoiceRepository answerChoiceRepository;

    @Override
    public AnswerChoice addAnswerChoice(AnswerChoice answerChoiceTrue) {
        return answerChoiceRepository.save(answerChoiceTrue);
    }

    @Override
    public List<AnswerChoice> addListAnswerChoise(List<AnswerChoice> listOfAnswer) {
        List<AnswerChoice> list = new ArrayList<AnswerChoice>();
        for (AnswerChoice answerChoice : listOfAnswer) {
            AnswerChoice answerChoice2 = answerChoiceRepository.save(answerChoice);
            list.add(answerChoice2);
        }
        return list;
    }

    @Override
    public void deleteAnswer(List<AnswerChoice> listOfAnswerHistory) {
        for (int i = 0; i < listOfAnswerHistory.size(); i++) {
            int id = listOfAnswerHistory.get(i).getId();
            AnswerChoice answerChoice = answerChoiceRepository.findById(id).get();
            answerChoice.setQuestion(null);
            answerChoiceRepository.save(answerChoice);
            answerChoiceRepository.deleteById(id);
        }
    }

    @Override
    public List<AnswerChoice> getListAnswerChoiseByQuestionId(int questionId) {
        List<AnswerChoice> list = answerChoiceRepository.getAsByQuestionId(questionId);
        return list;
    }

    @Autowired
    AnswerChoiceRepository answerChoiceRepository;

    public AnswerChoice getAnswerChoiceByAnswer(String answer, int questionId) {
        return answerChoiceRepository.getIdByAnswer(answer, questionId);
    }

    @Override
    public AnswerChoice getAnswerById(int answerId) {
        Optional<AnswerChoice> answerChoice = answerChoiceRepository.findById(answerId);
        if (answerChoice.isPresent()) {
            return answerChoice.get();
        }
        return null;
    }
}
