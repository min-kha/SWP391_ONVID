package group5.swp391.onlinelearning.model.teacher;

import javax.validation.constraints.NotBlank;

import group5.swp391.onlinelearning.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LessonDtoEditVideo {
    private int id;

    @NotBlank(message = "Title must not be empty")
    private String title;
    @NotBlank(message = "Name must not be empty")
    private String name;

    private Course course;
}
