package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.mapper.CourseMapper;
import group5.swp391.onlinelearning.model.teacher.CourseDTOAdd;
import group5.swp391.onlinelearning.model.teacher.CourseDTOTeacher;
import group5.swp391.onlinelearning.repository.CourseRepository;
import group5.swp391.onlinelearning.service.impl.CourseService;

@SpringBootTest
public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private HttpSession session;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCourse() {
        List<Course> expectedCourses = new ArrayList<>();
        Course course1 = Course.builder().id(1).name("java").build();
        Course course2 = Course.builder().id(2).name("css").build();
        expectedCourses.add(course1);
        expectedCourses.add(course2);
        when(courseRepository.findAll()).thenReturn(expectedCourses);

        List<Course> courses = courseService.getAllCourses();

        assertEquals(expectedCourses, courses);
    }

    @Test
    public void getCourseByCourseId() {
        Course course1 = Course.builder().id(1).name("java").build();
        when(courseRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(course1));

        Course courseResult = courseService.getCourseByCourseId(1);

        assertEquals(courseResult, course1);
    }

    @Test
    public void createCourse() {
        CourseDTOAdd courseDTOAdd = new CourseDTOAdd();
        Course course = Course.builder().id(1).name("java").build();
        when(courseMapper.courseDTOAddtoCourse(courseDTOAdd)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.createCourse(courseDTOAdd);

        assertEquals(course, result);
        Mockito.verify(courseMapper, Mockito.times(1)).courseDTOAddtoCourse(courseDTOAdd);
        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
    }

    @Test
    public void getCourseDTOTeacherList() {
        User teacher = User.builder().id(1).name("thai").build();
        List<Course> courses = new ArrayList<>();
        Course course1 = Course.builder().id(1).name("java").build();
        Course course2 = Course.builder().id(2).name("css").build();
        courses.add(course1);
        courses.add(course2);
        List<CourseDTOTeacher> expectedDTOTeachers = new ArrayList<>();
        CourseDTOTeacher dtoTeacher1 = CourseDTOTeacher.builder().name("Course 1").build();
        CourseDTOTeacher dtoTeacher2 = CourseDTOTeacher.builder().name("css").build();
        expectedDTOTeachers.add(dtoTeacher1);
        expectedDTOTeachers.add(dtoTeacher2);
        Mockito.when(courseRepository.findAllByTeacherId(1)).thenReturn(courses);
        Mockito.when(courseMapper.courseToCourseDTOTeacher(Mockito.any())).thenReturn(dtoTeacher1, dtoTeacher2);
        Mockito.when(session.getAttribute("userSession")).thenReturn(teacher);

        List<CourseDTOTeacher> result = courseService.getCourseDTOTeacherList();

        assertEquals(expectedDTOTeachers, result);
        Mockito.verify(session, Mockito.times(1)).getAttribute("userSession");
        Mockito.verify(courseRepository, Mockito.times(1)).findAllByTeacherId(1);
        Mockito.verify(courseMapper, Mockito.times(2)).courseToCourseDTOTeacher(Mockito.any());
    }

    @Test
    public void isCourse() {
        List<Course> courses = new ArrayList<>();
        Course course1 = Course.builder().id(1).name("java").build();
        Course course2 = Course.builder().id(2).name("css").build();
        courses.add(course1);
        courses.add(course2);
        when(courseRepository.findAll()).thenReturn(courses);

        boolean result = courseService.isCourse(1);

        assertTrue(result);
    }

    @Test
    public void isCourseNotExists() {
        List<Course> courses = new ArrayList<>();
        Course course1 = Course.builder().id(1).name("java").build();
        Course course2 = Course.builder().id(2).name("css").build();
        courses.add(course1);
        courses.add(course2);

        when(courseRepository.findAll()).thenReturn(courses);

        boolean result = courseService.isCourse(3);

        assertFalse(result);
    }
}
