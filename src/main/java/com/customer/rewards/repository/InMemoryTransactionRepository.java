package com.customer.rewards.repository;

import com.customer.rewards.model.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * In-memory transaction dataset used to demonstrate solution.
 * This can be easily swapped with JPA repository if needed.
 */
@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final List<Transaction> transactions;

    /**
     * Dataset: multiple customers + multiple transactions across 3 months
     */
    public InMemoryTransactionRepository() {

        String Sai   = "CMRWP10001";
        String Kishore = "CMRWP10002";

        String Arun   = "CMRWP10003";
        String Shriya   = "CMRWP10004";
        String Rajesh   = "CMRWP10005";
        String Kavitha  = "CMRWP10006";
        String Raju   = "CMRWP10007";
        String Shirisha   = "CMRWP10008";
        String Vikash  = "CMRWP10009";
        String Karthik   = "CMRWP10010";


        transactions = List.of(
                //  Sai (Jan-Feb 2025)
                new Transaction(UUID.randomUUID(), Sai, "Sai", new BigDecimal("120"), LocalDate.of(2025, 1, 10)),
                new Transaction(UUID.randomUUID(), Sai, "Sai", new BigDecimal("75"),  LocalDate.of(2025, 1, 15)),
                new Transaction(UUID.randomUUID(), Sai, "Sai", new BigDecimal("50"),  LocalDate.of(2025, 2, 1)),
                new Transaction(UUID.randomUUID(), Sai, "Sai", new BigDecimal("110"), LocalDate.of(2025, 2, 20)),

                //  Kishore (Jan-Mar 2025)
                new Transaction(UUID.randomUUID(), Kishore, "Kishore", new BigDecimal("40"),  LocalDate.of(2025, 1, 5)),
                new Transaction(UUID.randomUUID(), Kishore, "Kishore", new BigDecimal("99"),  LocalDate.of(2025, 2, 14)),
                new Transaction(UUID.randomUUID(), Kishore, "Kishore", new BigDecimal("130"), LocalDate.of(2025, 3, 2)),
                new Transaction(UUID.randomUUID(), Kishore, "Kishore", new BigDecimal("200"), LocalDate.of(2025, 3, 18)),

                //  Arun (Jan-Apr 2025)
                new Transaction(UUID.randomUUID(), Arun, "Arun", new BigDecimal("55"),  LocalDate.of(2025, 1, 12)),
                new Transaction(UUID.randomUUID(), Arun, "Arun", new BigDecimal("105"), LocalDate.of(2025, 2, 9)),
                new Transaction(UUID.randomUUID(), Arun, "Arun", new BigDecimal("180"), LocalDate.of(2025, 3, 21)),
                new Transaction(UUID.randomUUID(), Arun, "Arun", new BigDecimal("95"),  LocalDate.of(2025, 4, 7)),

                //  Shriya (Feb-May 2025)
                new Transaction(UUID.randomUUID(), Shriya, "Shriya", new BigDecimal("49"),  LocalDate.of(2025, 2, 2)),
                new Transaction(UUID.randomUUID(), Shriya, "Shriya", new BigDecimal("60"),  LocalDate.of(2025, 3, 5)),
                new Transaction(UUID.randomUUID(), Shriya, "Shriya", new BigDecimal("125"), LocalDate.of(2025, 4, 11)),
                new Transaction(UUID.randomUUID(), Shriya, "Shriya", new BigDecimal("210"), LocalDate.of(2025, 5, 19)),

                //  Rajesh (Jan-Jun 2025)
                new Transaction(UUID.randomUUID(), Rajesh, "Rajesh", new BigDecimal("70"),  LocalDate.of(2025, 1, 25)),
                new Transaction(UUID.randomUUID(), Rajesh, "Rajesh", new BigDecimal("150"), LocalDate.of(2025, 2, 18)),
                new Transaction(UUID.randomUUID(), Rajesh, "Rajesh", new BigDecimal("88"),  LocalDate.of(2025, 3, 9)),
                new Transaction(UUID.randomUUID(), Rajesh, "Rajesh", new BigDecimal("101"), LocalDate.of(2025, 4, 15)),
                new Transaction(UUID.randomUUID(), Rajesh, "Rajesh", new BigDecimal("130"), LocalDate.of(2025, 5, 10)),
                new Transaction(UUID.randomUUID(), Rajesh, "Rajesh", new BigDecimal("45"),  LocalDate.of(2025, 6, 1)),

                //  Kavitha (Feb-Jun 2025)
                new Transaction(UUID.randomUUID(), Kavitha, "Kavitha", new BigDecimal("50"),  LocalDate.of(2025, 2, 6)),
                new Transaction(UUID.randomUUID(), Kavitha, "Kavitha", new BigDecimal("110"), LocalDate.of(2025, 3, 20)),
                new Transaction(UUID.randomUUID(), Kavitha, "Kavitha", new BigDecimal("78"),  LocalDate.of(2025, 4, 3)),
                new Transaction(UUID.randomUUID(), Kavitha, "Kavitha", new BigDecimal("190"), LocalDate.of(2025, 5, 26)),
                new Transaction(UUID.randomUUID(), Kavitha, "Kavitha", new BigDecimal("99"),  LocalDate.of(2025, 6, 10)),

                //  Raju (Jan-May 2025)
                new Transaction(UUID.randomUUID(), Raju, "Raju", new BigDecimal("200"), LocalDate.of(2025, 1, 3)),
                new Transaction(UUID.randomUUID(), Raju, "Raju", new BigDecimal("120"), LocalDate.of(2025, 2, 22)),
                new Transaction(UUID.randomUUID(), Raju, "Raju", new BigDecimal("90"),  LocalDate.of(2025, 3, 17)),
                new Transaction(UUID.randomUUID(), Raju, "Raju", new BigDecimal("52"),  LocalDate.of(2025, 4, 8)),
                new Transaction(UUID.randomUUID(), Raju, "Raju", new BigDecimal("140"), LocalDate.of(2025, 5, 30)),

                //  Shirisha (Feb-May 2025)
                new Transaction(UUID.randomUUID(), Shirisha, "Shirisha", new BigDecimal("65"),  LocalDate.of(2025, 2, 12)),
                new Transaction(UUID.randomUUID(), Shirisha, "Shirisha", new BigDecimal("115"), LocalDate.of(2025, 3, 28)),
                new Transaction(UUID.randomUUID(), Shirisha, "Shirisha", new BigDecimal("40"),  LocalDate.of(2025, 4, 9)),
                new Transaction(UUID.randomUUID(), Shirisha, "Shirisha", new BigDecimal("175"), LocalDate.of(2025, 5, 5)),

                // Vikash (Jan-Jun 2025)
                new Transaction(UUID.randomUUID(), Vikash, "Vikash", new BigDecimal("95"),  LocalDate.of(2025, 1, 18)),
                new Transaction(UUID.randomUUID(), Vikash, "Vikash", new BigDecimal("100"), LocalDate.of(2025, 2, 13)),
                new Transaction(UUID.randomUUID(), Vikash, "Vikash", new BigDecimal("135"), LocalDate.of(2025, 3, 1)),
                new Transaction(UUID.randomUUID(), Vikash, "Vikash", new BigDecimal("205"), LocalDate.of(2025, 4, 25)),
                new Transaction(UUID.randomUUID(), Vikash, "Vikash", new BigDecimal("48"),  LocalDate.of(2025, 5, 8)),
                new Transaction(UUID.randomUUID(), Vikash, "Vikash", new BigDecimal("160"), LocalDate.of(2025, 6, 12)),

                //  Karthik (Mar-Jun 2025)
                new Transaction(UUID.randomUUID(), Karthik, "Karthik", new BigDecimal("55"),  LocalDate.of(2025, 3, 6)),
                new Transaction(UUID.randomUUID(), Karthik, "Karthik", new BigDecimal("82"),  LocalDate.of(2025, 4, 4)),
                new Transaction(UUID.randomUUID(), Karthik, "Karthik", new BigDecimal("125"), LocalDate.of(2025, 5, 14)),
                new Transaction(UUID.randomUUID(), Karthik, "Karthik", new BigDecimal("199"), LocalDate.of(2025, 6, 22))
        );
    }


    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }

    @Override
    public List<Transaction> findByCustomerId(String customerId) {
        return transactions.stream().filter(t -> t.customerId().equals(customerId)).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByDateRange(LocalDate from, LocalDate to) {
        return transactions.stream()
                .filter(t -> !t.date().isBefore(from) && !t.date().isAfter(to))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByCustomerIdAndDateRange(String customerId, LocalDate from, LocalDate to) {
        return transactions.stream()
                .filter(t -> t.customerId().equals(customerId))
                .filter(t -> !t.date().isBefore(from) && !t.date().isAfter(to))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> findCustomerName(String customerId) {
        return transactions.stream()
                .filter(t -> t.customerId().equals(customerId))
                .map(Transaction::customerName)
                .findFirst();
    }
}
