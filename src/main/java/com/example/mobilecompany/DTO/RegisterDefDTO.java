package com.example.mobilecompany.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDefDTO {
    private Integer who;
    private String name;
    private String info;
    private double price;
    private double toPriceDef;
    private double megabyte;
    private double minute;
    private double sms;
}
