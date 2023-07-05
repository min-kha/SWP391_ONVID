package group5.swp391.onlinelearning.model.admin;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TopicDTO {
    @NotBlank(message = "Topic Name must not blank")
    private String name;
    @NotBlank(message = "Topic Hashtag must not blank")
    private String hashtag;
}
