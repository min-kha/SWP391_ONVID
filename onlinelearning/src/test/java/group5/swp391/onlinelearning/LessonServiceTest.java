package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.model.mapper.LessonMapper;
import group5.swp391.onlinelearning.model.teacher.LessonDtoAdd;
import group5.swp391.onlinelearning.repository.LessonRepository;
import group5.swp391.onlinelearning.service.impl.CourseService;
import group5.swp391.onlinelearning.service.impl.LessonService;

public class LessonServiceTest {
    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonMapper lessonMapper;
    @Mock
    private CourseService courseService;

    @InjectMocks
    private LessonService lessonService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testGetLessonsByCourseId() {
        // Mock the necessary data
        int courseId = 123;

        // Create some mock Lesson objects
        Lesson lesson1 = new Lesson();
        lesson1.setId(101);
        lesson1.setTitle("Lesson 1");

        Lesson lesson2 = new Lesson();
        lesson2.setId(102);
        lesson2.setTitle("Lesson 2");

        // Create a list of mock Lesson objects
        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(lesson1);
        lessonList.add(lesson2);

        // Mock the behavior of the repository method to return the list of lessons
        when(lessonRepository.findAllByCourseId(courseId)).thenReturn(lessonList);

        // Call the method under test
        List<Lesson> result = lessonService.getLessonsByCourseId(courseId);

        // Verify that the repository method was called with the correct parameter
        verify(lessonRepository, times(1)).findAllByCourseId(courseId);

        // Assert that the returned list contains the correct lessons
        assertEquals(2, result.size());
        assertEquals(lesson1, result.get(0));
        assertEquals(lesson2, result.get(1));
    }

    @Test
    public void testGetLessonsByCourseId_NoLessonsFound() {
        // Mock the necessary data
        int courseId = 456;

        // Mock the behavior of the repository method to return an empty list
        when(lessonRepository.findAllByCourseId(courseId)).thenReturn(new ArrayList<>());

        // Call the method under test
        List<Lesson> result = lessonService.getLessonsByCourseId(courseId);

        // Verify that the repository method was called with the correct parameter
        verify(lessonRepository, times(1)).findAllByCourseId(courseId);

        // Assert that the returned list is empty
        assertEquals(0, result.size());
    }

    @Test
    public void testGetLessonById() {
        // Mock the necessary data
        int lessonId = 123;

        // Create a mock Lesson object
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        lesson.setTitle("Sample Lesson");

        // Mock the behavior of the repository method to return the mock Lesson
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));

        // Call the method under test
        Lesson result = lessonService.getLessonById(lessonId);

        // Verify that the repository method was called with the correct parameter
        verify(lessonRepository, times(1)).findById(lessonId);

        // Assert that the returned Lesson is the same as the mock Lesson
        assertEquals(lesson, result);
    }

    @Test
    public void testGetLessonById_LessonNotFound() {
        // Mock the necessary data
        int lessonId = 456;

        // Mock the behavior of the repository method to return an empty Optional
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        // Call the method under test using assertThrows to expect
        // NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> lessonService.getLessonById(lessonId));
    }

    @Test
    public void testAddLessonVideo() {
        // Mock the necessary data
        String fileName = "sample_video.mp4";
        LessonDtoAdd lessonDtoAdd = LessonDtoAdd.builder().title("Sample Lesson").name("java")
                .build();

        Lesson mappedLesson = Lesson.builder().id(101).title("Sample Lesson").document("This is a sample lesson.")
                .video("/videos/sample_video.mp4").build();

        // Mock the behavior of the lessonMapper
        when(lessonMapper.lessionDtoAddtoLessonVideo(lessonDtoAdd, fileName)).thenReturn(mappedLesson);

        // Mock the behavior of the lessonRepository.save method to return the mapped
        // lesson
        when(lessonRepository.save(mappedLesson)).thenReturn(mappedLesson);

        // Call the method under test
        Lesson result = lessonService.addLessonVideo(lessonDtoAdd, fileName);

        // Verify that the lessonMapper was called with the correct parameters
        verify(lessonMapper, times(1)).lessionDtoAddtoLessonVideo(lessonDtoAdd, fileName);

        // Verify that the lessonRepository.save method was called with the mapped
        // lesson
        verify(lessonRepository, times(1)).save(mappedLesson);

        // Assert that the returned Lesson is the same as the mapped Lesson
        assertEquals(mappedLesson, result);
    }

    @Test
    public void testAddLessonDocument() {
        // Mock the necessary data
        String document = "sample_document.pdf";
        LessonDtoAdd lessonDtoAdd = LessonDtoAdd.builder().title("Sample Lesson").name("java")
                .build();

        Lesson mappedLesson = Lesson.builder().id(101).title("Sample Lesson").document("This is a sample lesson.")
                .video("/videos/sample_video.mp4").build();

        // Mock the behavior of the lessonMapper
        when(lessonMapper.lessionDtoAddtoLessonDocument(lessonDtoAdd, document)).thenReturn(mappedLesson);

        // Mock the behavior of the lessonRepository.save method to return the mapped
        // lesson
        when(lessonRepository.save(mappedLesson)).thenReturn(mappedLesson);

        // Call the method under test
        Lesson result = lessonService.addLessonDocument(lessonDtoAdd, document);

        // Verify that the lessonMapper was called with the correct parameters
        verify(lessonMapper, times(1)).lessionDtoAddtoLessonDocument(lessonDtoAdd, document);

        // Verify that the lessonRepository.save method was called with the mapped
        // lesson
        verify(lessonRepository, times(1)).save(mappedLesson);

        // Assert that the returned Lesson is the same as the mapped Lesson
        assertEquals(mappedLesson, result);
    }

    @Test
    public void testIsLessonVideo_WithVideo() {
        // Mock the necessary data
        int lessonId = 101;

        // Create a mock Lesson object with a video
        Lesson lessonWithVideo = new Lesson();
        lessonWithVideo.setId(lessonId);
        lessonWithVideo.setVideo("sample_video.mp4");

        // Mock the behavior of the lessonRepository.findById method to return the
        // lessonWithVideo wrapped in Optional
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lessonWithVideo));

        // Call the method under test
        boolean result = lessonService.isLessonVideo(lessonId);

        // Verify that the lessonRepository.findById method was called with the correct
        // parameter
        verify(lessonRepository, times(1)).findById(lessonId);

        // Assert that the result is true since the lesson has a video
        assertTrue(result);
    }

    @Test
    public void testIsLessonVideo_NoVideo() {
        // Mock the necessary data
        int lessonId = 102;

        // Mock the behavior of the lessonRepository.findById method to return an empty
        // Optional
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        // Call the method under test
        boolean result = lessonService.isLessonVideo(lessonId);

        // Verify that the lessonRepository.findById method was called with the correct
        // parameter
        verify(lessonRepository, times(1)).findById(lessonId);

        // Assert that the result is false since the lesson doesn't have a video
        assertFalse(result);

        Lesson lesson = lessonService.getLessonById(lessonId);
        assertNull(lesson);
        System.out.println("Lesson: " + lesson);
    }

    @Test
    public void testIsLessonOfCourse_WithMatchingCourseId() {
        // Mock the necessary data
        int lessonId = 101;
        int courseId = 201;

        // Create a mock Lesson object with a matching courseId
        Course course = new Course();
        course.setId(courseId);

        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        lesson.setCourse(course);

        // Mock the behavior of the lessonRepository.findById method to return the
        // lesson wrapped in Optional
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));

        // Call the method under test
        boolean result = lessonService.isLessonOfCourse(lessonId, courseId);

        // Verify that the lessonRepository.findById method was called with the correct
        // parameter
        verify(lessonRepository, times(1)).findById(lessonId);

        // Assert that the result is true since the lesson's courseId matches the
        // provided courseId
        assertTrue(result);
    }

    @Test
    public void testIsLessonOfCourse_WithDifferentCourseId() {
        // Mock the necessary data
        int lessonId = 102;
        int courseId = 202;

        // Create a mock Lesson object with a different courseId
        Course course = new Course();
        course.setId(courseId);

        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        lesson.setCourse(course);

        // Mock the behavior of the lessonRepository.findById method to return the
        // lesson wrapped in Optional
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));

        // Call the method under test
        boolean result = lessonService.isLessonOfCourse(lessonId, courseId);

        // Verify that the lessonRepository.findById method was called with the correct
        // parameter
        verify(lessonRepository, times(1)).findById(lessonId);

        // Assert that the result is false since the lesson's courseId does not match
        // the provided courseId
        assertFalse(result);
    }

    @Test
    public void testIsLessonOfCourse_LessonNotFound() {
        // Mock the necessary data
        int lessonId = 103;
        int courseId = 203;

        // Mock the behavior of the lessonRepository.findById method to return an empty
        // Optional
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.empty());

        // Call the method under test
        boolean result = lessonService.isLessonOfCourse(lessonId, courseId);

        // Verify that the lessonRepository.findById method was called with the correct
        // parameter
        verify(lessonRepository, times(1)).findById(lessonId);

        // Assert that the result is false since the lesson was not found
        assertFalse(result);
    }

    @Test
    public void testUpdateLesson() {
        // Mock the necessary data
        Lesson lessonToUpdate = new Lesson();
        lessonToUpdate.setId(101);
        lessonToUpdate.setTitle("Updated Lesson Title");
        // Set other necessary properties of the lesson

        // Mock the behavior of the lessonRepository.save method
        when(lessonRepository.save(lessonToUpdate)).thenReturn(lessonToUpdate);

        // Call the method under test
        Lesson resultLesson = lessonService.updateLesson(lessonToUpdate);

        // Verify that the lessonRepository.save method was called with the correct
        // Lesson object
        verify(lessonRepository, times(1)).save(lessonToUpdate);

        // Assert that the returned Lesson object is the same as the input
        // lessonToUpdate
        assertEquals(lessonToUpdate, resultLesson);
    }

    @Test
    public void testDeleteLessonById() {
        // Mock the necessary data
        Integer lessonId = 101;

        // Create a mock Lesson object
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        lesson.setTitle("Sample Lesson");
        // Set other necessary properties of the lesson

        // Mock the behavior of the lessonRepository.findById method to return the
        // lesson wrapped in Optional
        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));

        // Call the method under test
        Lesson resultLesson = lessonService.deleteLessonById(lessonId);

        // Verify that the lessonRepository.findById method was called with the correct
        // parameter
        verify(lessonRepository, times(1)).findById(lessonId);

        // Verify that the lessonRepository.save method was called with the correct
        // Lesson object
        verify(lessonRepository, times(1)).save(lesson);

        // Assert that the returned Lesson object is the same as the mocked Lesson
        // object
        assertEquals(lesson, resultLesson);
    }

    // @Test
    // public void
    // testDeleteLessonByIdOfTeacher_CourseNotPublishedOrSubmittedOrInProgress() {
    // // Mock the necessary data
    // Integer lessonId = 101;
    // Integer courseId = 201;

    // // Create a mock Course object
    // Course course = new Course();
    // course.setId(courseId);
    // course.setStatus(2); // Assuming status 2 means something other than
    // "published", "submitted", or "in
    // // progress"

    // // Mock the behavior of the courseService.getCourseById method to return the
    // // mock Course object
    // when(courseService.getCourseById(courseId)).thenReturn(course);

    // // Call the method under test
    // Lesson resultLesson = lessonService.deleteLessonByIdOfTeacher(lessonId,
    // courseId);

    // // Verify that the courseService.getCourseById method was called with the
    // // correct parameter
    // verify(courseService, times(1)).getCourseById(courseId);

    // // Verify that the deleteLessonById method was not called since the course
    // // status is not one of the valid ones
    // verify(lessonService, never()).deleteLessonById(lessonId);

    // // Assert that the returned Lesson object is null since no deletion is
    // performed
    // assertNull(resultLesson);
    // }

    // @Test
    // public void
    // testDeleteLessonByIdOfTeacher_CoursePublishedOrSubmittedOrInProgress() {
    // // Mock the necessary data
    // Integer lessonId = 101;
    // Integer courseId = 201;

    // // Create a mock Course object
    // Course course = new Course();
    // course.setId(courseId);
    // course.setStatus(3); // Assuming status 3 means "published", "submitted", or
    // "in progress"

    // // Mock the behavior of the courseService.getCourseById method to return the
    // // mock Course object
    // when(courseService.getCourseById(courseId)).thenReturn(course);

    // // Mock the behavior of the lessonRepository.save method to return a Lesson
    // // object
    // Lesson deletedLesson = new Lesson();
    // when(lessonService.deleteLessonById(lessonId)).thenReturn(deletedLesson);

    // // Call the method under test
    // Lesson resultLesson = lessonService.deleteLessonByIdOfTeacher(lessonId,
    // courseId);

    // // Verify that the courseService.getCourseById method was called with the
    // // correct parameter
    // verify(courseService, times(1)).getCourseById(courseId);

    // // Verify that the deleteLessonById method was called since the course status
    // is
    // // one of the valid ones
    // verify(lessonService, times(1)).deleteLessonById(lessonId);

    // // Assert that the returned Lesson object is the same as the mocked
    // // deletedLesson
    // assertEquals(deletedLesson, resultLesson);
    // }
}
