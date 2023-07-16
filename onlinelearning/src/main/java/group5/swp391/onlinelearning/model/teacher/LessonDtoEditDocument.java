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

public class LessonDtoEditDocument {
    private int id;

    @NotBlank(message = "Title must not be empty")
    private String title;
    @NotBlank(message = "Name must not be empty")
    private String name;
    @NotBlank(message = "Document must not be empty")
    private String document;

    private Course course;
}
