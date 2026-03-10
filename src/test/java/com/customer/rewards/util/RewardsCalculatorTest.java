package com.customer.rewards.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * Unit tests for reward calculation.
 */
class RewardsCalculatorTest {

    private final RewardsCalculator calculator = new RewardsCalculator();

    @Test
    void shouldReturnZeroWhenAmountBelowOrEqual50() {
        Assertions.assertEquals(0, calculator.calculatePoints(new BigDecimal("50")));
        Assertions.assertEquals(0, calculator.calculatePoints(new BigDecimal("10")));
    }

    @Test
    void shouldCalculatePointsBetween50And100() {
        Assertions.assertEquals(1, calculator.calculatePoints(new BigDecimal("51")));
        Assertions.assertEquals(25, calculator.calculatePoints(new BigDecimal("75")));
        Assertions.assertEquals(50, calculator.calculatePoints(new BigDecimal("100")));
    }

    @Test
    void shouldCalculatePointsAbove100() {
        Assertions.assertEquals(90, calculator.calculatePoints(new BigDecimal("120")));
        Assertions.assertEquals(250, calculator.calculatePoints(new BigDecimal("200"))); // 50 + (100*2)
    }

    @Test
    void shouldThrowForNegativeAmount() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> calculator.calculatePoints(new BigDecimal("-1")));
    }

    @Test
    void shouldThrowForNullAmount() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> calculator.calculatePoints(null));
    }
}
