package group5.swp391.onlinelearning.service.impl;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Question;

import group5.swp391.onlinelearning.repository.CourseRepository;

import group5.swp391.onlinelearning.repository.QuestionRepository;
import group5.swp391.onlinelearning.service.IQuestionService;

@Service
public class QuestionService implements IQuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestionsByCourseId() {
        // TODO Auto-generated method stub
        List<Question> questions = null;
        return questions;
    }

    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void remove(@NotNull int questionId) {
        questionRepository.deleteById(questionId);
    }

    @Override
    public Question getQuestionById(int questionId) {
        Question question = questionRepository.findById(questionId).get();
        return question;
    }

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestionsByCourseId(int courseId) {
        return questionRepository.getQuestionsByCourseId(courseId);
    }

}
