package com.iql.javaCRUD.services;

import com.iql.javaCRUD.models.Phone;
import com.iql.javaCRUD.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PhoneService {

    @Autowired
    PhoneRepository phoneRepository;

    @Transactional
    public void create(String value, String user_email) {
        Phone phone = new Phone(value, user_email);
        phoneRepository.save(phone);
        System.out.println("USER_EMAIL: " + user_email);
    }

    @Transactional
    public void delete(String phone) {
        phoneRepository.deleteByValue(phone);
    }
}
