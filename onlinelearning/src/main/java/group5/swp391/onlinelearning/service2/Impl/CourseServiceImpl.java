package group5.swp391.onlinelearning.service2.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.entity.View;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetail;
import group5.swp391.onlinelearning.repository2.CourseRepository;

@Service
public class CourseServiceImpl {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserService userService;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public CourseDtoDetail getCourseById(int id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            CourseDtoDetail courseDTO = new CourseDtoDetail();
            // copy course to courseDto
            courseDTO.setCourseId(course.getCourseId());
            courseDTO.setCourseName(course.getCourseName());
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

    public Course createCourse(Course course) {
        course.setDate(new Date());
        // TODO: get teacher from session
        course.setTeacher(userService.getUserById(2));
        // TODO: chưa có CRUD topic nên làm tạm
        course.setTopic(new Topic().builder().id(1).build());
        return courseRepository.save(course);
    }

    public Course updateCourse(int id, CourseDtoDetail updatedCourse) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course existingCourse = courseOptional.get();
            existingCourse.setCourseName(updatedCourse.getCourseName());
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
}
