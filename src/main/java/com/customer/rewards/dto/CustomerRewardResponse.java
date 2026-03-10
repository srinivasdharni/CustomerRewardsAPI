package com.customer.rewards.dto;

import java.util.Map;

/**
 * API response for rewards computed for a single customer.
 */
public class CustomerRewardResponse {

    private String customerId;
    private String customerName;
    /** Map in format yyyy-MM -> points */
    private Map<String, Integer> monthlyPoints;
    private int totalPoints;

    public CustomerRewardResponse() { }

    public CustomerRewardResponse(String customerId, String customerName, Map<String, Integer> monthlyPoints, int totalPoints) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.monthlyPoints = monthlyPoints;
        this.totalPoints = totalPoints;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<String, Integer> getMonthlyPoints() {
        return monthlyPoints;
    }

    public void setMonthlyPoints(Map<String, Integer> monthlyPoints) {
        this.monthlyPoints = monthlyPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

}
