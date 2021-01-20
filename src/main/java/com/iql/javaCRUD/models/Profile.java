package com.iql.javaCRUD.models;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;

@Entity
@Table(name = "PROFILES")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private int age;

    private Double cash = 0.0;

    public String getUserEmail() {
        return userEmail;
    }

    public int getInc() {
        return inc;
    }

    public void setInc(int inc) {
        this.inc = inc;
    }

    @Column(name = "inc_value")
    private int inc;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    public Profile(int age, double cash, String user_email, int inc) {
        this.age = age;
        this.cash = cash;
        this.userEmail = user_email;
        this.inc = inc;
    }

    public Profile() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }
}
