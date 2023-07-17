package group5.swp391.onlinelearning.model.admin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;
    @NotEmpty(message = "Name must not blank")
    private String name;

    @NotEmpty(message = "Email must not blank")
    @Email
    private String email;

    @NotEmpty(message = "Role must not blank")
    private String roleName;

    @NotNull(message = "Status must not null")
    private Boolean status;
}
