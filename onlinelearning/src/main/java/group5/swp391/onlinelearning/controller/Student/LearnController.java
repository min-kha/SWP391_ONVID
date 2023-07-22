package group5.swp391.onlinelearning.controller.Student;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.service.ILearnService;

@Controller
@RequestMapping("/student")
public class LearnController {
    @Autowired
    ILearnService learnService;

    @PostMapping("/set-learn-default")
    public String setLearnDefault(HttpSession session) {
        List<Course> courses = (List<Course>) session.getAttribute("cartStudentSession");
        User student = (User) session.getAttribute("studentSession");
        learnService.setLearnDefault(courses, student);
        return "forward:/student/cart/pay";
    }

    @PostMapping("/course/lesson/change-learn-status")
    public String changeLearnStatus(HttpSession session, @RequestParam("lessonId") String lessonId,
            @RequestParam("courseId") String courseId) {

        int lessonIdInt = Integer.parseInt(lessonId);
        int courseIdInt = Integer.parseInt(courseId);
        User student = (User) session.getAttribute("studentSession");
        learnService.changeLearnStatus(true, lessonIdInt, student.getId());
        return "redirect:/student/course/lesson/" + courseIdInt + "/" + lessonIdInt;
    }

}
