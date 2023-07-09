package group5.swp391.onlinelearning.model.dto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import group5.swp391.onlinelearning.entity.Feedback;
import group5.swp391.onlinelearning.entity.Lession;
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
public class CourseDtoDetail {
    private int id;
    private String name;
    private int status;
    private String description;
    private BigDecimal price;
    private Date date;
    private Topic topic;
    private long views;
    private String image;
    private Collection<Feedback> feedbacks;
    private User teacher;
    private Collection<Lession> lessions;
}
