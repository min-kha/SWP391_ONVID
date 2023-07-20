package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import group5.swp391.onlinelearning.repository.CourseReviewRepository;
import group5.swp391.onlinelearning.service.impl.CourseReviewService;

public class CourseReviewTest {
    @Mock
    private CourseReviewRepository courseReviewRepository;

    @InjectMocks
    private CourseReviewService courseReviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCourseReviews() {

        List<CourseReview> courseReviews = new ArrayList<>();
        courseReviews.add(new CourseReview());
        courseReviews.add(new CourseReview());
        courseReviews.add(new CourseReview());

        when(courseReviewRepository.findAll()).thenReturn(courseReviews);

        List<CourseReview> resultCourseReviews = courseReviewService.getCourseReviews();

        verify(courseReviewRepository, times(1)).findAll();

        assertNotNull(resultCourseReviews);
        assertEquals(courseReviews.size(), resultCourseReviews.size());

    }

    @Test
    public void testGetCourseReviews_EmptyList() {
        when(courseReviewRepository.findAll()).thenReturn(new ArrayList<>());

        List<CourseReview> resultCourseReviews = courseReviewService.getCourseReviews();
        verify(courseReviewRepository, times(1)).findAll();

        assertTrue(resultCourseReviews.isEmpty());
    }

    @Test
    public void testAddCourseReview() throws Exception {
        CourseReview courseReview = new CourseReview();
        courseReview.setId(101);

        when(courseReviewRepository.findById(courseReview.getId())).thenReturn(Optional.empty());
        when(courseReviewRepository.save(courseReview)).thenReturn(courseReview);
        courseReviewService.addCourseReview(courseReview);

        verify(courseReviewRepository, times(1)).findById(courseReview.getId());
        verify(courseReviewRepository, times(1)).save(courseReview);
    }

    @Test
    public void testDeleteCourseReview() {
        int reviewId = 101;

        courseReviewService.deleteCourseReview(reviewId);

        verify(courseReviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    public void testUpdateCourseReview() throws Exception {
        CourseReview updatingCourseReview = new CourseReview();
        updatingCourseReview.setId(101);

        when(courseReviewRepository.findById(updatingCourseReview.getId()))
                .thenReturn(Optional.of(updatingCourseReview));
        when(courseReviewRepository.save(updatingCourseReview)).thenReturn(updatingCourseReview);

        courseReviewService.updateCourseReview(updatingCourseReview);

        verify(courseReviewRepository, times(1)).findById(updatingCourseReview.getId());
        verify(courseReviewRepository, times(1)).save(updatingCourseReview);
    }

    @Test
    public void testGetCourseReviewById() {
        int reviewId = 101;

        CourseReview courseReview = new CourseReview();
        courseReview.setId(reviewId);
        when(courseReviewRepository.findById(reviewId)).thenReturn(Optional.of(courseReview));

        CourseReview resultCourseReview = courseReviewService.getCourseReviewById(reviewId);

        verify(courseReviewRepository, times(1)).findById(reviewId);
        assertNotNull(resultCourseReview);
    }

    @Test
    public void testGetCourseReviewById_NotFound() {
        int reviewId = 101;

        when(courseReviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        CourseReview resultCourseReview = courseReviewService.getCourseReviewById(reviewId);

        verify(courseReviewRepository, times(1)).findById(reviewId);
        assertNull(resultCourseReview);
    }

}
