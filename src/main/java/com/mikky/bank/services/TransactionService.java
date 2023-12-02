package com.mikky.bank.services;

import com.mikky.bank.dtos.TransactionDto;
import com.mikky.bank.entities.Transaction;

public interface TransactionService {

    void transaction(TransactionDto transactionDto);
}
