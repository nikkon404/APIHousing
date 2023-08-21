package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.dtos.TransactionDTO;
import com.conestoga.APIHousing.interfaces.TransactionRepository;
import com.conestoga.APIHousing.model.Transaction;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    Logger logger = Logger.getLogger(TransactionService.class.getName());

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAllByOrderByIdDesc();
        logger.info("Transactions found: " + transactions.size());
        return transactions.stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> getTransactionsByPayerId(int payerId) {
        List<Transaction> transactions = transactionRepository.findByPayerIdOrderByIdDesc(payerId);
        logger.info("Transactions found: " + transactions.size() + " for payerId: " + payerId);
        return transactions.stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    }

    public Transaction createTransaction(Transaction transaction) {
        Transaction t = transactionRepository.save(transaction);
        logger.info("Transaction created");
        return t;
    }
}

