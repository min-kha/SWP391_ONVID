package group5.swp391.onlinelearning.model.mapper;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetailStudent;
import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;

public class CourseMapper {
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
}
