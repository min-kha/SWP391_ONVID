package group5.swp391.onlinelearning.controller.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import group5.swp391.onlinelearning.entity.AnswerChoice;
import group5.swp391.onlinelearning.entity.Question;
import group5.swp391.onlinelearning.service.IAnswerChoiceService;
import group5.swp391.onlinelearning.service.IQuestionService;
import group5.swp391.onlinelearning.utils.PagingUtils;

@Controller
@RequestMapping("/student")
public class DoTestController {

    @Autowired
    IQuestionService questionService;

    @Autowired
    IAnswerChoiceService answerChoiceService;

    @Autowired
    PagingUtils pagingUtils;

    @GetMapping("/course/test")
    public String getTestOfCourse(@RequestParam("courseId") int courseId,
            @RequestParam("questionChoose") String questionChoose, RedirectAttributes redirectAttributes,

            Model model) {
        List<Question> questions = questionService.getQuestionsByCourseId(courseId);
        int numberPerPage = 1;
        List<Question> questionOnPage = (List<Question>) pagingUtils.getPagingList(questionChoose,
                questions, numberPerPage);
        int numberOfPage = pagingUtils.getNumberOfPage(questions, numberPerPage);
        int questionChooseInt = Integer.parseInt(questionChoose);
        List<Integer> listQuestionNumber = pagingUtils.getListPageNumber(numberOfPage);

        model.addAttribute("question", questionOnPage);
        model.addAttribute("listQuestionNumber", listQuestionNumber);
        model.addAttribute("questionChoose", questionChooseInt);
        model.addAttribute("courseId", courseId);
        return "/student/test/question";
    }

    @GetMapping("/course/test/get-answer/")
    public String postMethodName(@RequestParam("questionId") int questionId,
            @RequestParam("questionChoose") String questionChoose, @RequestParam("answer") String answer,
            @RequestParam("courseId") int courseId, Model model, RedirectAttributes redirectAttributes) {
        AnswerChoice answerChoice = answerChoiceService.getAnswerChoiceByAnswer(answer, questionId);
        Question question = questionService.getQuestionByCourseIdAndQuestionId(courseId, questionId);
        AnswerChoice correctAnswer = answerChoiceService.getAnswerById(question.getAnswer().getId());
        boolean checkChooseCorrectAnswer = false;
        if (answerChoice.getId() == correctAnswer.getId()) {
            checkChooseCorrectAnswer = true;
        } else {
            checkChooseCorrectAnswer = false;
        }

        List<Question> questions = questionService.getQuestionsByCourseId(courseId);
        int numberPerPage = 1;
        List<Question> questionOnPage = (List<Question>) pagingUtils.getPagingList(questionChoose,
                questions, numberPerPage);
        int numberOfPage = pagingUtils.getNumberOfPage(questions, numberPerPage);
        int questionChooseInt = Integer.parseInt(questionChoose);
        List<Integer> listQuestionNumber = pagingUtils.getListPageNumber(numberOfPage);

        model.addAttribute("question", questionOnPage);
        model.addAttribute("answerChoice", answerChoice);
        model.addAttribute("checkChooseCorrectAnswer", checkChooseCorrectAnswer);
        model.addAttribute("correctAnswer", correctAnswer);
        model.addAttribute("listQuestionNumber", listQuestionNumber);
        model.addAttribute("questionChoose", questionChooseInt);
        model.addAttribute("courseId", courseId);
        return "/student/test/question";
    }

}
