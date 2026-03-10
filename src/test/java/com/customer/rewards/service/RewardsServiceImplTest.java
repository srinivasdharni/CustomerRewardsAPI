package com.customer.rewards.service;

import com.customer.rewards.dto.CustomerRewardResponse;
import com.customer.rewards.dto.RewardsSummaryResponse;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.TransactionRepository;
import com.customer.rewards.service.serviceimpl.RewardsServiceImpl;
import com.customer.rewards.util.RewardsCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyString;




class RewardsServiceImplTest {

    private TransactionRepository transactionRepository;
    private RewardsCalculator rewardsCalculator;
    private RewardsService rewardsService;

    // ✅ Customer IDs in sequence format
    private final String Sai = "CMRWP10001";
    private final String Kishore = "CMRWP10002";

    // ✅ Transaction UUIDs
    private final UUID T1 = UUID.fromString("7d9c2a10-6d3c-4f22-9b5e-1a2c3d4e5f60");
    private final UUID T2 = UUID.fromString("1f3a5b7c-2d4e-4a6b-8c0d-9e1f2a3b4c5d");
    private final UUID T3 = UUID.fromString("9a8b7c6d-5e4f-3a2b-1c0d-0e9f8a7b6c5d");

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        rewardsCalculator = mock(RewardsCalculator.class);
        rewardsService = new RewardsServiceImpl(transactionRepository, rewardsCalculator);
    }

    @Test
    void calculateRewards_shouldReturnRewardsForCustomerId() {
        // given
        List<Transaction> txns = List.of(
                new Transaction(T1, Sai, "Sai", BigDecimal.valueOf(120.0), LocalDate.of(2025, 1, 10)),
                new Transaction(T2, Sai, "Sai", BigDecimal.valueOf(90.0), LocalDate.of(2025, 2, 15))
        );

        when(transactionRepository.findByCustomerId(Sai)).thenReturn(txns);

        // points calculation mocked
        when(rewardsCalculator.calculatePoints(BigDecimal.valueOf(120.0))).thenReturn(90);
        when(rewardsCalculator.calculatePoints(BigDecimal.valueOf(90.0))).thenReturn(40);

        // when
        RewardsSummaryResponse response = rewardsService.calculateRewards(
                Optional.of(Sai),
                Optional.empty(),
                Optional.empty()
        );

        // then
        assertNotNull(response);
        assertEquals(LocalDate.of(2025, 1, 10), response.getFrom());
        assertEquals(LocalDate.of(2025, 2, 15), response.getTo());

        assertNotNull(response.getCustomers());
        assertEquals(1, response.getCustomers().size());

        CustomerRewardResponse customer = response.getCustomers().get(0);

        assertEquals(Sai, customer.getCustomerId());
        assertEquals("Sai", customer.getCustomerName());

        // total points = 90 + 40 = 130
        assertEquals(130, customer.getTotalPoints());

        assertNotNull(customer.getMonthlyPoints());
        assertEquals(2, customer.getMonthlyPoints().size());

        assertEquals(90, customer.getMonthlyPoints().get("2025-01"));
        assertEquals(40, customer.getMonthlyPoints().get("2025-02"));

        verify(transactionRepository, times(1)).findByCustomerId(Sai);
        verify(transactionRepository, never()).findAll();
    }

    @Test
    void calculateRewards_shouldApplyDateRangeFilter() {
        // given
        List<Transaction> txns = List.of(
                new Transaction(T1, Kishore, "Kishore", BigDecimal.valueOf(120.0), LocalDate.of(2025, 1, 10)),
                new Transaction(T2, Kishore, "Kishore", BigDecimal.valueOf(90.0), LocalDate.of(2025, 2, 15)),
                new Transaction(T3, Kishore, "Kishore", BigDecimal.valueOf(200.0), LocalDate.of(2025, 3, 5))
        );

        when(transactionRepository.findByCustomerId(Kishore)).thenReturn(txns);

        when(rewardsCalculator.calculatePoints(BigDecimal.valueOf(120.0))).thenReturn(90);
        when(rewardsCalculator.calculatePoints(BigDecimal.valueOf(90.0))).thenReturn(40);
        when(rewardsCalculator.calculatePoints(BigDecimal.valueOf(200.0))).thenReturn(250);

        LocalDate from = LocalDate.of(2025, 2, 1);
        LocalDate to = LocalDate.of(2025, 2, 28);

        // when
        RewardsSummaryResponse response = rewardsService.calculateRewards(
                Optional.of(Kishore),
                Optional.of(from),
                Optional.of(to)
        );

        // then
        assertNotNull(response);
        assertEquals(from, response.getFrom());
        assertEquals(to, response.getTo());

        assertEquals(1, response.getCustomers().size());
        CustomerRewardResponse customer = response.getCustomers().get(0);

        // Only Feb txn should be included => 40 points
        assertEquals(40, customer.getTotalPoints());
        assertEquals(1, customer.getMonthlyPoints().size());
        assertEquals(40, customer.getMonthlyPoints().get("2025-02"));
    }

    @Test
    void calculateRewards_shouldReturnEmptyResponseIfNoTransactions() {
        // given
        when(transactionRepository.findAll()).thenReturn(List.of());

        // when
        RewardsSummaryResponse response = rewardsService.calculateRewards(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        // then
        assertNotNull(response);
        assertNotNull(response.getCustomers());
        assertTrue(response.getCustomers().isEmpty());

        verify(transactionRepository, times(1)).findAll();
        verify(transactionRepository, never()).findByCustomerId(anyString());
    }

    @Test
    void calculateRewards_shouldThrowExceptionIfInvalidDateRange() {
        // given (at least one txn exists)
        when(transactionRepository.findAll()).thenReturn(List.of(
                new Transaction(T1, Sai, "Sai", BigDecimal.valueOf(120.0), LocalDate.of(2025, 1, 10))
        ));

        LocalDate from = LocalDate.of(2025, 5, 1);
        LocalDate to = LocalDate.of(2025, 1, 1);

        // when + then
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                rewardsService.calculateRewards(Optional.empty(), Optional.of(from), Optional.of(to))
        );

        assertTrue(ex.getMessage().contains("'from' date must be before or equal to 'to' date"));
    }

    @Test
    void calculateRewards_shouldReturnMultipleCustomersWhenCustomerIdNotProvided() {
        // given
        List<Transaction> txns = List.of(
                new Transaction(T1, Sai, "Sai", BigDecimal.valueOf(120.0), LocalDate.of(2025, 1, 10)),
                new Transaction(T2, Kishore, "Kishore", BigDecimal.valueOf(90.0), LocalDate.of(2025, 1, 12))
        );

        when(transactionRepository.findAll()).thenReturn(txns);

        when(rewardsCalculator.calculatePoints(BigDecimal.valueOf(120.0))).thenReturn(90);
        when(rewardsCalculator.calculatePoints(BigDecimal.valueOf(90.0))).thenReturn(40);

        // when
        RewardsSummaryResponse response = rewardsService.calculateRewards(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        // then
        assertNotNull(response);
        assertEquals(2, response.getCustomers().size());

        // sorted by name -> Kishore, Sai
        assertEquals("Kishore", response.getCustomers().get(0).getCustomerName());
        assertEquals("Sai", response.getCustomers().get(1).getCustomerName());

        verify(transactionRepository, times(1)).findAll();
        verify(transactionRepository, never()).findByCustomerId(anyString());
    }
}
