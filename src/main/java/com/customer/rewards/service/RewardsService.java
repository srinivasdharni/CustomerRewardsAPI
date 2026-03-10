package com.customer.rewards.service;

import com.customer.rewards.dto.RewardsSummaryResponse;

import java.time.LocalDate;
import java.util.Optional;

public interface RewardsService {

    RewardsSummaryResponse calculateRewards(Optional<String> customerId,
                                            Optional<LocalDate> from,
                                            Optional<LocalDate> to);
}
