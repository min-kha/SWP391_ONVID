package group5.swp391.onlinelearning.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.entity.View;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetail;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetailStudent;
import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;
import group5.swp391.onlinelearning.model.mapper.CourseMapper;
import group5.swp391.onlinelearning.model.teacher.CourseDTOAdd;
import group5.swp391.onlinelearning.repository.CourseRepository;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserService userService;
    @Autowired
    CourseMapper courseMapper;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseByCourseId(int id) {
        return courseRepository.findById(id).get();
    }

    public CourseDtoDetail getCourseById(int id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            CourseDtoDetail courseDTO = new CourseDtoDetail();
            // copy course to courseDto
            courseDTO.setCourseId(course.getId());
            courseDTO.setCourseName(course.getName());
            courseDTO.setStatus(course.getStatus());
            courseDTO.setDescription(course.getDescription());
            courseDTO.setPrice(course.getPrice());
            courseDTO.setDate(course.getDate());
            courseDTO.setTopic(course.getTopic());
            // get numeber of last views in the course
            if (course.getViews() != null && !course.getViews().isEmpty()) {
                View view = ((List<View>) course.getViews()).get(course.getViews().size() - 1);
                courseDTO.setViews(view.getViewNumber());
            } else {
                courseDTO.setViews(0);
            }
            courseDTO.setFeedbacks(course.getFeedbacks());
            courseDTO.setTeacher(course.getTeacher());
            courseDTO.setLessons(course.getLessons());

            return courseDTO;
        }
        return null;
    }

    public Course createCourse(CourseDTOAdd courseDTOAdd) {
        Course course = courseMapper.courseDTOAddtoCourse(courseDTOAdd);
        return courseRepository.save(course);
    }

    public Course updateCourse(int id, CourseDtoDetail updatedCourse) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course existingCourse = courseOptional.get();
            existingCourse.setName(updatedCourse.getCourseName());
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setPrice(updatedCourse.getPrice());
            existingCourse.setDate(new Date());
            existingCourse.setTopic(updatedCourse.getTopic());
            return courseRepository.save(existingCourse);
        }
        // TODO: Throw exception in here
        return null;
    }

    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
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
