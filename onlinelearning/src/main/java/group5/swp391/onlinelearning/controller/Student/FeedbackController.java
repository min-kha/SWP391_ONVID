package group5.swp391.onlinelearning.controller.student;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.model.dto.FeedbackDtoRequest;
import group5.swp391.onlinelearning.service.IFeedbackServive;
import group5.swp391.onlinelearning.service.impl.CourseService;

@Controller
@RequestMapping("/student")
public class FeedbackController {
    @Autowired
    CourseService courseService;

    @Autowired
    IFeedbackServive feedbackServive;

    @GetMapping("/course/feedback/{courseId}")
    public String getFeedbackForm(Model model, @PathVariable String courseId,
            @ModelAttribute("feedback") FeedbackDtoRequest feedback) {
        int courseIdInt = Integer.parseInt(courseId);
        Course course = courseService.getCourseById(courseIdInt);
        model.addAttribute("course", course);
        model.addAttribute("courseId", courseId);
        // if (model.getAttribute("feedback") == null) {
        // model.addAttribute("feedback", new FeedbackDtoRequest());
        // }

        return "student/course/feedback";
    }

    @PostMapping(value = "/course/feedback/{courseId}")
    public String createFeedback(@PathVariable String courseId,
            @Valid @ModelAttribute("feedback") FeedbackDtoRequest feedback, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            int courseIdInt = Integer.parseInt(courseId);
            Course course = courseService.getCourseById(courseIdInt);
            model.addAttribute("course", course);
            model.addAttribute("courseId", courseId);
            return "student/course/feedback/";
        }
        Feedback feedbackRes = feedbackServive.createFeedback(Integer.parseInt(courseId), feedback.getRatingStar(),
                feedback.getComment());
        model.addAttribute("feedback", feedbackRes);
        return "redirect:/student/course/feedback/" + courseId;
    }
}
