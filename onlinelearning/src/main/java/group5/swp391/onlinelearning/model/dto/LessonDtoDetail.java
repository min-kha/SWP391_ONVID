package group5.swp391.onlinelearning.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDtoDetail {
    private int id;
    private String status;
    private int index;
}
