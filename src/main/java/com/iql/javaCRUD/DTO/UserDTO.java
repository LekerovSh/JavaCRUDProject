package com.iql.javaCRUD.DTO;

import com.iql.javaCRUD.models.Phone;
import com.iql.javaCRUD.models.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class UserDTO {
    private String email;
    private Set<Phone> phones;

    public UserDTO(User user) {
        this.email = user.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }
}
