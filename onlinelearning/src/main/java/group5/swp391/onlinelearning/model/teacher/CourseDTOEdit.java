package group5.swp391.onlinelearning.model.teacher;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTOEdit {
    private int id;
    @NotNull(message = "Name must not be empty")
    private String name;
    @NotNull(message = "Description must not be empty")
    private String description;
    @NotNull(message = "Price must not be empty")
    private BigDecimal price;
    private int topic_id;
    private String imageLink;
}
