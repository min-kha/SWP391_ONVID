package group5.swp391.onlinelearning.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOLoginRequest {

    @Email(message = "Email is not valid")
    private String email;

    // viết regex với yêu cầu tối thiểu 6 ký tự,
    // 1 ký tự viết hoa, 1 ký tự đặc biệt,
    // ít nhất 1 chữ số và các ký tự nằm chỗ nào cũng được
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{6,}$", message = "Password not true")
    private String password;

}
