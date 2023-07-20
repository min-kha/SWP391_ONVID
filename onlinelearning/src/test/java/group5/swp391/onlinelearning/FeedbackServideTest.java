package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.repository.FeedbackRepositoty;
import group5.swp391.onlinelearning.service.impl.FeedbackService;

public class FeedbackServideTest {
    @Mock
    private FeedbackRepositoty feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private MockHttpSession session;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateFeedback() {
        // Mock the necessary data
        Course course = new Course();
        int ratingStar = 4;
        String comment = "Great course!";

        // Call the method under test
        feedbackService.createFeedback(course, ratingStar, comment);

        // Verify that session.getAttribute was called to get the student
        verify(session, times(1)).getAttribute("studentSession");

        // Verify that feedbackRepository.save method was called with the correct
        // Feedback object
        verify(feedbackRepository, times(1)).save(any(Feedback.class));

    }

    @Test
    public void testGetFeedbackDtoRequest() {
        // Mock the necessary data
        int courseId = 101;
        int studentId = 201;

        // Create a mock Feedback object
        Feedback feedback = new Feedback();
        feedback.setCourse(new Course());
        feedback.setStudent(new User());
        feedback.setRatingStar(4);
        feedback.setComment("Great course!");

        // Mock the behavior of the feedbackRepository.getFeedbackDtoRequest method to
        // return the mock Feedback object
        when(feedbackRepository.getFeedbackDtoRequest(courseId, studentId)).thenReturn(Optional.of(feedback));

        // Call the method under test
        Optional<Feedback> resultFeedback = feedbackService.getFeedbackDtoRequest(courseId, studentId);

        // Verify that feedbackRepository.getFeedbackDtoRequest method was called with
        // the correct parameters
        verify(feedbackRepository, times(1)).getFeedbackDtoRequest(courseId, studentId);

        // Assert that the returned Optional contains the correct Feedback object
        assertTrue(resultFeedback.isPresent());
        assertEquals(feedback, resultFeedback.get());
    }

    @Test
    public void testGetFeedbackDtoRequest_FeedbackNotFound() {
        // Mock the necessary data
        int courseId = 101;
        int studentId = 201;

        // Mock the behavior of the feedbackRepository.getFeedbackDtoRequest method to
        // return an empty Optional
        when(feedbackRepository.getFeedbackDtoRequest(courseId, studentId)).thenReturn(Optional.empty());

        // Call the method under test
        Optional<Feedback> resultFeedback = feedbackService.getFeedbackDtoRequest(courseId, studentId);

        // Verify that feedbackRepository.getFeedbackDtoRequest method was called with
        // the correct parameters
        verify(feedbackRepository, times(1)).getFeedbackDtoRequest(courseId, studentId);

        // Assert that the returned Optional is empty since feedback was not found
        assertFalse(resultFeedback.isPresent());
    }

    @Test
    public void testUpdateFeedback() {
        // Mock the necessary data
        int courseId = 101;
        int ratingStar = 4;
        String comment = "Initial feedback";

        // Create a mock User object
        User student = new User();
        student.setId(201);

        // Create a mock Feedback object
        Feedback feedback = new Feedback();
        feedback.setCourse(new Course());
        feedback.setStudent(student);
        feedback.setRatingStar(4);
        feedback.setComment("Initial feedback");

        // Mock the behavior of session.getAttribute to return the mock User object
        when(session.getAttribute("studentSession")).thenReturn(student);

        // Mock the behavior of feedbackRepository.getFeedbackDtoRequest method to
        // return the mock Feedback object
        when(feedbackRepository.getFeedbackDtoRequest(courseId, student.getId())).thenReturn(Optional.of(feedback));

        // Call the method under test
        Optional<Feedback> resultFeedback = feedbackService.updateFeedback(courseId, ratingStar, comment);

        // Verify that session.getAttribute method was called to get the student
        verify(session, times(1)).getAttribute("studentSession");

        // Verify that feedbackRepository.updateFeedback method was called with the
        // correct parameters
        verify(feedbackRepository, times(1)).updateFeedback(comment, ratingStar, courseId, student.getId());

        // Verify that feedbackRepository.getFeedbackDtoRequest method was called with
        // the correct parameters
        verify(feedbackRepository, times(1)).getFeedbackDtoRequest(courseId, student.getId());

        // Assert that the returned Optional contains the updated Feedback object
        assertTrue(resultFeedback.isPresent());
        assertEquals(ratingStar, resultFeedback.get().getRatingStar());
        assertEquals(comment, resultFeedback.get().getComment());
    }

    @Test
    public void testUpdateFeedback_FeedbackNotFound() {
        // Mock the necessary data
        int courseId = 101;
        int ratingStar = 4;
        String comment = "Updated feedback";

        // Create a mock User object
        User student = new User();
        student.setId(201);

        // Mock the behavior of session.getAttribute to return the mock User object
        when(session.getAttribute("studentSession")).thenReturn(student);

        // Mock the behavior of feedbackRepository.getFeedbackDtoRequest method to
        // return an empty Optional
        when(feedbackRepository.getFeedbackDtoRequest(courseId, student.getId())).thenReturn(Optional.empty());

        // Call the method under test
        Optional<Feedback> resultFeedback = feedbackService.updateFeedback(courseId, ratingStar, comment);

        // Verify that session.getAttribute method was called to get the student
        verify(session, times(1)).getAttribute("studentSession");

        // Verify that feedbackRepository.updateFeedback method was called with the
        // correct parameters
        verify(feedbackRepository, times(1)).updateFeedback(comment, ratingStar, courseId, student.getId());

        // Verify that feedbackRepository.getFeedbackDtoRequest method was called with
        // the correct parameters
        verify(feedbackRepository, times(1)).getFeedbackDtoRequest(courseId, student.getId());

        // Assert that the returned Optional is empty since feedback was not found
        assertFalse(resultFeedback.isPresent());
    }

    @Test
    public void testGetFeedbackByCourseId() {
        // Mock the necessary data
        int courseId = 101;

        // Create a list of mock Feedback objects
        List<Feedback> feedbackList = new ArrayList<>();
        feedbackList.add(new Feedback());
        feedbackList.add(new Feedback());
        feedbackList.add(new Feedback());

        // Mock the behavior of feedbackRepository.getFeedbackByCourseId method to
        // return the mock Feedback list
        when(feedbackRepository.getFeedbackByCourseId(courseId)).thenReturn(feedbackList);

        // Call the method under test
        List<Feedback> resultFeedbackList = feedbackService.getFeedbackByCourseId(courseId);

        // Verify that feedbackRepository.getFeedbackByCourseId method was called with
        // the correct parameter
        verify(feedbackRepository, times(1)).getFeedbackByCourseId(courseId);

        // Assert that the returned list of Feedback objects is not null and has the
        // correct size
        assertNotNull(resultFeedbackList);
        assertEquals(feedbackList.size(), resultFeedbackList.size());

        // You can add more assertions to validate the contents of the
        // resultFeedbackList
    }

    @Test
    public void testGetFeedbackByCourseId_EmptyList() {
        // Mock the necessary data
        int courseId = 101;

        // Mock the behavior of feedbackRepository.getFeedbackByCourseId method to
        // return an empty list
        when(feedbackRepository.getFeedbackByCourseId(courseId)).thenReturn(new ArrayList<>());

        // Call the method under test
        List<Feedback> resultFeedbackList = feedbackService.getFeedbackByCourseId(courseId);

        // Verify that feedbackRepository.getFeedbackByCourseId method was called with
        // the correct parameter
        verify(feedbackRepository, times(1)).getFeedbackByCourseId(courseId);

        // Assert that the returned list of Feedback objects is empty
        assertTrue(resultFeedbackList.isEmpty());
    }

}
