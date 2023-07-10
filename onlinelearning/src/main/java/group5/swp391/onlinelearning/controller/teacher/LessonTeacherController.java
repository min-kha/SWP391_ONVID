package group5.swp391.onlinelearning.controller.teacher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.model.teacher.LessonDtoAdd;
import group5.swp391.onlinelearning.service.ILessonService;
import group5.swp391.onlinelearning.service.impl.CourseService;

@RequestMapping(value = "/teacher")
@Service

public class LessonTeacherController {

    @Autowired
    CourseService courseService;
    @Autowired
    ILessonService lessonService;

    @GetMapping("/lesson/create/{courseid}")
    public String getCreateLessionNoneOption(@PathVariable Integer courseid, Model model) {
        if (courseid == null || !courseService.isCourse(courseid)) {
            return "404";
        }
        if (!courseService.checkCourseOwner(courseid))
            return "AccessDenied";
        model.addAttribute("courseId", courseid);
        return "teacher/lesson/choose-option";
    }

    @GetMapping("/lesson/create/{courseid}/{options}")
    public String getCreateLession(@PathVariable Integer courseid, Model model, @PathVariable Integer options) {
        if (courseid == null || !courseService.isCourse(courseid)) {
            return "404";
        }
        if (!courseService.checkCourseOwner(courseid))
            return "AccessDenied";
        Lesson lesson = new Lesson();
        lesson.setCourse(courseService.getCourseById(courseid));
        model.addAttribute("lesson", new LessonDtoAdd());
        model.addAttribute("id", courseid);
        if (options == 1) {
            return "teacher/lesson/add-document";
        }
        return "teacher/lesson/add-video";
    }

    @PostMapping("/lesson/create/video/{courseid}")
    public String postCreateLession(
            @Valid @ModelAttribute("lesson") LessonDtoAdd lessonDtoAdd, HttpServletRequest req,
            @RequestParam("video") MultipartFile video, BindingResult result) throws IOException, ServletException {
        if (result.hasErrors()) {
            return "/lesson/create/" + lessonDtoAdd.getCourseId();
        }
        // lay course id
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        lessonDtoAdd.setCourseId(courseId);

        // lay link video va luu video vao project
        String projectPath = System.getProperty("user.dir");
        String fileName = "";
        Part filePart = req.getPart("video");
        fileName = filePart.getSubmittedFileName();
        fileName.equals("");
        String relativePath = "\\onlinelearning\\src\\main\\resources\\static\\video";
        String filePath = projectPath + relativePath + File.separator + fileName;
        Path path = Paths.get(filePath);
        InputStream inputStream = filePart.getInputStream();
        Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

        try {
            Lesson lesson = lessonService.addLessonVideo(lessonDtoAdd, fileName);
            if (lesson == null)
                throw new Exception();
        } catch (Exception e) {
            return "404";
        }

        return "404";
    }

    @PostMapping("/lesson/create/document/{courseid}")
    public String postCreateLessionDocument(
            @Valid @ModelAttribute("lesson") LessonDtoAdd lessonDtoAdd, HttpServletRequest req, BindingResult result)
            throws IOException, ServletException {
        if (result.hasErrors()) {
            return "/lesson/create/" + lessonDtoAdd.getCourseId();
        }
        // lay course id
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        lessonDtoAdd.setCourseId(courseId);

        // lay document
        String document = req.getParameter("document");
        try {
            Lesson lesson = lessonService.addLessonDocument(lessonDtoAdd, document);
            if (lesson == null)
                throw new Exception();
        } catch (Exception e) {
            return "404";
        }

        return "404";
    }
}
