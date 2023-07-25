package group5.swp391.onlinelearning.service;

import java.util.List;
import javax.validation.constraints.NotNull;

import group5.swp391.onlinelearning.entity.Question;

public interface IQuestionService {
    public List<Question> getQuestionsByCourseId();

    public Question addQuestion(Question question);

    public Question updateQuestion(Question question);

    public void remove(@NotNull int questionId);

    public Question getQuestionById(int questionId);

    public List<Question> getQuestionsByCourseId(int courseId);

    public Question getQuestionByCourseIdAndQuestionId(int courseId, int questionId);
}
