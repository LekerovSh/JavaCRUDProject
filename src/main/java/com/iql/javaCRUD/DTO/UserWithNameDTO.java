package com.iql.javaCRUD.DTO;

import com.iql.javaCRUD.models.User;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class UserWithNameDTO {

    @ApiModelProperty(position = 0)
    @NotNull
    private String name;
    @ApiModelProperty(position = 1)
    @NotNull
    private String email;
    @ApiModelProperty(position = 2)
    @NotNull
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        return new User(this.name, this.email, this.password);
    }
}

