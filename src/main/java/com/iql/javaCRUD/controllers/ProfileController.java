package com.iql.javaCRUD.controllers;

import com.iql.javaCRUD.models.Profile;
import com.iql.javaCRUD.services.ProfileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@Validated
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @PostMapping(path="/create")
    public void create(
            @ApiParam("Age") @RequestParam int age,
            @ApiParam("Cash") @RequestParam double cash) {
        // TODO: 16.01.2021 fill this code
        profileService.create(age, cash);
    }

}
