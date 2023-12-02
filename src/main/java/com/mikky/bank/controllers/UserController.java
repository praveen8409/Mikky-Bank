package com.mikky.bank.controllers;

import com.mikky.bank.dtos.BankResponse;
import com.mikky.bank.dtos.CreditDebitRequest;
import com.mikky.bank.dtos.EnquiryRequest;
import com.mikky.bank.dtos.UserRequest;
import com.mikky.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/balanceEnquiry")
    public ResponseEntity<BankResponse> balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        BankResponse response = userService.balanceEnquiry(enquiryRequest);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping("/nameEnquiry")
    public ResponseEntity<String> nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        String response = userService.nameEnquiry(enquiryRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }
}
