package com.iql.javaCRUD.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    @Transactional
    public void create(int age, double cash) {
        // нужно ли писать email тут или сам автоматом чет хызы
    }

}
