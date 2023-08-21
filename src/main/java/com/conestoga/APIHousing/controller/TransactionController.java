package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.dtos.TransactionDTO;
import com.conestoga.APIHousing.service.TransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Get all transactions
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    // Get transactions by payer ID
    @GetMapping("/{payerId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByPayerId(@PathVariable String payerId) {
        final int id = Integer.parseInt(payerId);
        List<TransactionDTO> transactions = transactionService.getTransactionsByPayerId(id);
        return ResponseEntity.ok(transactions);
    }

   
}
