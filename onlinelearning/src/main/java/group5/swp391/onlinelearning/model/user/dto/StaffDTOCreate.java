package group5.swp391.onlinelearning.model.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class StaffDTOCreate {
    @NotBlank(message = "Name must not empty")
    private String name;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{6,}$", message = "Password must contain at least 6 characters, 1 uppercase character, 1 special character and at least 1 number")
    private String password;
    @Email
    private String email;

}
