package group5.swp391.onlinelearning.controller.teacher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group5.swp391.onlinelearning.entity.AnswerChoice;
import group5.swp391.onlinelearning.entity.Question;
import group5.swp391.onlinelearning.repository.QuestionRepository;

@Controller
@RequestMapping("/teacher/question")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    // @Autowired
    // private AnswerChoiceRepository answerChoiceRepository;

    @GetMapping("/add")
    public String showAddQuestionForm(Model model) {
        List<AnswerChoice> answerChoices = new ArrayList<>();
        model.addAttribute("answerChoices", answerChoices);
        model.addAttribute("question", new Question());
        List<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);

        return "teacher/question/test";
    }

    @PostMapping("/add")
    public String addQuestion(@ModelAttribute("question") Question question,
            @RequestParam("answers") List<String> answerChoices,
            @RequestParam("correctRadio") int correctAnswerIndex) {

        // List<AnswerChoice> choices = new ArrayList<>();
        // for (int i = 0; i < answerChoices.size(); i++) {
        // String answerText = answerChoices.get(i);
        // boolean isCorrect = (i == correctAnswerIndex - 1);
        // AnswerChoice answerChoice = new AnswerChoice(answerText, isCorrect,
        // question);
        // choices.add(answerChoice);
        // }

        // question.setAnswerChoices(choices);
        // questionRepository.save(question);

        return "redirect:/questions/list";
    }

    @GetMapping("/list")
    public String showQuestionList(Model model) {
        List<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return "question_list";
    }
}