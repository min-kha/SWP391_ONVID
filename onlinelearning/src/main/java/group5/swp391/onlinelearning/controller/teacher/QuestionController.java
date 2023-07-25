package group5.swp391.onlinelearning.controller.teacher;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.AnswerChoice;
import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Question;
import group5.swp391.onlinelearning.service.IAnswerChoiceService;
import group5.swp391.onlinelearning.service.IQuestionService;
import group5.swp391.onlinelearning.service.impl.CourseService;

@Controller
@RequestMapping("/teacher/question")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IAnswerChoiceService answerChoiceService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/create/{courseId}")
    public String showAddQuestionForm(Model model, @PathVariable @NotNull int courseId, HttpSession session) {
        List<AnswerChoice> answerChoices = new ArrayList<>();
        Course course = courseService.getCourseByCourseId(courseId);
        model.addAttribute("course", course);
        return "/teacher/question/create";
    }

    @PostMapping("/create")
    public String addQuestion(HttpServletRequest request) {
        Question questionDTO = new Question();
        if (request.getParameter("question") != null) {
            questionDTO.setName(request.getParameter("question"));
        }
        // set course
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        questionDTO.setCourse(courseService.getCourseById(courseId));
        // save question
        Question question = questionService.addQuestion(questionDTO);

        int numberOfAnswer = 0;
        if (request.getParameter("numberOfAnswerInput") != null) {
            numberOfAnswer = Integer.parseInt(request.getParameter("numberOfAnswerInput"));
        }
        AnswerChoice answerChoiceTrue = new AnswerChoice();
        List<AnswerChoice> listOfAnswer = new ArrayList<AnswerChoice>();
        for (int i = 1; i <= numberOfAnswer; i++) {
            AnswerChoice answerChoice = new AnswerChoice();

            if (request.getParameter("answer" + i) != null) {
                answerChoice.setAnswer(request.getParameter("answer" + i));
                listOfAnswer.add(answerChoice);
            }

            answerChoice.setQuestion(question);
        }

        int x = Integer.parseInt(request.getParameter("correctRadio"));

        // save list answerchoice
        List<AnswerChoice> listOfAnswerSave = answerChoiceService.addListAnswerChoise(listOfAnswer);
        // assign answerChoiceTrue1 to question
        question.setAnswer(listOfAnswerSave.get(x - 1));
        // update question
        questionService.updateQuestion(question);
        return "redirect:/teacher/question/create/" + courseId;
    }

    @GetMapping("/delete/{courseId}/{questionId}")
    public String getDeleteQuestion(Model model, @PathVariable @NotNull int questionId, HttpSession session,
            @PathVariable @NotNull int courseId) {
        questionService.remove(questionId);
        return "redirect:/teacher/question/create/" + courseId;
    }

    @GetMapping("/detail/{courseId}/{questionId}")
    public String getDetailQuestion(Model model, @PathVariable @NotNull int questionId, HttpSession session,
            @PathVariable @NotNull int courseId) {
        Course course = courseService.getCourseByCourseId(courseId);
        Question question = questionService.getQuestionById(questionId);
        model.addAttribute("question", question);
        model.addAttribute("course", course);
        return "/teacher/question/detail";
    }

    @PostMapping("/update")
    public String postUpdateQuestion(HttpServletRequest request) {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        Question questionOrigin = questionService.getQuestionById(questionId);
        if (request.getParameter("question") != null) {
            questionOrigin.setName(request.getParameter("question"));
        }
        List<AnswerChoice> listOfAnswerHistory = answerChoiceService.getListAnswerChoiseByQuestionId(questionId);
        int numberOfAnswer = 0;
        if (request.getParameter("numberOfAnswerInput") != null) {
            numberOfAnswer = Integer.parseInt(request.getParameter("numberOfAnswerInput"));
        }
        AnswerChoice answerChoiceTrue = new AnswerChoice();
        List<AnswerChoice> listOfAnswer = new ArrayList<AnswerChoice>();
        for (int i = 1; i <= numberOfAnswer; i++) {
            AnswerChoice answerChoice = new AnswerChoice();

            if (request.getParameter("answer" + i) != null) {
                answerChoice.setAnswer(request.getParameter("answer" + i));
                listOfAnswer.add(answerChoice);
            }

            answerChoice.setQuestion(questionOrigin);
        }

        int x = Integer.parseInt(request.getParameter("correctRadio"));
        // save list answerchoice
        List<AnswerChoice> list = answerChoiceService.addListAnswerChoise(listOfAnswer);
        // assign answerChoiceTrue1 to question
        questionOrigin.setAnswer(list.get(x - 1));

        // update question
        questionService.updateQuestion(questionOrigin);
        // delete all answer old
        answerChoiceService.deleteAnswer(listOfAnswerHistory);

        return "redirect:/teacher/question/create/" + courseId;
    }

}