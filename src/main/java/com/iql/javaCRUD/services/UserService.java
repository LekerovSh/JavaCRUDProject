package com.iql.javaCRUD.services;

import com.iql.javaCRUD.handler.CustomException;
import com.iql.javaCRUD.models.Phone;
import com.iql.javaCRUD.models.Profile;
import com.iql.javaCRUD.models.User;
import com.iql.javaCRUD.repositories.PhoneRepository;
import com.iql.javaCRUD.repositories.ProfileRepository;
import com.iql.javaCRUD.repositories.UserRepository;
import com.iql.javaCRUD.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public ResponseEntity signup(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(jwtTokenProvider.createToken(user.getEmail()));
        } else {
            throw new CustomException("Email is already in use", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity signin(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return ResponseEntity.status(HttpStatus.OK).body(jwtTokenProvider.createToken(email));
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid email/password supplied", HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public ResponseEntity changeEmail(String emailOld, String emailNew) {
        try {
            if (userRepository.existsByEmail(emailNew))
                throw new CustomException("Email is already used", HttpStatus.BAD_REQUEST);

            User user = userRepository.findByEmail(emailOld);
            user.setEmail(emailNew);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Email changed");
        } catch (AuthenticationException e) {
            throw new CustomException("Email not Found", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity delete(String email) {
        userRepository.deleteByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted by Email");
    }

    @Transactional
    public ResponseEntity filterByAge (int age) {
        List<String> userNameList = new ArrayList<>();
        List<Profile> profileList = profileRepository.findByAge(age);
        for (Profile profile : profileList) {
            User user = userRepository.findByEmail(profile.getUserEmail());
            userNameList.add(user.getName());
        }
        return ResponseEntity.status(HttpStatus.OK).body(userNameList);
    }

    @Transactional
    public List<String> filterByName(String name) {
        List<String> emailList = new ArrayList<>();
        List<User> userList = userRepository.findByName(name);

        for (User user: userList) {
            emailList.add(user.getEmail());
        }
        return emailList;
    }

    @Transactional
    public String filterByEmail(String email) {
        return userRepository.findByEmail(email).getEmail();
    }

    @Transactional
    public String filterByPhone(String value) {
        Phone phone = phoneRepository.findByValue(value);
        return userRepository.findByEmail(phone.getUserEmail()).getName();
    }

    @Transactional(readOnly = true)
    public Long countAllUsers() {
        return userRepository.count();
    }

}
