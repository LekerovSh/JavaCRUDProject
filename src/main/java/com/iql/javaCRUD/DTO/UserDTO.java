package com.iql.javaCRUD.DTO;

import com.iql.javaCRUD.models.User;

import javax.validation.constraints.NotNull;

public class UserDTO {
    @NotNull
    private String email;

    @NotNull
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toUser() {
        return new User(this.email, this.password);
    }
}
