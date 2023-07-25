package group5.swp391.onlinelearning.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.AnswerChoice;
import group5.swp391.onlinelearning.repository.AnswerChoiceRepository;
import group5.swp391.onlinelearning.service.IAnswerChoiceService;

@Service
public class AnswerChoiceService implements IAnswerChoiceService {

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
