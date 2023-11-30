package com.mikky.bank.services;

import com.mikky.bank.dtos.BankResponse;
import com.mikky.bank.dtos.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
}
