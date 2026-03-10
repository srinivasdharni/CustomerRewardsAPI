package com.customer.rewards.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * Top-level response containing computed rewards for one or more customers.
 */
public class RewardsSummaryResponse {
    private LocalDate from;
    private LocalDate to;
    private List<CustomerRewardResponse> customers;

    public RewardsSummaryResponse() { }

    public RewardsSummaryResponse(LocalDate from, LocalDate to, List<CustomerRewardResponse> customers) {
        this.from = from;
        this.to = to;
        this.customers = customers;
    }

    public LocalDate getFrom() { return from; }
    public void setFrom(LocalDate from) { this.from = from; }

    public LocalDate getTo() { return to; }
    public void setTo(LocalDate to) { this.to = to; }

    public List<CustomerRewardResponse> getCustomers() { return customers; }
    public void setCustomers(List<CustomerRewardResponse> customers) { this.customers = customers; }
}
