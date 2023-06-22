package group5.swp391.onlinelearning.Service.Impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.Repository.CourseRepository;
import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.View;
import group5.swp391.onlinelearning.model.CourseDTO;

@Service
public class CourseServiceImpl {
    @Autowired
    CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public CourseDTO getCourseById(int id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            CourseDTO courseDTO = new CourseDTO();
            // Sao chép các thuộc tính từ đối tượng Course sang CourseDTO
            courseDTO.setCourseId(course.getCourseId());
            courseDTO.setCourseName(course.getCourseName());
            courseDTO.setStatus(course.getStatus());
            courseDTO.setDescription(course.getDescription());
            courseDTO.setPrice(course.getPrice());
            courseDTO.setDate(course.getDate());
            courseDTO.setTopic(course.getTopic());
            // get numeber of last views in the course
            View view = (View) ((List) course.getViews()).get(course.getViews().size() - 1);
            courseDTO.setViews(view.getViewNumber());
            courseDTO.setFeedbacks(course.getFeedbacks());
            courseDTO.setTeacher(course.getTeacher());
            courseDTO.setLessons(course.getLessons());

            return courseDTO;
        }
        return null;
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(int id, CourseDTO updatedCourse) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course existingCourse = courseOptional.get();
            existingCourse.setCourseName(updatedCourse.getCourseName());
            existingCourse.setStatus(updatedCourse.getStatus());
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setPrice(updatedCourse.getPrice());
            existingCourse.setDate(new Date());
            existingCourse.setTopic(updatedCourse.getTopic());
            return courseRepository.save(existingCourse);
        }
        return null;
    }

    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }

}
