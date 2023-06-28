package group5.swp391.onlinelearning.model.dto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;

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
public class CourseDtoAdd {
    @NotNull(message = "Name must not be empty")
    private String courseName;
    @NotNull(message = "Description must not be empty")
    private String description;
    @NotNull(message = "Price must not be empty")
    private BigDecimal price;
    @NotNull(message = "Topic must not be empty")
    private Topic topic;
}
