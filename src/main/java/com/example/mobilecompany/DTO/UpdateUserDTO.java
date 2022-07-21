package com.example.mobilecompany.DTO;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private String username;
    private String password;
    private String newUsername;
    private String newPassword;
    private String newFullName;
}
