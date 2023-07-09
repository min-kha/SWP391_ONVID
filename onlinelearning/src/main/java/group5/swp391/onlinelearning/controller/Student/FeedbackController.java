package group5.swp391.onlinelearning.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class FeedbackController {

    @GetMapping("/course/feedback/{courseId}")
    public String getFeedbackForCourse(Model model, @PathVariable String courseId) {
        model.addAttribute("courseId", courseId);
        return "student/course/feedback";
    }
}
