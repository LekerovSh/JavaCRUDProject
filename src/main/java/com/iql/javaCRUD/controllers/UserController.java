package com.iql.javaCRUD.controllers;

import com.iql.javaCRUD.DTO.UserDTO;
import com.iql.javaCRUD.services.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/signup")
    @ApiOperation(value = "Create User", response = ResponseEntity.class)
    public ResponseEntity signup(@RequestBody UserDTO userDTO) {
        return userService.signup(userDTO.toUser());
    }

    @PostMapping("/signin")
    @ApiOperation(value = "Login User", response = ResponseEntity.class)
    public ResponseEntity signin(
                        @RequestParam String email,
                        @RequestParam String password) {
        return userService.signin(email, password);
    }

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @PatchMapping("/update-email")
    public ResponseEntity updateEmail(
                        @RequestParam String emailOld,
                        @RequestParam String emailNew) {
        return userService.updateEmail(emailOld, emailNew);
    }

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @DeleteMapping
    public ResponseEntity delete(
            HttpServletRequest httpServletRequest) {
        return userService.delete((String) httpServletRequest.getAttribute("user_email"));
    }

    @GetMapping("/all")
    public ResponseEntity allUsers(
            @RequestParam String page,
            @RequestParam String size) {
        return userService.allUsers(Integer.parseInt(page), Integer.parseInt(size));
    }

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @GetMapping("/filter/{age}")
    public ResponseEntity filterByAge(
            @ApiParam("Age") @PathVariable String age) {
        return userService.filterByAge(Integer.parseInt(age));
    }

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @GetMapping("/filter-name/{name}")
    public ResponseEntity filterByName(
            @ApiParam("Name") @PathVariable String name) {
        return userService.filterByName(name);
    }

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @GetMapping("/filter-email/{email}")
    public ResponseEntity filterByEmail(
            @ApiParam("Email") @PathVariable String email) {
        return userService.filterByEmail(email);
    }

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @GetMapping("/filter-phone/{phone}")
    public ResponseEntity filterByPhone(
            @ApiParam("Phone") @PathVariable String phone) {
        return userService.filterByPhone(phone);
    }

}
