package com.mikky.bank.services;

import com.mikky.bank.dtos.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
}
