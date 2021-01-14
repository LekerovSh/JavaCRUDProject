package com.iql.javaCRUD.models;

import javax.persistence.*;

public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String value;

    @OneToMany(targetEntity = User.class)
    @Column(name = "user_email", nullable = false)
    private String userEmail;
}
