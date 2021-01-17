package com.iql.javaCRUD.controllers;

import com.iql.javaCRUD.services.PhoneService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/phone")
@Validated
public class PhoneController {

    @Autowired
    PhoneService phoneService;

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @PostMapping(path="/create")
    public void create(HttpServletRequest httpServletRequest, @ApiParam("value") @RequestParam String value) {
        phoneService.create(value, (String) httpServletRequest.getAttribute("user_email"));
    }

    @ApiImplicitParam(name = "X-Auth-Token", value = "Access Token", required = true, paramType = "header")
    @DeleteMapping(value = "/{value}")
    public ResponseEntity delete(@ApiParam("value") @PathVariable String value) {
        phoneService.delete(value);
        return new ResponseEntity(HttpStatus.OK);
    }
}
