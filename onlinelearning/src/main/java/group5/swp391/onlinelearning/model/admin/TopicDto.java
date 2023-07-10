package group5.swp391.onlinelearning.model.admin;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    @NotEmpty(message = "Topic Name must not blank")
    private String name;

    @NotEmpty(message = "Topic Hashtag must not blank")
    private String hashtag;
}
