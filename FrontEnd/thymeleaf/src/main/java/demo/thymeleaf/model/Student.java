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
    private String address;
    private String teacher;
    private String choHung;
    private String Kha;
    private String Wifes;
}
