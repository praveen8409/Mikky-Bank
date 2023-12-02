package com.mikky.bank.repositories;

import com.mikky.bank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
