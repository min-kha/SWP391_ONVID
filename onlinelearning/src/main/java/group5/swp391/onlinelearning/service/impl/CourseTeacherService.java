package group5.swp391.onlinelearning.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.model.mapper.CourseMapper;
import group5.swp391.onlinelearning.model.teacher.CourseDTOTeacher;
import group5.swp391.onlinelearning.repository.CourseRepository;
import group5.swp391.onlinelearning.service.ICourseTeacherService;

@Service
public class CourseTeacherService implements ICourseTeacherService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseDTOTeacher> getCourseDTOTeacherList() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTOTeacher> cDtoTeachers = new ArrayList<CourseDTOTeacher>();
        for (Course course : courses) {
            cDtoTeachers.add(courseMapper.courseToCourseDTOTeacher(course));
        }
        return cDtoTeachers;
    }

}
