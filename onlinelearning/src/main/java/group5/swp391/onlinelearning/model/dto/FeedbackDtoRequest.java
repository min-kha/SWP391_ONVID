package group5.swp391.onlinelearning.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedbackDtoRequest {
    @NotBlank(message = "Comment must not be empty")
    private String comment;

    @Min(value = 1, message = "rating star not valid")
    @Max(value = 10, message = "rating star not valid")
    private int ratingStar;
}
