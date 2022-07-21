package com.example.mobilecompany.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterSimDTO {
    private Integer who;
    private String fullName;
    private String simNumber;
    private double balance;
    private String passport;
    private Integer definition;
    private boolean illegalType;
}
