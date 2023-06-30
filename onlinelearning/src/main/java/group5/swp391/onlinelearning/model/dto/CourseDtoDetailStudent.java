package group5.swp391.onlinelearning.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDtoDetailStudent {
    private int id;
    private String name;
    private String description;
    private String imageLink;
    private BigDecimal price;
    private Date date;
}
