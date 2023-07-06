package group5.swp391.onlinelearning.controller.teacher;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import group5.swp391.onlinelearning.model.dto.CourseDtoDetail;
import group5.swp391.onlinelearning.model.teacher.CourseDTOAdd;
import group5.swp391.onlinelearning.model.teacher.CourseDTOTeacher;
import group5.swp391.onlinelearning.service.ICourseTeacherService;
import group5.swp391.onlinelearning.service.Impl.CourseService;
import group5.swp391.onlinelearning.service.admin.ITopicService;

@RequestMapping("/teacher/course")
@Controller
public class CourseController {
    // CRUD
    @Autowired
    CourseService courseService;
    @Autowired
    ICourseTeacherService courseTeacherService;
    @Autowired
    ITopicService topicService;

    @GetMapping("/detail/{id}")
    public String getCourse(Model model, @PathVariable @NotNull Integer id) {
        CourseDtoDetail course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "course/detail";
    }

    @GetMapping("/list")
    public String getCourseList(Model model) {
        List<CourseDTOTeacher> courses = courseTeacherService.getCourseDTOTeacherList();
        model.addAttribute("courses", courses);
        return "teacher/teacher-course";
    }

    @GetMapping("/create")
    public String getCreateCourse(Model model) {
        model.addAttribute("course", new CourseDTOAdd());
        // TODO: chèn tạm Topic vì chưa có topic CRUD
        model.addAttribute("topics", topicService.getTopics());
        return "teacher/teacher-course-add";
    }

    @PostMapping("/create")
    public String postCreateCourse(@ModelAttribute Course course) {
        courseService.createCourse(course);
        return "teacher/teacher-course-add";
    }

    @GetMapping("/update/{id}")
    public String getUpdateCourse(Model model, @PathVariable @NotNull Integer id) {
        model.addAttribute("course", courseService.getCourseById(id));
        // TODO: chèn tạm Topic vì chưa có topic CRUD
        model.addAttribute("topics", topicService.getTopics());
        return "course/update";
    }

    @PostMapping("/update/{id}")
    public String postUpdateCourse(@Valid @ModelAttribute CourseDtoDetail course, @PathVariable @NotNull Integer id,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course/update";
        }
        courseService.updateCourse((int) id, course);
        return "redirect:../detail/" + id;
    }

    @GetMapping("/delete/{id}")
    public String getDeleteCourse(Model model, @PathVariable @NotNull Integer id) {
        courseService.deleteCourse((int) id);
        return "redirect:../list";
    }
}
