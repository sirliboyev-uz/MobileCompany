package com.example.mobilecompany.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SIMCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String simNumber;

    @Column(nullable = false)
    private String passport;

    @ManyToOne
    private Definition definition;

    @Column(nullable = false)
    private String simType;

    @Column(nullable = false)
    private boolean illegalType;

    private double balance;
}
