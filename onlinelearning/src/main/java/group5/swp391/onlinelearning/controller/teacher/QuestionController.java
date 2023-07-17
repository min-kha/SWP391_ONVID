package group5.swp391.onlinelearning.controller.teacher;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.Question;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.service.impl.CourseService;

@Controller
@RequestMapping("/teacher/question/")
public class QuestionController {
    @Autowired
    CourseService courseService;

    // @GetMapping("/lesson/create/{courseid}")
    // public String getCreateLesson(@PathVariable Integer courseid, Model model,
    // HttpSession req) {
    // // check role
    // User user = (User) req.getAttribute("userSession");
    // if (user.getRole() != 1)
    // return "AccessDenied";
    // // check valid course
    // if (courseid == null || !courseService.isCourse(courseid)) {
    // return "404";
    // }
    // if (!courseService.checkCourseOwner(courseid))
    // return "AccessDenied";
    // //
    // model.addAttribute("question", new Question());

    // return "/teacher/question/create";
    // }

    @GetMapping("/lesson/create/{courseid}")
    public String getCreateLesson(@PathVariable Integer courseid, Model model, HttpSession req) {
        Question question = new Question();
        model.addAttribute("quiz", question);

        return "teacher/question/testKha";
    }
}