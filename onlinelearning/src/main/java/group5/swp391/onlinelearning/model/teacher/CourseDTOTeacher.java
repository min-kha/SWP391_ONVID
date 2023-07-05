package group5.swp391.onlinelearning.model.teacher;

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
public class CourseDTOTeacher {
    private String name;
    private int status;
    private String image;
    private BigDecimal price;
    private String topic;
    private Date date;
    // TODO:Thêm star
}
