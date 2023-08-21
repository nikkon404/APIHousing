package com.conestoga.APIHousing.interfaces;

import com.conestoga.APIHousing.model.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

  // findByPayerIdOrderByRequestIdDesc
  List<Transaction> findByPayerIdOrderByIdDesc(int payerId);

  // get all desc
  List<Transaction> findAllByOrderByIdDesc();
}
