package com.mikky.bank.controllers;

import com.mikky.bank.dtos.BankResponse;
import com.mikky.bank.dtos.UserRequest;
import com.mikky.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<BankResponse> createAccount(@RequestBody UserRequest userRequest){
        BankResponse user = userService.createAccount(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
