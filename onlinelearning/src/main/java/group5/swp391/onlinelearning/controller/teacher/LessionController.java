package group5.swp391.onlinelearning.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.model.teacher.LessionDtoAdd;
import group5.swp391.onlinelearning.service.ILessonService;
import group5.swp391.onlinelearning.service.impl.CourseService;

@RequestMapping(value = "/teacher")
@Service

public class LessionController {

    @Autowired
    CourseService courseService;
    @Autowired
    ILessonService lessionService;

    @GetMapping("/create/lession/{courseid}")
    public String getCreateLession(@PathVariable Integer courseid, Model model) {
        if (courseid == null || courseService.isCourse(courseid)) {
            return "404";
        }
        if (!courseService.checkCourseOwner(courseid))
            return "AccessDenied";
        model.addAttribute("lession", new LessionDtoAdd());
        return "teacher/lession/add";
    }
}
