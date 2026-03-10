package com.customer.rewards.controller;

import com.customer.rewards.dto.RewardsSummaryResponse;
import com.customer.rewards.service.RewardsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

/**
 * REST controller exposing rewards calculation.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    /**
     * Calculates reward points per customer per month and total.
     *
     * @param customerId optional customer id
     * @param from optional start date (inclusive)
     * @param to optional end date (inclusive)
     */
    @GetMapping
    public RewardsSummaryResponse getRewards(
            @RequestParam(name = "customerId", required = false) String customerId,
            @RequestParam(name = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return rewardsService.calculateRewards(
                Optional.ofNullable(customerId),
                Optional.ofNullable(from),
                Optional.ofNullable(to)
        );
    }

}
