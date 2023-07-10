package group5.swp391.onlinelearning.model.teacher;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDtoAdd {
    @NotNull(message = "Title must not be empty")
    private String title;
    @NotNull(message = "Name must not be empty")
    private String name;

    private int courseId;
}