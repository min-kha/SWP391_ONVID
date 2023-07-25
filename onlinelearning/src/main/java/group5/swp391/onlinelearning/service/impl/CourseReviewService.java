package group5.swp391.onlinelearning.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.CourseReview;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.repository.CourseReviewRepository;
import group5.swp391.onlinelearning.service.ICourseReviewService;

@Service
public class CourseReviewService implements ICourseReviewService {
    @Autowired
    CourseReviewRepository courseReviewRepository;

    public List<CourseReview> getCourseReviews() {
        return courseReviewRepository.findAll();
    }

    @Override
    public void addCourseReview(@NotNull CourseReview courseReview) throws Exception {
        if (courseReviewRepository.findById(courseReview.getId()).isPresent()) {
            throw new InvalidInputException("", "courseReview.duplicate", "Course review is already exists");
        }
        courseReviewRepository.save(courseReview);
    }

    @Override
    public void deleteCourseReview(int id) {
        courseReviewRepository.deleteById(id);
    }

    @Override
    public void updateCourseReview(@NotNull CourseReview updatingCourseReview) throws Exception {
        Optional<CourseReview> exitingCourseReview = courseReviewRepository.findById(updatingCourseReview.getId());
        if (exitingCourseReview.isPresent()) {
            courseReviewRepository.save(updatingCourseReview);
        } else {
            throw new InvalidInputException("id", "courseReview.notfound", "Course review not found");
        }
    }

    @Override
    public CourseReview getCourseReviewById(int id) {
        return courseReviewRepository.findById(id).orElse(null);
    }

    // By Hung: GET 5 Review Early by teacherid
    @Override
    public List<CourseReview> getCourseReview(int teacherId) {
        List<CourseReview> list = courseReviewRepository.findDistinctByMaxTime();
        for (CourseReview review : list) {
            if (review.getCourse().getTeacher().getId() != teacherId) {
                list.remove(list);
                if (list.size() == 5) {
                    return list;
                }
            }
        }
        return list;
    }

}
