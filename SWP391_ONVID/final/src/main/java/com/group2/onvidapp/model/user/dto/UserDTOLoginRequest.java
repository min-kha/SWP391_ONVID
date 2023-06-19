package com.group2.onvidapp.model.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOLoginRequest {
    private String email;
    private String passWord;
}
