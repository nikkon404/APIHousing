package com.conestoga.APIHousing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.conestoga.APIHousing.dtos.TransactionDTO;
import com.conestoga.APIHousing.interfaces.TransactionRepository;
import com.conestoga.APIHousing.model.Transaction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testGetAllTransactions() {
        // Create some sample transactions to be returned by the mock repository
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        transactions.add(new Transaction());
        transactions.add(new Transaction());
        // Add more transactions to the list

        // Create a mock of the TransactionRepository
        TransactionRepository transactionRepository = mock(TransactionRepository.class);

        // Set up the mock to return the sample transactions when findAllByOrderByIdDesc() is called
        when(transactionRepository.findAllByOrderByIdDesc()).thenReturn(transactions);

        // Create an instance of the TransactionService and pass the mock repository
        TransactionService transactionService = new TransactionService(transactionRepository);

        // Call the getAllTransactions() method of the service
        List<TransactionDTO> transactionDTOs = transactionService.getAllTransactions();

        // Assertions
        assertEquals(transactions.size(), transactionDTOs.size());
        // Add more assertions as per your DTO mapping logic or other requirements
    }

    @Test
    void testGetTransactionsByPayerId() {
        int payerId = 1;
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(/* Add necessary parameters */));
        // Add more transactions to the list

        // Create a mock of the TransactionRepository
        TransactionRepository transactionRepository = mock(TransactionRepository.class);

        // Set up the mock to return the sample transactions when findByPayerIdOrderByIdDesc() is called
        when(transactionRepository.findByPayerIdOrderByIdDesc(payerId)).thenReturn(transactions);

        // Create an instance of the TransactionService and pass the mock repository
        TransactionService transactionService = new TransactionService(transactionRepository);

        // Call the getTransactionsByPayerId() method of the service
        List<TransactionDTO> transactionDTOs = transactionService.getTransactionsByPayerId(payerId);

        // Assertions
        assertEquals(transactions.size(), transactionDTOs.size());
        // Add more assertions as per your DTO mapping logic or other requirements
    }

    @Test
    void testCreateTransaction() {
        Transaction transaction = new Transaction(/* Add necessary parameters */);

        // Create a mock of the TransactionRepository
        TransactionRepository transactionRepository = mock(TransactionRepository.class);

        // Set up the mock to return the transaction when save() is called
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Create an instance of the TransactionService and pass the mock repository
        TransactionService transactionService = new TransactionService(transactionRepository);

        // Call the createTransaction() method of the service
        Transaction createdTransaction = transactionService.createTransaction(transaction);

        // Assertions
        assertEquals(transaction, createdTransaction);
    }
}
