package com.iql.javaCRUD.models;

import javax.persistence.*;

public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private int age;

    private Double cash = 0.0;

    @OneToOne(targetEntity = User.class)
    @Column(name = "user_email", nullable = false)
    private String userEmail;
}
