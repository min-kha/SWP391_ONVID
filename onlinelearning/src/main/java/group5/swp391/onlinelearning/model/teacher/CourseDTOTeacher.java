package group5.swp391.onlinelearning.model.teacher;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import group5.swp391.onlinelearning.entity.CourseReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CourseDTOTeacher {
    private int id;
    private String name;
    private int status;
    private String image;
    private BigDecimal price;
    private String topic;
    private Date date;
    private String description;
    private boolean isReviews;
    // TODO:Thêm star
}
