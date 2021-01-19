package com.iql.javaCRUD.controllers;

import com.iql.javaCRUD.DTO.UserDTO;
import com.iql.javaCRUD.repositories.UserRepository;
import com.iql.javaCRUD.services.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @GetMapping("/say")
    public String say() {
        return "chlenn";
    }

    // TODO: 19.01.21  DONE
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path="/signup")
    @ApiOperation(value = "Create User", response = ResponseEntity.class)
    public ResponseEntity signup(@ApiParam("Signup User") @Valid @RequestBody UserDTO userDTO) {
        return userService.signup(userDTO.toUser());
    }

    // TODO: 19.01.21 DONE
    @PostMapping("/signin")
    @ApiOperation(value = "Login User", response = ResponseEntity.class)
    public ResponseEntity signin(
                        @RequestParam String email,
                        @RequestParam String password) {
        return userService.signin(email, password);
    }

    // TODO: 19.01.21 DONE
    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @PatchMapping("/change-email")
    public ResponseEntity updateEmail(
                        @RequestParam String emailOld,
                        @RequestParam String emailNew) {
        return userService.changeEmail(emailOld, emailNew);
    }

    // TODO: 19.01.21 DONE
    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @DeleteMapping(value = "/{email}")
    public ResponseEntity delete(@ApiParam("Email") @PathVariable String email) {
        return userService.delete(email);
    }

    // TODO: 19.01.21 DONE
    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @GetMapping("/filter/{age}")
    public ResponseEntity filterByAge(
            @ApiParam("Age") @PathVariable String age) {
        return userService.filterByAge(Integer.parseInt(age));
    }

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @GetMapping("/filter-name/{name}")
    public List<String> filterByName(
            @ApiParam("Name") @PathVariable String name) {
        return userService.filterByName(name);
    }

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @GetMapping("/filter-email/{email}")
    public String filterByEmail(
            @ApiParam("Email") @PathVariable String email) {
        return userService.filterByEmail(email);
    }

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @GetMapping("/filter-phone/{phone}")
    public String filterByPhone(
            @ApiParam("Phone") @PathVariable String phone) {
        return userService.filterByPhone(phone);
    }

}
