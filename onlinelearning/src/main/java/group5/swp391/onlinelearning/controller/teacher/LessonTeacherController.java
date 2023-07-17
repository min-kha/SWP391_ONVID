package group5.swp391.onlinelearning.controller.teacher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.model.mapper.LessonMapper;
import group5.swp391.onlinelearning.model.teacher.LessonDtoAdd;
import group5.swp391.onlinelearning.model.teacher.LessonDtoEditDocument;
import group5.swp391.onlinelearning.model.teacher.LessonDtoEditVideo;
import group5.swp391.onlinelearning.service.ILessonService;
import group5.swp391.onlinelearning.service.impl.CourseService;

@RequestMapping(value = "/teacher")
@Service

public class LessonTeacherController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CourseService courseService;
    @Autowired
    ILessonService lessonService;

    @Autowired
    LessonMapper lessonMappers;

    @GetMapping("/lesson/create/{courseid}")
    public String getCreateLessionNoneOption(@PathVariable Integer courseid, Model model) {
        if (courseid == null || !courseService.isCourse(courseid)) {
            return "404";
        }
        if (!courseService.checkCourseOwner(courseid))
            return "AccessDenied";
        model.addAttribute("courseId", courseid);
        model.addAttribute("errorFormat", "");
        return "teacher/lesson/choose-option";
    }

    @GetMapping("/lesson/create/{courseid}/{options}")
    public String getCreateLesson(@PathVariable Integer courseid, Model model, @PathVariable Integer options) {
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
    public String postCreateLesson(
            @Valid @ModelAttribute("lesson") LessonDtoAdd lessonDtoAdd, BindingResult result, HttpServletRequest req,
            @RequestParam("video") MultipartFile video, Model model) throws IOException, ServletException {
        if (result.hasErrors()) {
            return "/lesson/create/" + lessonDtoAdd.getCourseId();
        }
        // lay course id
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        lessonDtoAdd.setCourseId(courseId);

        // lay link video va luu video vao project
        // Xử lý video
        String projectPath = System.getProperty("user.dir");
        String fileName = "";
        Part filePart = req.getPart("video");
        fileName = filePart.getSubmittedFileName();
        if (fileName.endsWith(".mp4") || fileName.endsWith(".mov")) {
            String relativePath = "\\src\\main\\resources\\static\\video";
            String filePath = projectPath + relativePath + File.separator + fileName;
            Path path = Paths.get(filePath);
            try (InputStream inputStream = filePart.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                return "404";
            }
        } else {
            Lesson lesson = new Lesson();
            lesson.setCourse(courseService.getCourseById(courseId));
            model.addAttribute("lesson", new LessonDtoAdd());
            model.addAttribute("id", courseId);
            model.addAttribute("errorFormat", "Video is not available");
            return "teacher/lesson/add-video";
        }

        try {
            Lesson lesson = lessonService.addLessonVideo(lessonDtoAdd, fileName);
            if (lesson == null)
                throw new Exception();
        } catch (Exception e) {
            return "404";
        }

        return "redirect:/teacher/lesson/list/" + lessonDtoAdd.getCourseId();
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

        return "redirect:/teacher/lesson/list/" + lessonDtoAdd.getCourseId();
    }

    @GetMapping(value = "lesson/detail/{courseId}/{lessonId}")
    public String getLessons(@PathVariable Integer courseId, Model model, @PathVariable Integer lessonId) {
        // get all lesson following by courseid

        List<Lesson> lessons = lessonService.getLessonsByCourseId(courseId);
        Lesson lesson = null;
        try {
            lesson = lessonService.getLessonById(lessonId);
        } catch (Exception e) {
            return "404";
        }
        boolean isVideo = true;
        if (lesson.getVideo() == null || lesson.getVideo().isEmpty()) {
            isVideo = false;
        }
        model.addAttribute("lessons", lessons);
        model.addAttribute("lesson", lesson);
        model.addAttribute("empty", 1);
        model.addAttribute("isVideo", isVideo);
        return "teacher/lesson/list";
    }

    @GetMapping(value = "lesson/list/{courseId}")
    public String getListLessons(@PathVariable Integer courseId, Model model) {
        // get all lesson following by courseid
        List<Lesson> lessons = lessonService.getLessonsByCourseId(courseId);

        Lesson lesson = null;
        // check empty courses
        int empty = 0; // = 0 uf for course not have lessson
        if (lessons.size() != 0) {
            empty = 1;
            lesson = lessons.get(0);
        }
        boolean isVideo = true;
        if (lesson.getVideo() == null || lesson.getVideo().isEmpty()) {
            isVideo = false;
        }
        model.addAttribute("isVideo", isVideo);
        model.addAttribute("empty", empty);
        model.addAttribute("lessons", lessons);
        model.addAttribute("lesson", lesson);
        return "teacher/lesson/list";
    }

    @GetMapping(value = "/lesson/edit/{courseId}/{lessonId}/{options}")
    public String getEditLesson(@PathVariable Integer lessonId, @PathVariable Integer courseId,
            @PathVariable Integer options, Model model) {
        // Check validation of lesson
        if (lessonService.getLessonById(lessonId) == null)
            return "404";
        // Check Course Owner
        if (!courseService.checkCourseOwner(courseId))
            return "AccessDenied";
        // Check lesson in course or not
        if (!lessonService.isLessonOfCourse(lessonId, courseId))
            return "404";
        Course course = courseService.getCourseById(lessonId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        // check lesson has video
        boolean isLessonVideo = lessonService.isLessonVideo(lessonId);
        // check options == (1 document) == (0 video)
        boolean isVideo = options == 0;

        // set lessonDtoEdit model for html
        // lesson hien tai va lesson muon sua khac nhau ==> Tạo Lesson Mới và thêm vào
        if (isLessonVideo != isVideo) {
            // check
            if (isVideo == true) {
                LessonDtoEditVideo lessonDtoEditVideo = new LessonDtoEditVideo();
                lessonDtoEditVideo = modelMapper.map(lesson, LessonDtoEditVideo.class);
                model.addAttribute("lesson", lessonDtoEditVideo);
            }

            else {
                LessonDtoEditDocument lessonDtoEditDocument = new LessonDtoEditDocument();
                lessonDtoEditDocument = modelMapper.map(lesson, LessonDtoEditDocument.class);
                model.addAttribute("lesson", lessonDtoEditDocument);
            }

        } else { // lesson hiện tại và lesson sửa là 1 kiêu ( lesson kiểu video hoặc document)
            if (isVideo == true) {
                LessonDtoEditVideo lessonDtoEdit = new LessonDtoEditVideo();
                lessonDtoEdit = modelMapper.map(lesson, LessonDtoEditVideo.class);
                String oldVideo = lesson.getVideo();
                model.addAttribute("oldVideo", oldVideo);
                model.addAttribute("lesson", lessonDtoEdit);
            } else {
                LessonDtoEditDocument lessonEditDocument = new LessonDtoEditDocument();
                lessonEditDocument = modelMapper.map(lesson, LessonDtoEditDocument.class);
                model.addAttribute("lesson", lessonEditDocument);
            }
        }

        model.addAttribute("options", options);
        model.addAttribute("errorFormat", "");

        // với trường hợp thêm mới lesson thay thế vào
        return "/teacher/lesson/edit";
    }

    @PostMapping(value = "lesson/edit/{courseId}/{lessonId}/0")
    public String postEditVideo(
            @Valid @ModelAttribute("lesson") LessonDtoEditVideo lessonDtoEditVideo, BindingResult result,
            HttpServletRequest req, @PathVariable Integer courseId, @PathVariable Integer lessonId,
            @RequestParam("video") MultipartFile video, Model model) throws IOException, ServletException {
        if (result.hasErrors()) {
            model.addAttribute("options", 0);
            return "teacher/lesson/edit";
        }
        // Xử lý video
        String oldVideo = req.getParameter("oldVideo");
        String projectPath = System.getProperty("user.dir");
        String fileName = "";
        Part filePart = req.getPart("video");
        fileName = filePart.getSubmittedFileName();
        if (fileName.endsWith(".mp4") || fileName.endsWith(".mov")) {
            String relativePath = "\\src\\main\\resources\\static\\video";
            String filePath = projectPath + relativePath + File.separator + fileName;
            Path path = Paths.get(filePath);
            try (InputStream inputStream = filePart.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                return "404";
            }
            oldVideo = fileName;
        } else {
            // TODO:
            Lesson lesson = modelMapper.map(lessonDtoEditVideo, Lesson.class);
            model.addAttribute("errorFormat", "Video format not supported");
            model.addAttribute("options", 0);
            if (lesson.getDocument() != null || lesson.getDocument() != "") {
                LessonDtoEditDocument lessonEditDocument = new LessonDtoEditDocument();
                lessonEditDocument = modelMapper.map(lesson, LessonDtoEditDocument.class);
                model.addAttribute("lesson", lessonEditDocument);
            } else {
                LessonDtoEditVideo lessonDtoEdit = new LessonDtoEditVideo();
                lessonDtoEdit = modelMapper.map(lesson, LessonDtoEditVideo.class);
                model.addAttribute("oldVideo", oldVideo);
                model.addAttribute("lesson", lessonDtoEdit);
            }
            return "teacher/lesson/edit";
        }

        // TODO:Save
        Lesson lesson = modelMapper.map(lessonDtoEditVideo, Lesson.class);
        lesson.setVideo(oldVideo);
        lesson.setDocument("");
        lesson.setStatus(true);
        try {
            if (lessonService.updateLesson(lesson) == null)
                throw new Exception("");
        } catch (Exception e) {
            return "404";
        }
        return "redirect:/teacher/lesson/list/" + lessonDtoEditVideo.getCourse().getId();
    }

    @PostMapping(value = "lesson/edit/{courseId}/{lessonId}/1")
    public String postEditDocument(
            @Valid @ModelAttribute("lesson") LessonDtoEditDocument lessonDtoEditDocument, BindingResult result,
            HttpServletRequest req, Model model) throws IOException, ServletException {
        if (result.hasErrors()) {
            model.addAttribute("options", 0);
            return "teacher/lesson/edit";
        }

        // TODO:Save
        Lesson lesson = modelMapper.map(lessonDtoEditDocument, Lesson.class);
        lesson.setVideo("");
        lesson.setStatus(true);
        try {
            if (lessonService.updateLesson(lesson) == null)
                throw new Exception("");
        } catch (Exception e) {
            return "404";
        }
        return "redirect:/teacher/lesson/list/" + lessonDtoEditDocument.getCourse().getId();
    }

    @GetMapping("/lesson/delete/{courseId}/{lessonId}")
    public String getDeleteLesson(@PathVariable Integer courseId, @PathVariable Integer lessonId, Model model) {
        // Check validation of lesson
        if (lessonService.getLessonById(lessonId) == null)
            return "404";
        // Check Course Owner
        if (!courseService.checkCourseOwner(courseId))
            return "AccessDenied";
        // Check lesson in course or not
        if (!lessonService.isLessonOfCourse(lessonId, courseId))
            return "404";
        // Delete lesson
        if (lessonService.deleteLessonByIdOfTeacher(lessonId, courseId) == null)
            return "404";
        // Go to list lesson of course owner
        return "redirect:" + "/teacher/lesson/list/" + courseId;
    }
}
