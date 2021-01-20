package com.iql.javaCRUD.services;

import com.iql.javaCRUD.handler.CustomException;
import com.iql.javaCRUD.models.Phone;
import com.iql.javaCRUD.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PhoneService {

    @Autowired
    PhoneRepository phoneRepository;

    @Transactional
    public ResponseEntity create(String value, String user_email) {
        try {
            Phone phone = new Phone(value, user_email);
            phoneRepository.save(phone);
            return ResponseEntity.status(HttpStatus.OK).body("Phone created");
        }
        catch (Exception e) {
            throw new CustomException("Phone is already in use", HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public ResponseEntity delete(String phone) {
        if (phoneRepository.existsByValue(phone)) {
            phoneRepository.deleteByValue(phone);
            return ResponseEntity.status(HttpStatus.OK).body("Phone deleted");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Phone doesn't exists");
        }
    }
}
