package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
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
import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.model.dto.CourseDtoDetailStudent;
import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;
import group5.swp391.onlinelearning.model.mapper.CourseMapper;
import group5.swp391.onlinelearning.model.teacher.CourseDTOAdd;
import group5.swp391.onlinelearning.model.teacher.CourseDTOEdit;
import group5.swp391.onlinelearning.model.teacher.CourseDTOTeacher;
import group5.swp391.onlinelearning.repository.CourseRepository;
import group5.swp391.onlinelearning.repository.TopicRepository;
import group5.swp391.onlinelearning.service.impl.CourseService;
import group5.swp391.onlinelearning.service.impl.FeedbackService;

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

    @Mock
    TopicRepository topicRepository;

    @Mock
    FeedbackService feedbackService;

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
        Mockito.when(session.getAttribute("user")).thenReturn(teacher);

        List<CourseDTOTeacher> result = courseService.getCourseDTOTeacherList();

        assertEquals(expectedDTOTeachers, result);
        Mockito.verify(session, Mockito.times(1)).getAttribute("user");
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

    @Test
    public void testCheckCourseOwner_WithMatchingCourseId_ShouldReturnTrue() {
        // Giả lập dữ liệu trả về từ courseRepository
        User teacher = User.builder().id(1).name("thai").build();
        Course course1 = Course.builder().id(1).name("java").build();
        Course course2 = Course.builder().id(2).name("css").build();
        List<Course> mockCourses = new ArrayList<>();
        mockCourses.add(course1);
        mockCourses.add(course2);
        Mockito.when(session.getAttribute("user")).thenReturn(teacher);
        when(courseRepository.findAllByTeacherId(1)).thenReturn(mockCourses);

        // Kiểm tra kết quả trả về từ phương thức
        boolean result = courseService.checkCourseOwner(1);
        assertTrue(result);
    }

    @Test
    public void testCheckCourseOwner_WithNonMatchingCourseId_ShouldReturnFalse() {
        // Giả lập dữ liệu trả về từ courseRepository
        User teacher = User.builder().id(1).name("thai").build();
        Course course1 = Course.builder().id(1).name("java").build();
        Course course2 = Course.builder().id(2).name("css").build();
        List<Course> mockCourses = new ArrayList<>();
        mockCourses.add(course1);
        mockCourses.add(course2);
        Mockito.when(session.getAttribute("user")).thenReturn(teacher);

        when(courseRepository.findAllByTeacherId(1)).thenReturn(mockCourses);

        // Kiểm tra kết quả trả về từ phương thức
        boolean result = courseService.checkCourseOwner(3);
        assertFalse(result);
    }

    @Test
    public void testCheckCourseOwner_WithNoCourses_ShouldReturnFalse() {
        // Giả lập dữ liệu trả về từ courseRepository là một danh sách rỗng
        User teacher = User.builder().id(1).name("thai").build();
        when(courseRepository.findAllByTeacherId(1)).thenReturn(Collections.emptyList());
        Mockito.when(session.getAttribute("user")).thenReturn(teacher);

        // Kiểm tra kết quả trả về từ phương thức
        boolean result = courseService.checkCourseOwner(1);
        assertFalse(result);
    }

    @Test
    public void testUpdateCourse_WithExistingCourse_ShouldReturnUpdatedCourse() {
        // Giả lập dữ liệu đầu vào và đối tượng Course

        CourseDTOEdit courseDTOEdit = CourseDTOEdit.builder().id(1).name("java").description("java java")
                .price(new BigDecimal("100000")).topic_id(1).imageLink("java.png").build();

        CourseDTOEdit existingCourse = CourseDTOEdit.builder().id(1).name("java meo meo").description("java introduce")
                .price(new BigDecimal("50000")).topic_id(3).imageLink("old_image_url").build();
        // Mock behavior của courseRepository
        Course course = Course.builder().id(1).name("java").description("java java")
                .price(new BigDecimal("100000")).imageLink("java.png").build();
        when(courseRepository.findById(courseDTOEdit.getId())).thenReturn(Optional.of(course));
        when(topicRepository.findById(courseDTOEdit.getTopic_id())).thenReturn(Optional.of(new Topic()));
        when(courseService.updateCourse(courseDTOEdit)).thenReturn(course);

        // Gọi phương thức cần kiểm tra
        Course updatedCourse = courseService.updateCourse(courseDTOEdit);

        // Kiểm tra xem phương thức trả về Course đã được cập nhật đúng chưa
        assertEquals(courseDTOEdit.getName(), updatedCourse.getName());
        assertEquals(courseDTOEdit.getPrice(), updatedCourse.getPrice());
        assertEquals(courseDTOEdit.getDescription(), updatedCourse.getDescription());
        assertEquals(courseDTOEdit.getImageLink(), updatedCourse.getImageLink());
    }

    @Test
    public void testUpdateCourse_WithNonExistingCourse_ShouldReturnNull() {
        // Giả lập dữ liệu đầu vào
        CourseDTOEdit courseDTOEdit = CourseDTOEdit.builder().id(1).name("java").description("java java")
                .price(new BigDecimal("100000")).topic_id(1).imageLink("java.png").build();
        // Mock behavior của courseRepository
        when(courseRepository.findById(courseDTOEdit.getId())).thenReturn(Optional.empty());
        // Gọi phương thức cần kiểm tra
        Course updatedCourse = courseService.updateCourse(courseDTOEdit);
        // Kiểm tra xem phương thức trả về null khi không tìm thấy Course cần update
        assertEquals(null, updatedCourse);
    }

    @Test
    public void testDeleteCourse_WithExistingCourse_ShouldDeleteCourse() {
        // Giả lập dữ liệu đầu vào và đối tượng Course
        int courseId = 1;
        Course course = Course.builder().id(1).name("java").description("java java")
                .price(new BigDecimal("100000")).imageLink("java.png").build();

        // Mock behavior của courseRepository.findById
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Gọi phương thức cần kiểm tra
        courseService.deleteCourse(courseId);

        // Kiểm tra xem phương thức đã gọi phương thức delete với đúng đối tượng Course
        // chưa
        verify(courseRepository).delete(course);
    }


    @Test
    public void testGetAllCourseDtoHomeDetails_ShouldReturnCorrectList() {
        // Mock data
        Course course1 = Course.builder().id(1).name("java").description("java java")
                .price(new BigDecimal("100000")).imageLink("java.png").build();
        Course course2 = Course.builder().id(2).name("css").description("css css")
                .price(new BigDecimal("200000")).imageLink("css.png").build();
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        Feedback feedback1 = Feedback.builder().id(1).ratingStar(5).comment("course so good").build();
        Feedback feedback2 = Feedback.builder().id(2).ratingStar(5).comment("course so good").build();
        List<Feedback> feedbackList = new ArrayList<>();
        feedbackList.add(feedback1);
        feedbackList.add(feedback2);

        float avgRating = (feedback1.getRatingStar() + feedback2.getRatingStar()) / feedbackList.size();

        CourseDtoHomeDetail dto1 = CourseDtoHomeDetail.builder().id(1).name("java")
                .price(new BigDecimal("100000")).imageLink("java.png").build();
        CourseDtoHomeDetail dto2 = CourseDtoHomeDetail.builder().id(2).name("css")
                .price(new BigDecimal("200000")).imageLink("css.png").build();
        List<CourseDtoHomeDetail> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(dto1);
        expectedDtoList.add(dto2);

        // Mock behavior of courseRepository.getAllCourses()
        when(courseService.getAllCourses()).thenReturn(courses);

        // Mock behavior of feedbackService.getFeedbackByCourseId()
        when(feedbackService.getFeedbackByCourseId(course1.getId())).thenReturn(feedbackList);
        when(feedbackService.getFeedbackByCourseId(course2.getId())).thenReturn(new ArrayList<>());

        // Mock behavior of courseMapper.courseToCourseDtoHomeDetail()
        when(courseMapper.courseToCourseDtoHomeDetail(course1, avgRating)).thenReturn(dto1);
        when(courseMapper.courseToCourseDtoHomeDetail(course2, 0)).thenReturn(dto2);

        // Call the method to test
        List<CourseDtoHomeDetail> result = courseService.getAllCourseDtoHomeDetails();

        // Verify the result
        assertEquals(expectedDtoList, result);
    }


    @Test
    public void testGetCourseAllById_WithExistingId_ShouldReturnCorrectCourse() {
        // Create the mock Course object
        int courseId = 1;
        Course course = Course.builder().id(1).name("java").description("java java")
                .price(new BigDecimal("100000")).imageLink("java.png").build();

        // Mock behavior of courseRepository.findById
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Call the method to test
        Course result = courseService.getCourseAllById(courseId);

        // Verify that the correct Course object is returned
        assertEquals(course, result);
    }

    @Test
    public void testGetCourseAllById_WithNonExistingId_ShouldReturnNull() {
        // Create a non-existing ID
        int courseId = 10;
        // Mock behavior of courseRepository.findById
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        // Call the method to test, it should return null for non-existing ID
        Course result = courseService.getCourseAllById(courseId);
        // Verify that the result is null for non-existing ID
        assertEquals(null, result);
    }

    @Test
    public void testGetCourseDetailForStudentById_WithExistingId_ShouldReturnCorrectCourseDtoDetailStudent() {
        // Create the mock Course object
        int courseId = 1;
        String courseName = "Java Programming";
        Course course = Course.builder().id(1).name("java").description("java java")
                .price(new BigDecimal("100000")).imageLink("java.png").build();
        // Mock behavior of getCourseAllById
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Call the method to test
        CourseDtoDetailStudent result = courseService.getCourseDetailForStudentById(courseId);

        // Verify that the correct CourseDtoDetailStudent object is returned
        CourseDtoDetailStudent expectedDto = CourseMapper.courseToCourseDtoDetailStudent(course);
        assertEquals(expectedDto, result);
    }

    @Test
    public void testGetCourseDetailForStudentById_WithNonExistingId_ShouldReturnNull() {
        // Create a non-existing ID
        int courseId = 10;

        // Mock behavior of getCourseAllById
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Call the method to test, it should return null for non-existing ID
        CourseDtoDetailStudent result = courseService.getCourseDetailForStudentById(courseId);

        // Verify that the result is null for non-existing ID
        assertEquals(null, result);
    }

    @Test
    public void testSubmitCourse_ShouldSetStatusToZeroAndSaveCourse() {
        // Create the mock Course object

        Course course = Course.builder().id(1).name("java").description("java java")
                .price(new BigDecimal("100000")).imageLink("java.png").build();

        // Call the method to test
        courseService.submitCourse(course);

        // Verify that the status is set to 0
        assertEquals(0, course.getStatus());

        // Verify that the save method is called with the correct Course object
        verify(courseRepository).save(course);
    }
}
