package group5.swp391.onlinelearning.controller.student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        int courseIdInt = Integer.parseInt(courseId);
        List<Lesson> listLesson = lessonService.getLessonsByCourseId(courseIdInt);
        int firstLessonId = listLesson.get(0).getId();
        return "redirect:/student/course/lesson/" + courseId + "/" + firstLessonId;
    }

    @GetMapping("/lesson/{courseId}/{leassonId}")
    public String getLessonByCourseIdAndLessonId(@PathVariable String courseId, Model model,
            @PathVariable String leassonId, HttpSession session) {
        int courseIdInt = Integer.parseInt(courseId);
        List<Lesson> listLesson = lessonService.getLessonsByCourseId(courseIdInt);
        model.addAttribute("courseId", courseId);
        model.addAttribute("listLesson", listLesson);
        int leassonIdInt = Integer.parseInt(leassonId);
        Lesson lesson = lessonService.getLessonById(leassonIdInt);
        if (lesson.getDocument() == null || lesson.getDocument().equals("")) {
            model.addAttribute("isVideo", true);
        } else {
            model.addAttribute("isVideo", false);
        }
        model.addAttribute("lesson", lesson);
        User student = (User) session.getAttribute("studentSession");
        List<Learn> learns = learnService.getListLearnByLessonIdAndStudentId(listLesson, student);
        List<LessonDtoDetail> lessonDtoDetails = new ArrayList<>();
        int countDone = 0;
        for (int i = 0; i < listLesson.size(); i++) {
            LessonDtoDetail lessonDtoDetail = LearnMapper.lessonToLessonDtoDetail(listLesson.get(i), learns.get(i),
                    i + 1);
            lessonDtoDetails.add(lessonDtoDetail);
            if (learns.get(i).isStatus()) {
                countDone++;
            }
        }

        float progress = ((float) countDone / listLesson.size()) * 100;
        model.addAttribute("progress", progress);
        if (progress == 100.0) {
            model.addAttribute("isDone", "true");
        } else {
            model.addAttribute("isDone", "false");
        }
        List<Integer> numberList = IntStream.range(1, listLesson.size() + 1).boxed().collect(Collectors.toList());
        model.addAttribute("numberList", numberList);

        model.addAttribute("lessonDtoDetails", lessonDtoDetails);
        return "/student/course/all-lesson";
    }
}
