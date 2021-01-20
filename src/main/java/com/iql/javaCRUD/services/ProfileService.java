package com.iql.javaCRUD.services;

import com.iql.javaCRUD.handler.CustomException;
import com.iql.javaCRUD.models.Profile;
import com.iql.javaCRUD.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Transactional
    public ResponseEntity create(String ageStr, String cashStr, String user_email) {
        try {
            if (profileRepository.existsByUserEmail(user_email)) {
                throw new CustomException("Profile is already in use", HttpStatus.BAD_REQUEST);
            }

            int age = Integer.parseInt(ageStr);
            double cash = Double.parseDouble(cashStr);

            Profile profile = new Profile(age, cash, user_email, 8);
            profileRepository.save(profile);

            return ResponseEntity.status(200).body("Profile created");
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Scheduled(fixedRate = 20000)
    public void increaseCash() {
        List<Profile> profileList = profileRepository.findByIncGreaterThan(0);
        for (Profile profile: profileList) {
            profile.setInc(profile.getInc() - 1);
            profile.setCash(profile.getCash() * 1.1);
        }
        profileRepository.saveAll(profileList);
    }
}
