package group5.swp391.onlinelearning.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Question;
import group5.swp391.onlinelearning.repository.QuestionRepository;
import group5.swp391.onlinelearning.service.IQuestionService;

@Service
public class QuestionService implements IQuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestionsByCourseId(int courseId) {
        return questionRepository.getQuestionsByCourseId(courseId);
    }

    @Override
    public Question getQuestionByCourseIdAndQuestionId(int courseId, int questionId) {
        Optional<Question> questionOp = questionRepository.getQuestionsByCourseId(questionId, courseId);
        if (questionOp.isPresent()) {
            return questionOp.get();
        }
        return null;
    }

}
