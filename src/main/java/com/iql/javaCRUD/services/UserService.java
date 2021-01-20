package com.iql.javaCRUD.services;

import com.iql.javaCRUD.controllers.UserController;
import com.iql.javaCRUD.handler.CustomException;
import com.iql.javaCRUD.models.Phone;
import com.iql.javaCRUD.models.Profile;
import com.iql.javaCRUD.models.User;
import com.iql.javaCRUD.repositories.PhoneRepository;
import com.iql.javaCRUD.repositories.ProfileRepository;
import com.iql.javaCRUD.repositories.UserRepository;
import com.iql.javaCRUD.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public ResponseEntity signup(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            logger.info("Method signup is succeed");
            return ResponseEntity.status(HttpStatus.OK).body(jwtTokenProvider.createToken(user.getEmail()));
        } else {
            logger.error("Email is already in use");
            throw new CustomException("Email is already in use", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity signin(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            logger.info("Method signin is succeed");
            return ResponseEntity.status(HttpStatus.OK).body(jwtTokenProvider.createToken(email));
        } catch (AuthenticationException e) {
            logger.error("Invalid email/password supplied");
            throw new CustomException("Invalid email/password supplied", HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public ResponseEntity updateEmail(String emailOld, String emailNew) {
        try {
            if (userRepository.existsByEmail(emailNew))
                throw new CustomException("Email is already used", HttpStatus.BAD_REQUEST);

            User user = userRepository.findByEmail(emailOld);
            user.setEmail(emailNew);
            userRepository.save(user);
            logger.info("Method updateEmail is succeed");
            return ResponseEntity.status(HttpStatus.OK).body("Email changed");
        } catch (AuthenticationException e) {
            logger.error("Email not Found");
            throw new CustomException("Email not Found", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity delete(String email) {
        if (userRepository.existsByEmail(email)) {
            userRepository.deleteByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted by Email");
        }
        return ResponseEntity.status(HttpStatus.OK).body("There is no User with this email");
    }

    @Transactional
    public ResponseEntity allUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userList = userRepository.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(userList.getContent());
    }

    @Transactional(readOnly = true)
    public ResponseEntity filterByAge (int age) {
        List<String> userNameList = new ArrayList<>();
        List<Profile> profileList = profileRepository.findByAge(age);
        for (Profile profile : profileList) {
            User user = userRepository.findByEmail(profile.getUserEmail());
            userNameList.add(user.getName());
        }
        if (userNameList.size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body(userNameList);
        return ResponseEntity.status(HttpStatus.OK).body("There is no Users with age: " + age);
    }

    @Transactional(readOnly = true)
    public ResponseEntity filterByName(String name) {
        List<String> emailList = new ArrayList<>();
        List<User> userList = userRepository.findByName(name);

        for (User user: userList) {
            emailList.add(user.getEmail());
        }
        if (emailList.size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body(emailList);
        return ResponseEntity.status(HttpStatus.OK).body("There is no Users with name: " + name);
    }

    @Transactional(readOnly = true)
    public ResponseEntity filterByEmail(String email) {
        if (userRepository.existsByEmail(email))
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByEmail(email).getName());
        return ResponseEntity.status(HttpStatus.OK).body("There is no Users with email: " + email);
    }

    @Transactional(readOnly = true)
    public ResponseEntity filterByPhone(String value) {
        Phone phone = phoneRepository.findByValue(value);
        if (phoneRepository.existsByValue(value))
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByEmail(phone.getUserEmail()).getName());
        return ResponseEntity.status(HttpStatus.OK).body("There is no Users with phone: " + value);
    }

}
