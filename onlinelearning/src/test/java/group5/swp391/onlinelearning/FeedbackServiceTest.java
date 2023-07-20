package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.repository.FeedbackRepositoty;
import group5.swp391.onlinelearning.service.impl.FeedbackService;

public class FeedbackServiceTest {
    @Mock
    private FeedbackRepositoty feedbackRepository;

    @Mock
    private HttpSession session;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testCreateFeedback() {
        // Mock the necessary data
        Course course = Course.builder().id(1).name("java").build();
        int ratingStar = 5;
        String comment = "Great course!";

        User student = User.builder().id(1).name("thai").build();

        // Mock the session attribute

        Mockito.when(session.getAttribute("studentSession")).thenReturn(student);
        // Call the method under test
      feedbackService.createFeedback(course, ratingStar, comment);

        // Verify that the feedback was saved using the repository
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    public void testGetFeedbackDtoRequest_WithExistingFeedback() {
        // Mock the necessary data
        int courseId = 123;
        int studentId = 456;

        // Create a mock Course and User object
        Course course = new Course();
        course.setId(courseId);

        User student = new User();
        student.setId(studentId);

        // Create a mock Feedback object
        Feedback feedback = new Feedback();
        feedback.setId(1);
        feedback.setCourse(course);
        feedback.setStudent(student);
        feedback.setRatingStar(5);
        feedback.setComment("Great course!");

        // Mock the behavior of the repository method to return the mock Feedback
        when(feedbackRepository.getFeedbackDtoRequest(courseId, studentId)).thenReturn(Optional.of(feedback));

        // Call the method under test
        Optional<Feedback> result = feedbackService.getFeedbackDtoRequest(courseId, studentId);

        // Verify the repository method was called with the correct parameters
        verify(feedbackRepository, times(1)).getFeedbackDtoRequest(courseId, studentId);

        // Assert that the result is not empty and contains the expected Feedback object
        assertTrue(result.isPresent());
        assertEquals(feedback, result.get());
    }

    @Test
    public void testGetFeedbackDtoRequest_WithNonExistingFeedback() {
        // Mock the necessary data
        int courseId = 123;
        int studentId = 456;

        // Mock the behavior of the repository method to return an empty Optional
        when(feedbackRepository.getFeedbackDtoRequest(courseId, studentId)).thenReturn(Optional.empty());

        // Call the method under test
        Optional<Feedback> result = feedbackService.getFeedbackDtoRequest(courseId, studentId);

        // Verify the repository method was called with the correct parameters
        verify(feedbackRepository, times(1)).getFeedbackDtoRequest(courseId, studentId);

        // Assert that the result is empty
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateFeedback() {
        // Mock the necessary data
        int courseId = 123;
        int ratingStar = 4;
        String comment = "Nice course!";
        int studentId = 456;

        // Create a mock User object
        User student = new User();
        student.setId(studentId);

        // Mock the behavior of the session.getAttribute method to return the mock User
        when(session.getAttribute("studentSession")).thenReturn(student);

        // Call the method under test
       feedbackService.updateFeedback(courseId, ratingStar, comment);

        // Verify that the session.getAttribute method was called with the correct
        // attribute name
        verify(session, times(1)).getAttribute("studentSession");

        // Verify that the feedbackRepository.updateFeedback method was called with the
        // correct parameters
        verify(feedbackRepository, times(1)).updateFeedback(comment, ratingStar, courseId, studentId);
    }

    @Test
    public void testGetFeedbackByCourseId() {
        // Mock the necessary data
        int courseId = 123;

        // Create some mock Feedback objects
        Feedback feedback1 = new Feedback();
        feedback1.setId(1);
        feedback1.setRatingStar(5);
        feedback1.setComment("Great course!");

        Feedback feedback2 = new Feedback();
        feedback2.setId(2);
        feedback2.setRatingStar(4);
        feedback2.setComment("Nice course!");

        // Create a list of mock Feedback objects
        List<Feedback> feedbackList = new ArrayList<>();
        feedbackList.add(feedback1);
        feedbackList.add(feedback2);

        // Mock the behavior of the repository method to return the list of feedback
        when(feedbackRepository.getFeedbackByCourseId(courseId)).thenReturn(feedbackList);

        // Call the method under test
        List<Feedback> result = feedbackService.getFeedbackByCourseId(courseId);

        // Verify that the repository method was called with the correct parameter
        verify(feedbackRepository, times(1)).getFeedbackByCourseId(courseId);

        // Assert that the returned list contains the correct feedback
        assertEquals(2, result.size());
        assertEquals(feedback1, result.get(0));
        assertEquals(feedback2, result.get(1));
    }

    @Test
    public void testGetFeedbackByCourseId_NoFeedbackFound() {
        // Mock the necessary data
        int courseId = 456;

        // Mock the behavior of the repository method to return an empty list
        when(feedbackRepository.getFeedbackByCourseId(courseId)).thenReturn(new ArrayList<>());

        // Call the method under test
        List<Feedback> result = feedbackService.getFeedbackByCourseId(courseId);

        // Verify that the repository method was called with the correct parameter
        verify(feedbackRepository, times(1)).getFeedbackByCourseId(courseId);

        // Assert that the returned list is empty
        assertEquals(0, result.size());
    }

}
