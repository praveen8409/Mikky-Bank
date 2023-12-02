package com.mikky.bank.controllers;

import com.mikky.bank.dtos.*;
import com.mikky.bank.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User management APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Create a new user account",
            description = "Create a new user and assigned account number"
    )
    @ApiResponse(
            responseCode = "2001",
            description = "Http response code for Created"
    )
    @PostMapping
    public ResponseEntity<BankResponse> createAccount(@RequestBody UserRequest userRequest){
        BankResponse user = userService.createAccount(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Balance Enquiry",
            description = "Given an account number and check how much user has balance"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http response code for successful"
    )
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

    @PostMapping("/credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("/debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }

    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest request){
        return userService.transfer(request);
    }
}
