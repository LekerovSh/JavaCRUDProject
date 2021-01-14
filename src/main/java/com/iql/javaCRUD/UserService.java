package com.iql.javaCRUD;

import com.iql.javaCRUD.dao.UserRepository;
import com.iql.javaCRUD.handler.CustomException;
import com.iql.javaCRUD.models.User;
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

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public String signup(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getEmail());
        } else {
            throw new RuntimeException("Email is already in use");
        }
    }

    @Transactional
    public String signin(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid email/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Transactional
    public ResponseEntity changeEmail(String emailOld, String emailNew) {
        try {
            System.out.println("change email");
            User user = userRepository.findByEmail(emailOld);
            System.out.println("GET EMAIL: " + user.getEmail());
            user.setEmail(emailNew);
            userRepository.save(user);

            return new ResponseEntity(HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException("Email not Found", HttpStatus.NOT_FOUND);
        }
    }
}
