package com.iql.javaCRUD.controllers;

import com.iql.javaCRUD.DTO.UserWithNameDTO;
import com.iql.javaCRUD.UserService;
import com.iql.javaCRUD.dao.UserRepository;
import com.iql.javaCRUD.models.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/say")
    public String say() {
        return "chlenn";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path="/signup")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 422, message = "Username is already in use")})
    public String signup(@ApiParam("Signup User") @Valid @RequestBody UserWithNameDTO userWithNameDTO) {
        return userService.signup(userWithNameDTO.toUser());
    }

    @PostMapping("/signin")
    @ApiOperation(value = "${UserController.signin}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public String login(
                        @RequestParam String email,
                        @RequestParam String password) {
        return userService.signin(email, password);
    }

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @PostMapping("/change-email")
    public ResponseEntity updateEmail(
                        @RequestParam String emailOld,
                        @RequestParam String emailNew) {
        System.out.println("update email");
        return userService.changeEmail(emailOld, emailNew);
    }

}
