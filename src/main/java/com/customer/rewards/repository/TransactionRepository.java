package com.customer.rewards.repository;

import com.customer.rewards.model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Transaction repository abstraction. For this assignment, data is in-memory.
 */
public interface TransactionRepository  {

    List<Transaction> findAll();

    List<Transaction> findByCustomerId(String customerId);

    List<Transaction> findByDateRange(LocalDate from, LocalDate to);

    List<Transaction> findByCustomerIdAndDateRange(String customerId, LocalDate from, LocalDate to);

    Optional<String> findCustomerName(String customerId);
}
