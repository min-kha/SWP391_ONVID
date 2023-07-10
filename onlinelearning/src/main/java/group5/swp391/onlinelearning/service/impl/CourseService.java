package group5.swp391.onlinelearning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetailStudent;
import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;

import group5.swp391.onlinelearning.model.mapper.CourseMapper;
import group5.swp391.onlinelearning.model.teacher.CourseDTOAdd;
import group5.swp391.onlinelearning.model.teacher.CourseDTOEdit;
import group5.swp391.onlinelearning.model.teacher.CourseDTOTeacher;
import group5.swp391.onlinelearning.repository.CourseRepository;
import group5.swp391.onlinelearning.repository.TopicRepository;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserService userService;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    private TopicRepository topicRepository;

    private HttpSession session;

    public CourseService(HttpSession session) {
        this.session = session;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseByCourseId(int id) {
        return courseRepository.findById(id).get();
    }

    public Course getCourseById(int id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            return course;
        }
        return null;
    }

    // hung
    public Course createCourse(CourseDTOAdd courseDTOAdd) {
        Course course = courseMapper.courseDTOAddtoCourse(courseDTOAdd);
        return courseRepository.save(course);
    }

    public List<CourseDTOTeacher> getCourseDTOTeacherList() {
        User teacher = (User) session.getAttribute("studentSession");
        int id = teacher.getId();
        List<Course> courses = courseRepository.findAllByTeacherId(id);
        List<CourseDTOTeacher> cDtoTeachers = new ArrayList<CourseDTOTeacher>();
        for (Course course : courses) {
            cDtoTeachers.add(courseMapper.courseToCourseDTOTeacher(course));
        }
        return cDtoTeachers;
    }

    // check course exit in database
    public boolean isCourse(int courseId) {
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            if (course.getId() == courseId) {
                return true;
            }
        }
        return false;
    }

    // Check course have true teacher owener
    public boolean checkCourseOwner(int courseId) {
        User teacher = (User) session.getAttribute("studentSession");
        int id = teacher.getId();
        List<Course> courses = courseRepository.findAllByTeacherId(id);
        for (Course course : courses) {
            if (course.getId() == courseId) {
                return true;
            }
        }
        return false;
    }

    public Course updateCourse(CourseDTOEdit courseDTOEdit) {
        Optional<Course> courseOptional = courseRepository.findById(courseDTOEdit.getId());
        if (courseOptional.isPresent()) {
            Course existingCourse = courseOptional.get();
            existingCourse.setName(courseDTOEdit.getName());
            Topic topic = topicRepository.findById(courseDTOEdit.getTopic_id()).get();
            existingCourse.setTopic(topic);
            existingCourse.setPrice(courseDTOEdit.getPrice());
            existingCourse.setDescription(courseDTOEdit.getDescription());
            existingCourse.setImageLink(courseDTOEdit.getImageLink());
            return courseRepository.save(existingCourse);
        }
        // TODO: Throw exception in here
        return null;
    }

    public Course deleteCourse(int id) {
        Course course = courseRepository.findById(id).get();
        if (course.getStatus() == 4) {
            course.setStatus(0);
        } else {
            course.setStatus(4);
        }
        return courseRepository.save(course);
    }

    public List<CourseDtoHomeDetail> getAllCourseDtoHomeDetails() {
        List<Course> courses = getAllCourses();
        List<CourseDtoHomeDetail> courseDtoHomeDetails = new ArrayList<>();

        for (Course course : courses) {
            courseDtoHomeDetails.add(CourseMapper.courseToCourseDtoHomeDetail(course));
        }
        return courseDtoHomeDetails;
    }

    public Course getCourseAllById(int id) {
        Course course = courseRepository.findById(id).get();
        return course;
    }

    public CourseDtoDetailStudent getCourseDetailForStudentById(int id) {
        Course course = getCourseAllById(id);
        CourseDtoDetailStudent courseRes = CourseMapper.courseToCourseDtoDetailStudent(course);
        return courseRes;
    }

    public List<Course> getMyCourse(int studentId) {
        return courseRepository.getMyCourse(studentId);
    }

}