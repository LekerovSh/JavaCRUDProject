package com.iql.javaCRUD.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "PHONES")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String value;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    public Phone() {

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Phone(String value, String userEmail) {
        this.value = value;
        this.userEmail = userEmail;
    }
}
