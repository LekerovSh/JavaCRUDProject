package com.iql.javaCRUD.services;

import com.iql.javaCRUD.models.Profile;
import com.iql.javaCRUD.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfileService {

    private static final String CRON = "*/10 * * * * *";

    @Autowired
    private ProfileRepository profileRepository;

    @Transactional
    public ResponseEntity create(String ageStr, String cashStr, String user_email) {
        try {
            int age = Integer.parseInt(ageStr);
            double cash = Double.parseDouble(cashStr);

            Profile profile = new Profile(age, cash, user_email, 8);
            profileRepository.save(profile);

            return ResponseEntity.status(200).body("Created");
        } catch (Throwable e) {
            return ResponseEntity.status(50456).body(e.getMessage());
        }
    }

}
