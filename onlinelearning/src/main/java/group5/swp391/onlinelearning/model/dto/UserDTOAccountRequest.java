package group5.swp391.onlinelearning.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class UserDTOAccountRequest {
    private int id;
    private String name;
    private String email;
    private String role;
    private boolean status;
}
