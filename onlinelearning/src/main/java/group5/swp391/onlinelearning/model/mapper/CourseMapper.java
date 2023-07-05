package group5.swp391.onlinelearning.model.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetailStudent;
import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;
import group5.swp391.onlinelearning.model.teacher.CourseDTOTeacher;
import group5.swp391.onlinelearning.repository.TopicRepository;

@Component
public class CourseMapper {
    @Autowired
    private static TopicRepository topicRepository;

    public static CourseDtoHomeDetail courseToCourseDtoHomeDetail(Course course) {
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

    public static CourseDtoDetailStudent courseToCourseDtoDetailStudent(Course course) {
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

    public static CourseDTOTeacher courseToCourseDTOTeacher(Course course) {
        CourseDTOTeacher courseDTOTeacher = CourseDTOTeacher.builder()
                .name(course.getName())
                .date(course.getDate())
                .image(course.getImageLink())
                .status(course.getStatus())
                .price(course.getPrice())
                .topic(course.getTopic().getName())
                // TODO:Thêm star
                .build();
        return courseDTOTeacher;
    }
}
