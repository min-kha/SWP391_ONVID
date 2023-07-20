package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Learn;
import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.repository.LearnRepository;
import group5.swp391.onlinelearning.service.impl.LearnService;
import group5.swp391.onlinelearning.service.impl.LessonService;

public class LearnServideTest {
    @Mock
    private LessonService lessonService;

    @Mock
    private LearnRepository learnRepository;

    @InjectMocks
    private LearnService learnService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testSetLearnDefault() {
        // Mock the necessary data
        User student = new User();
        student.setId(1);

        Course course1 = new Course();
        course1.setId(101);

        Course course2 = new Course();
        course2.setId(102);

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        Lesson lesson1 = new Lesson();
        lesson1.setId(201);

        Lesson lesson2 = new Lesson();
        lesson2.setId(202);

        List<Lesson> lessons1 = new ArrayList<>();
        lessons1.add(lesson1);

        List<Lesson> lessons2 = new ArrayList<>();
        lessons2.add(lesson2);

        // Mock the behavior of the lessonService.getLessonsByCourseId method to return
        // lessons
        when(lessonService.getLessonsByCourseId(course1.getId())).thenReturn(lessons1);
        when(lessonService.getLessonsByCourseId(course2.getId())).thenReturn(lessons2);

        // Call the method under test
        learnService.setLearnDefault(courses, student);

        // Verify that the lessonService.getLessonsByCourseId method was called for each
        // course
        verify(lessonService, times(1)).getLessonsByCourseId(course1.getId());
        verify(lessonService, times(1)).getLessonsByCourseId(course2.getId());

        // Verify that the learnRepository.save method was called for each lesson
        verify(learnRepository, atLeastOnce()).save(any(Learn.class));
    }

    @Test
    public void testGetListLearnByLessonIdAndStudentId() {
        // Mock the necessary data
        User student = new User();
        student.setId(1);

        Lesson lesson1 = new Lesson();
        lesson1.setId(101);

        Lesson lesson2 = new Lesson();
        lesson2.setId(102);

        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson1);
        lessons.add(lesson2);

        Learn learn1 = new Learn();
        learn1.setId(201);
        learn1.setLesson(lesson1);
        learn1.setStudent(student);
        learn1.setStatus(false);

        Learn learn2 = new Learn();
        learn2.setId(202);
        learn2.setLesson(lesson2);
        learn2.setStudent(student);
        learn2.setStatus(true);

        List<Learn> mockLearnList = new ArrayList<>();
        mockLearnList.add(learn1);
        mockLearnList.add(learn2);

        // Mock the behavior of the learnRepository.getListLearnByLessonIdAndStudentId
        // method
        when(learnRepository.getListLearnByLessonIdAndStudentId(lesson1.getId(), student.getId()))
                .thenReturn(learn1);
        when(learnRepository.getListLearnByLessonIdAndStudentId(lesson2.getId(), student.getId()))
                .thenReturn(learn2);

        // Call the method under test
        List<Learn> result = learnService.getListLearnByLessonIdAndStudentId(lessons, student);

        // Verify that the learnRepository.getListLearnByLessonIdAndStudentId method was
        // called for each lesson
        verify(learnRepository, times(1)).getListLearnByLessonIdAndStudentId(lesson1.getId(), student.getId());
        verify(learnRepository, times(1)).getListLearnByLessonIdAndStudentId(lesson2.getId(), student.getId());

        // Assert the size and content of the returned list
        assertEquals(2, result.size());
        assertEquals(learn1, result.get(0));
        assertEquals(learn2, result.get(1));
    }

    @Test
    public void testChangeLearnStatus() {
        // Mock the necessary data
        boolean newStatus = true;
        int lessonId = 123;
        int studentId = 456;

        // Call the method under test
        learnService.changeLearnStatus(newStatus, lessonId, studentId);

        // Verify that the learnRepository.changeLearnStatus method was called with the
        // correct parameters
        verify(learnRepository, times(1)).changeLearnStatus(newStatus, lessonId, studentId);
    }
}
