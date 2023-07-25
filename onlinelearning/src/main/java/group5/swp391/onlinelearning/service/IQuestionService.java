package group5.swp391.onlinelearning.service;

import java.util.List;

import group5.swp391.onlinelearning.entity.Question;

public interface IQuestionService {
    public List<Question> getQuestionsByCourseId(int courseId);

    public Question getQuestionByCourseIdAndQuestionId(int courseId, int questionId);
}
