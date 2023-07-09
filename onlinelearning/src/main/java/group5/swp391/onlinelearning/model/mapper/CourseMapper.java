package group5.swp391.onlinelearning.model.mapper;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetailStudent;
import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;
import group5.swp391.onlinelearning.model.teacher.CourseDTOAdd;
import group5.swp391.onlinelearning.model.teacher.CourseDTOEdit;
import group5.swp391.onlinelearning.model.teacher.CourseDTOTeacher;
import group5.swp391.onlinelearning.repository.TopicRepository;

@Component
public class CourseMapper {
    @Autowired
    private TopicRepository topicRepository;
    private HttpSession session;

    public CourseMapper(HttpSession session) {
        this.session = session;
    }
    // TODO: 0: Unsubmit 1: Submit 2: Reject 3 Published 4 DeActive

    public CourseDtoHomeDetail courseToCourseDtoHomeDetail(Course course) {
        CourseDtoHomeDetail courseDtoHomeDetail = CourseDtoHomeDetail.builder()
                .id(course.getId())
                .name(course.getName())
                .teacherName(course.getTeacher().getName())
                .teacherId(course.getTeacher().getId())
                .imageLink(course.getImageLink())
                .price(course.getPrice())
                .date(course.getDate())
                .build();
        return courseDtoHomeDetail;
    }

    public CourseDtoDetailStudent courseToCourseDtoDetailStudent(Course course) {
        CourseDtoDetailStudent courseDtoDetailStudent = CourseDtoDetailStudent.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .imageLink(course.getImageLink())
                .date(course.getDate())
                .price(course.getPrice())
                .build();
        return courseDtoDetailStudent;
    }

    public CourseDTOTeacher courseToCourseDTOTeacher(Course course) {
        CourseDTOTeacher courseDTOTeacher = CourseDTOTeacher.builder()
                .id(course.getId())
                .name(course.getName())
                .date(course.getDate())
                .image(course.getImageLink())
                .price(course.getPrice())
                .status(course.getStatus())
                .topic(course.getTopic().getName())
                .description(course.getDescription())
                // TODO:Thêm star
                .build();
        return courseDTOTeacher;
    }

    public Course courseDTOAddtoCourse(CourseDTOAdd courseDTOAdd) {
        Topic topic = topicRepository.findById(courseDTOAdd.getTopic_id()).get();
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        User teacher = (User) session.getAttribute("studentSession");
        Course course = Course.builder()
                .name(courseDTOAdd.getName())
                .date(date)
                .imageLink(courseDTOAdd.getImageLink())
                .status(0)
                .price(courseDTOAdd.getPrice())
                .topic(topic)
                .teacher(teacher)
                .description(courseDTOAdd.getDescription())
                // TODO:Thêm star
                .build();
        return course;
    }

    public CourseDTOEdit courseToCourseDtoEdit(Course course) {
        CourseDTOEdit courseDTOEdit = CourseDTOEdit.builder()
                .id(course.getId())
                .name(course.getName())
                .imageLink(course.getImageLink())
                .price(course.getPrice())
                .topic_id(course.getTopic().getId())
                .description(course.getDescription())
                // TODO:Thêm star
                .build();
        return courseDTOEdit;
    }
}
