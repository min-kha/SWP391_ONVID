package group5.swp391.onlinelearning.controller.student;

import java.util.Optional;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.FeedbackDtoRequest;
import group5.swp391.onlinelearning.model.dto.UserDTOLoginRequest;
import group5.swp391.onlinelearning.model.mapper.FeedbackMapper;
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
            RedirectAttributes redirectAttributes, HttpSession session) {
        int courseIdInt = Integer.parseInt(courseId);
        Course course = courseService.getCourseById(courseIdInt);
        model.addAttribute("course", course);
        model.addAttribute("courseId", courseId);
        User student = (User) session.getAttribute("studentSession");
        int studentId = student.getId();
        Optional<Feedback> feedbackRes = feedbackServive.getFeedbackDtoRequest(courseIdInt, studentId);
        if (feedbackRes.isPresent()) {
            model.addAttribute("feedback", feedbackRes.get());
            model.addAttribute("isUpdate", true);
        }
        if (model.getAttribute("feedback") == null) {
            model.addAttribute("feedback", new FeedbackDtoRequest());
            model.addAttribute("isUpdate", false);
        }
        return "student/course/feedback";
    }

    @PostMapping(value = "/course/feedback/{courseId}")
    public String createFeedback(@Valid @ModelAttribute("feedback") FeedbackDtoRequest feedback,
            BindingResult bindingResult, @PathVariable String courseId,
            Model model, RedirectAttributes redirectAttributes) {
        int courseIdInt = Integer.parseInt(courseId);
        Course course = courseService.getCourseById(courseIdInt);
        model.addAttribute("course", course);
        model.addAttribute("courseId", courseId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("isUpdate", false);
            return "student/course/feedback";
        }

        Course courseRes = courseService.getCourseByCourseId(Integer.parseInt(courseId));
        Feedback feedbackRes = feedbackServive.createFeedback(courseRes, feedback.getRatingStar(),
                feedback.getComment());
        FeedbackDtoRequest feedbackDtoRequest = FeedbackMapper.feedbackToFeedbackDtoRequest(feedbackRes);
        redirectAttributes.addFlashAttribute("feedback", feedbackDtoRequest);
        model.addAttribute("isUpdate", true);
        return "student/course/feedback";
    }

    @PostMapping(value = "/course/feedback/update/{courseId}")
    public String updateFeedback(@Valid @ModelAttribute("feedback") FeedbackDtoRequest feedback,
            BindingResult bindingResult, @PathVariable String courseId,
            Model model, RedirectAttributes redirectAttributes) {
        int courseIdInt = Integer.parseInt(courseId);
        Course course = courseService.getCourseById(courseIdInt);
        model.addAttribute("course", course);
        model.addAttribute("courseId", courseId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("isUpdate", true);
            return "student/course/feedback";
        }
        Optional<Feedback> feedbackRes = feedbackServive
                .updateFeedback(Integer.parseInt(courseId), feedback.getRatingStar(),
                        feedback.getComment());
        if (feedbackRes.isPresent()) {
            redirectAttributes.addFlashAttribute("feedback", feedbackRes.get());
            model.addAttribute("isUpdate", true);
        }
        return "student/course/feedback";
    }
}
