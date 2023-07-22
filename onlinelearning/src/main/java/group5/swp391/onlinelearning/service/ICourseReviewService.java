package group5.swp391.onlinelearning.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import group5.swp391.onlinelearning.entity.CourseReview;

public interface ICourseReviewService {

    public List<CourseReview> getCourseReviews();

    public void addCourseReview(@NotNull CourseReview courseReview) throws Exception;

    public void deleteCourseReview(int id);

    public void updateCourseReview(@NotNull CourseReview courseReview) throws Exception;

    public CourseReview getCourseReviewById(int id);

    public List<CourseReview> getCourseReview(int teacherId);

}
