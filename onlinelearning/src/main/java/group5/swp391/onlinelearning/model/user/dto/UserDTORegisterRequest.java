package group5.swp391.onlinelearning.model.user.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTORegisterRequest {

    @NotNull(message = "Email must not be empty")
    @Email(message = "Email not true")
    private String email;
    @NotNull(message = "Name must not be empty")
    private String name;
    @NotNull(message = "dob must not be empty")
    private Date dob;
    @NotNull(message = "password must not be empty")
    private String password;
    @NotNull(message = "rePassword must not be empty")
    private String rePassword;
}
