package com.mikky.bank.controllers;

import com.itextpdf.text.DocumentException;
import com.mikky.bank.entities.Transaction;
import com.mikky.bank.services.impl.BankStatement;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/bankStatement")
public class TransactionController {

    @Autowired
    private BankStatement bankStatement;

    @GetMapping
    private List<Transaction> bankStatement(
            @RequestParam String accountNumber,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) throws DocumentException, FileNotFoundException, MessagingException {
        return bankStatement.generateAndSendStatement(accountNumber, startDate, endDate);
    }
}
