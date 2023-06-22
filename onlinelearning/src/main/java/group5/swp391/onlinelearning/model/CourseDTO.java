package group5.swp391.onlinelearning.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.entity.Lesson;
import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private int courseId;
    private String courseName;
    private int status;
    private String description;
    private BigDecimal price;
    private Date date;
    private Topic topic;
    private long views;
    private Collection<Feedback> feedbacks;
    private User teacher;
    private Collection<Lesson> lessons;
}
