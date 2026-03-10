package com.customer.rewards.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Calculates reward points for a single transaction amount.
 *
 * <pre>
 * - 2 points for every dollar spent over 100
 * - 1 point for every dollar spent between 50 and 100
 * </pre>
 */
@Component
public class RewardsCalculator{

    public int calculatePoints(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Transaction amount cannot be null");
        }
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Transaction amount cannot be negative");
        }

        // Convert to whole dollars (truncate/round down)
        int dollars = amount.setScale(0, RoundingMode.FLOOR).intValueExact();

        if (dollars <= 50) {
            return 0;
        }

        int points = 0;
        if (dollars > 100) {
            points += (dollars - 100) * 2;
            points += 50; // 1 point for dollars 51..100 => 50 points
        } else {
            points += (dollars - 50);
        }
        return points;
    }
}
