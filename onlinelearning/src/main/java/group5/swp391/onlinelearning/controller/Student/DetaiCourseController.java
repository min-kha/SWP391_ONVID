package group5.swp391.onlinelearning.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DetaiCourseController {
    @GetMapping("detail")
    public String detail() {
        return "Detail";
    }
}
