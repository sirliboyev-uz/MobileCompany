package com.example.mobilecompany.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterUserDTO {
    private Integer who;
    private String fullName;
    private String username;
    private String password;
    private Integer roleType;
}
