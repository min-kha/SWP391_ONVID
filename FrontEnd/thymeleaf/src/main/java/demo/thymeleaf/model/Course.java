package demo.thymeleaf.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    private int courseId;
    private String courseName;
    private int status;
    private String description;
    private BigDecimal price;
    private Date date;
    private String topic;
    private long views;
    // private Collection<Feedback> feedbacks;
    // private User teacher;
    // private Collection<Lesson> lessons;
}
