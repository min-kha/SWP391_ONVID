package group5.swp391.onlinelearning.controller.student;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.Learn;
import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.LessonDtoDetail;
import group5.swp391.onlinelearning.model.mapper.LearnMapper;
import group5.swp391.onlinelearning.service.ILearnService;
import group5.swp391.onlinelearning.service.ILessonService;

@Controller
@RequestMapping("/student/course")
public class LessonController {

    @Autowired
    ILessonService lessonService;
    @Autowired
    ILearnService learnService;

    @GetMapping("/all-lesson/{courseId}")
    public String getAllLessonInCourse(@PathVariable String courseId, Model model, HttpSession session) {
        return "redirect:/student/course/lesson/" + courseId + "/" + 1;
    }

    @GetMapping("/lesson/{courseId}/{leassonId}")
    public String getLessonByCourseIdAndLessonId(@PathVariable String courseId, Model model,
            @PathVariable String leassonId, HttpSession session) {
        int courseIdInt = Integer.parseInt(courseId);
        List<Lesson> listLesson = lessonService.getLessonsByCourseId(courseIdInt);
        model.addAttribute("courseId", courseId);
        model.addAttribute("listLesson", listLesson);
        Lesson lesson = listLesson.get(Integer.parseInt(leassonId) - 1);
        if (lesson.getDocument() == null || lesson.getDocument().equals("")) {
            model.addAttribute("isVideo", true);
        } else {
            model.addAttribute("isVideo", false);
        }
        model.addAttribute("lesson", lesson);
        User student = (User) session.getAttribute("studentSession");
        List<Learn> learns = learnService.getListLearnByLessonIdAndStudentId(listLesson, student);
        List<LessonDtoDetail> lessonDtoDetails = new ArrayList<>();
        for (int i = 0; i < listLesson.size(); i++) {
            LessonDtoDetail lessonDtoDetail = LearnMapper.lessonToLessonDtoDetail(listLesson.get(i), learns.get(i));
            lessonDtoDetails.add(lessonDtoDetail);
        }

        model.addAttribute("lessonDtoDetails", lessonDtoDetails);
        return "/student/course/all-lesson";
    }
}
