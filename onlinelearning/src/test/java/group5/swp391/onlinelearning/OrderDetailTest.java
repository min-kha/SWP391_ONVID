package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.repository.FeedbackRepositoty;
import group5.swp391.onlinelearning.repository.OrderDetailRepository;
import group5.swp391.onlinelearning.service.impl.CourseService;
import group5.swp391.onlinelearning.service.impl.FeedbackService;
import group5.swp391.onlinelearning.service.impl.OrderService;

public class OrderDetailTest {
    @Mock
    private FeedbackRepositoty feedbackRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private CourseService courseService;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
