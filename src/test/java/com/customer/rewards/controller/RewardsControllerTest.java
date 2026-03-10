package com.customer.rewards.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests using MockMvc.
 */
@SpringBootTest
@AutoConfigureMockMvc
class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnRewardsForAllCustomers() throws Exception {
        mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers").isArray())
                .andExpect(jsonPath("$.customers.length()").value(2))
                .andExpect(jsonPath("$.customers[0].monthlyPoints").exists())
                .andExpect(jsonPath("$.customers[0].totalPoints").exists());
    }

    @Test
    void shouldReturnBadRequestWhenFromAfterTo() throws Exception {
        mockMvc.perform(get("/api/rewards")
                        .param("from", "2025-03-01")
                        .param("to", "2025-01-01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("'from' date must be before or equal to 'to' date"));
    }

    @Test
    void shouldReturnBadRequestForInvalidUuid() throws Exception {
        mockMvc.perform(get("/api/rewards")
                        .param("customerId", "not-a-uuid"))
                .andExpect(status().isBadRequest());
    }
}
