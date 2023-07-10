package group5.swp391.onlinelearning.model.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTORegisterRequest {

    @Email(message = "Email not true")
    @NotBlank(message = "Email must not be empty")
    private String email;
    @NotBlank(message = "Name must not be empty")
    private String name;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{6,}$", message = "Password must contain at least 6 characters, 1 uppercase character, 1 special character and at least 1 number")
    private String password;
    @NotBlank(message = "Re-Password must not be empty")
    private String rePassword;
}
