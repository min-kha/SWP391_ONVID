package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
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

import group5.swp391.onlinelearning.entity.CourseReview;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.repository.CourseReviewRepository;
import group5.swp391.onlinelearning.service.impl.CourseReviewService;
import group5.swp391.onlinelearning.utils.TestDataProvider;

public class CourseReviewServiceTest {

    @Mock
    private CourseReviewRepository courseReviewRepository;

    @InjectMocks
    private CourseReviewService courseReviewService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCourseReviews() {
        List<CourseReview> mockReviews = new ArrayList<>();
        mockReviews.add(TestDataProvider.createSampleCourseReview());
        mockReviews.add(TestDataProvider.createSampleCourseReview());
        when(courseReviewRepository.findAll()).thenReturn(mockReviews);

        List<CourseReview> result = courseReviewService.getCourseReviews();

        assertEquals(result, mockReviews);
    }

    @Test
    public void testAddCourseReview() throws Exception {
        CourseReview courseReview = TestDataProvider.createSampleCourseReview();

        when(courseReviewRepository.findById(courseReview.getId())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> courseReviewService.addCourseReview(courseReview));
        verify(courseReviewRepository, times(1)).save(courseReview);
    }

    @Test
    public void testAddCourseReviewDuplicateId() throws Exception {
        CourseReview courseReview = TestDataProvider.createSampleCourseReview();

        when(courseReviewRepository.findById(courseReview.getId())).thenReturn(Optional.of(courseReview));

        assertThrows(InvalidInputException.class, () -> courseReviewService.addCourseReview(courseReview));
        verify(courseReviewRepository, never()).save(courseReview);
    }

    @Test
    public void testUpdateCourseReview() throws Exception {
        CourseReview courseReview = TestDataProvider.createSampleCourseReview();

        when(courseReviewRepository.findById(courseReview.getId())).thenReturn(Optional.of(courseReview));

        assertDoesNotThrow(() -> courseReviewService.updateCourseReview(courseReview));
        verify(courseReviewRepository, times(1)).save(courseReview);
    }

    @Test
    public void testUpdateCourseReviewNotFound() {
        CourseReview courseReview = TestDataProvider.createSampleCourseReview();

        when(courseReviewRepository.findById(courseReview.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidInputException.class, () -> courseReviewService.updateCourseReview(courseReview));
        verify(courseReviewRepository, never()).save(courseReview);
    }

    @Test
    public void testDeleteCourseReview() {
        int courseReviewId = 1;

        assertDoesNotThrow(() -> courseReviewService.deleteCourseReview(courseReviewId));
        verify(courseReviewRepository, times(1)).deleteById(courseReviewId);
    }
}
