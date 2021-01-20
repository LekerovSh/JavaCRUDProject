package com.iql.javaCRUD.controllers;

import com.iql.javaCRUD.services.ProfileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/profile")
@Validated
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @PostMapping("/create")
    public ResponseEntity create(
            HttpServletRequest httpServletRequest,
            @ApiParam("Age") @RequestParam String age,
            @ApiParam("Cash") @RequestParam String cash) {

        return profileService.create(age, cash, (String) httpServletRequest.getAttribute("user_email"));
    }

}
