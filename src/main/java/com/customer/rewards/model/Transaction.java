package com.customer.rewards.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a single purchase transaction made by a customer.
 *
 * @param id unique transaction id
 * @param customerId customer identifier
 * @param customerName customer display name
 * @param amount purchase amount in USD
 * @param date transaction date
 */
public record Transaction(
        UUID id,
        String customerId,
        String customerName,
        BigDecimal amount,
        LocalDate date
) { }
