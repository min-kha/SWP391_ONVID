package group5.swp391.onlinelearning.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import group5.swp391.onlinelearning.entity.CV;
import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.CourseReview;
import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.teacher.CourseDTOAdd;
import group5.swp391.onlinelearning.model.teacher.CourseDTOEdit;

public class TestDataProvider {
    public static CV createSampleCV() {
        int randomNumber = new Random().nextInt(100000);
        return CV.builder()
                .id(randomNumber)
                .pdfLink("pdfLink " + randomNumber + ".pdf")
                .build();
    }

    public static Topic createSampleTopic() {
        int randomNumber = new Random().nextInt(100000);
        return Topic.builder()
                .id(randomNumber)
                .name("Topic " + randomNumber)
                .hashtag("hashtag" + randomNumber)
                .build();
    }

    public static User createSampleUser() {
        int randomNumber = new Random().nextInt(100000);
        return User.builder()
                .id(randomNumber)
                .name("User " + randomNumber)
                .email("email" + randomNumber + "@example.com")
                .build();
    }

    public static Course createSampleCourse() {
        int randomNumber = new Random().nextInt(100000);
        return Course.builder()
                .id(randomNumber)
                .name("Course " + randomNumber)
                .build();
    }

    // COMMENT BY HUNG: LOI

    // public static CourseDTOEdit createSampleCourseDTOEdit() {
    // int randomNumber = new Random().nextInt(100000);
    // return new CourseDTOEdit(randomNumber, "Course " + randomNumber, "description
    // " + randomNumber,
    // BigDecimal.valueOf(randomNumber), 1, null);
    // }

    public static CourseReview createSampleCourseReview() {
        int randomNumber = new Random().nextInt(100000);
        return CourseReview.builder()
                .id(randomNumber)
                .comment("Comment " + randomNumber)
                .time(new Date())
                .build();
    }
}
