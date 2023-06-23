package group5.swp391.onlinelearning.model.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOLoginRequest {

    @Email(message = "Email is not valid")
    private String email;

    // viết regex với yêu cầu tối thiểu 6 ký tự,
    // 1 ký tự viết hoa, 1 ký tự đặc biệt,
    // ít nhất 1 chữ số và các ký tự nằm chỗ nào cũng được
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{6,}$", message = "Password must contain at least 6 characters, 1 uppercase character, 1 special character and at least 1 number")
    private String password;

}
