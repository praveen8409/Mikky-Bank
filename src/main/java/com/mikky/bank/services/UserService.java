package com.mikky.bank.services;

import com.mikky.bank.dtos.BankResponse;
import com.mikky.bank.dtos.CreditDebitRequest;
import com.mikky.bank.dtos.EnquiryRequest;
import com.mikky.bank.dtos.UserRequest;

public interface UserService {

    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);

}
