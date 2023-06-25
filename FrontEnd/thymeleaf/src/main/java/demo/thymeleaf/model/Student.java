package demo.thymeleaf.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Student {
    private int id;
    private Boolean isStudent;
    private double salary;
    private String name;
    private String phone;
    private String email;
    private String email1;
    private String email2;
    private String email3;
    private String email4;
    private String email5;
}
