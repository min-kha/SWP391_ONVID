package group5.swp391.onlinelearning.model.user.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOReponse {
    private int userId;
    private Date date;
    private String userName;
    private String email;
    private int role;
    private String token;
}
