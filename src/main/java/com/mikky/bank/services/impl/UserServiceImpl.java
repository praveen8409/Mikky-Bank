package com.mikky.bank.services.impl;

import com.mikky.bank.dtos.AccountInfo;
import com.mikky.bank.dtos.BankResponse;
import com.mikky.bank.dtos.EmailDetails;
import com.mikky.bank.dtos.UserRequest;
import com.mikky.bank.entities.User;
import com.mikky.bank.repositories.UserRepository;
import com.mikky.bank.services.EmailService;
import com.mikky.bank.services.UserService;
import com.mikky.bank.utills.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        if(userRepository.existsByEmail(userRequest.getEmail())){
            BankResponse response = BankResponse.builder()
                    .accountInfo(null)
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .build();
            return response;
        }

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .startOfOrigin(userRequest.getStartOfOrigin())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .email(userRequest.getEmail())
                .accountNumber(AccountUtils.generatedAccountName())
                .accountBalance(BigDecimal.ZERO)
                .status("ACTIVE")
                .build();
        User savedUser = userRepository.save(user);

        // Send mail notification
        EmailDetails accountCreationMail = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("Account Creation")
                .messageBody("Congratulations! You have successfully opened Account\n\n Account Holder Name : " + savedUser.getFirstName() + " " + savedUser.getLastName() + "\nAccount Number : " + savedUser.getAccountNumber() + "\n")
                .build();
        emailService.sendEmailAlert(accountCreationMail);

        BankResponse response = BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountBalance(savedUser.getAccountBalance())
                        .build()
                ).build();
        return response;
    }
}
