package com.iql.javaCRUD.services;

import com.iql.javaCRUD.DTO.UserDTO;
import com.iql.javaCRUD.repositories.UserRepository;
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

import java.util.List;
import java.util.stream.Collectors;

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
//            System.out.println("in signin service method");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email);
        } catch (AuthenticationException e) {
//            System.out.println("in signin service exception");
            throw new CustomException("Invalid email/password supplied", HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public ResponseEntity changeEmail(String emailOld, String emailNew) {
        try {
            User user = userRepository.findByEmail(emailOld);
            user.setEmail(emailNew);
            userRepository.save(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException("Email not Found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

}
