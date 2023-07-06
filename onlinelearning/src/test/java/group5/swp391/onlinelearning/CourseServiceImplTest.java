package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetail;
import group5.swp391.onlinelearning.repository.CourseRepository;
import group5.swp391.onlinelearning.service.Impl.CourseService;
import group5.swp391.onlinelearning.service.Impl.UserService;

public class CourseServiceImplTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCourses() {
        // Arrange
        Course course1 = new Course();
        course1.setId(1);
        course1.setName("Course 1");

        Course course2 = new Course();
        course2.setId(1);
        course2.setName("Course 2");

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        when(courseRepository.findAll()).thenReturn(courses);

        // Act
        List<Course> result = courseService.getAllCourses();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Course 1", result.get(0).getName());
        assertEquals("Course 2", result.get(1).getName());
    }

    @Test
    public void testGetCourseById() {
        // Arrange
        int courseId = 1;
        Course course = new Course();
        course.setId(courseId);
        course.setName("Course 1");
        course.setStatus(1);
        // Set other properties for the course

        Optional<Course> courseOptional = Optional.of(course);
        when(courseRepository.findById(courseId)).thenReturn(courseOptional);

        // Act
        CourseDtoDetail result = courseService.getCourseById(courseId);

        // Assert
        assertEquals(courseId, result.getCourseId());
        assertEquals("Course 1", result.getCourseName());
        assertEquals(1, result.getStatus());
        // Assert other properties for the result
    }

    @Test
    public void testCreateCourse() {
        // Arrange
        Course course = new Course();
        course.setName("New Course");
        course.setDescription("Course Description");
        course.setPrice(BigDecimal.valueOf(99.99));
        course.setTopic(new Topic().builder().id(1).build());

        // Mocking the userService.getUserById(2) call
        User teacher = new User();
        teacher.setId(2);
        when(userService.getUserById(2)).thenReturn(teacher);

        // Mocking the courseRepository.save(course) call
        Course savedCourse = new Course();
        savedCourse.setId(1);
        savedCourse.setName("New Course");
        savedCourse.setDescription("Course Description");
        savedCourse.setPrice(BigDecimal.valueOf(99.99));
        savedCourse.setTopic(new Topic().builder().id(1).build());
        savedCourse.setTeacher(teacher);
        savedCourse.setDate(new Date());
        when(courseRepository.save(course)).thenReturn(savedCourse);

        // Act
        Course result = courseService.createCourse(course);

        // Assert
        assertNotNull(result);
        assertEquals(savedCourse.getId(), result.getId());
        assertEquals(savedCourse.getName(), result.getName());
        assertEquals(savedCourse.getDescription(), result.getDescription());
        assertEquals(savedCourse.getPrice(), result.getPrice());
        assertEquals(savedCourse.getTopic(), result.getTopic());
        assertEquals(savedCourse.getTeacher(), result.getTeacher());
        assertEquals(savedCourse.getDate(), result.getDate());
        verify(userService, times(1)).getUserById(2);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testUpdateCourse() {
        // Arrange
        int courseId = 1;
        CourseDtoDetail updatedCourse = new CourseDtoDetail();
        updatedCourse.setCourseName("Updated Course");
        updatedCourse.setDescription("Updated Description");
        updatedCourse.setPrice(BigDecimal.valueOf(999));
        updatedCourse.setTopic(new Topic().builder().id(1).build());

        Course existingCourse = new Course();
        existingCourse.setId(courseId);
        existingCourse.setName("Original Course");
        existingCourse.setDescription("Original Description");
        existingCourse.setPrice(BigDecimal.valueOf(123));
        existingCourse.setTopic(new Topic().builder().id(1).build());

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(existingCourse)).thenReturn(existingCourse);
        // Act
        Course result = courseService.updateCourse(courseId, updatedCourse);
        // Assert
        assertNotNull(result);
        assertEquals(updatedCourse.getCourseName(), result.getName());
        assertEquals(updatedCourse.getDescription(), result.getDescription());
        assertEquals(updatedCourse.getPrice(), result.getPrice());
        assertEquals(updatedCourse.getTopic(), result.getTopic());
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(existingCourse);
    }

    @Test
    public void testDeleteCourse() {
        // Arrange
        int courseId = 1;
        // Act
        courseService.deleteCourse(courseId);
        // Assert
        verify(courseRepository, times(1)).deleteById(courseId);
    }

}
