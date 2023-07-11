package group5.swp391.onlinelearning.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDtoRequest {
    @NotBlank(message = "Comment must not be empty")
    @NotNull
    private String comment;

    @NotNull
    @Min(value = 1, message = "rating star not valid")
    @Max(value = 10, message = "rating star not valid")
    private int ratingStar;
}
