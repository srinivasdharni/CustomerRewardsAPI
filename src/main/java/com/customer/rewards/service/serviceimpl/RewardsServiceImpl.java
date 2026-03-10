package com.customer.rewards.service.serviceimpl;

import com.customer.rewards.dto.CustomerRewardResponse;
import com.customer.rewards.dto.RewardsSummaryResponse;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.TransactionRepository;
import com.customer.rewards.service.RewardsService;
import com.customer.rewards.util.RewardsCalculator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RewardsServiceImpl implements RewardsService {

    private final TransactionRepository transactionRepository;
    private final RewardsCalculator rewardsCalculator;

    public RewardsServiceImpl(TransactionRepository transactionRepository, RewardsCalculator rewardsCalculator) {
        this.transactionRepository = transactionRepository;
        this.rewardsCalculator = rewardsCalculator;
    }


    @Override
    public RewardsSummaryResponse calculateRewards(Optional<String> customerId, Optional<LocalDate> from, Optional<LocalDate> to) {
        List<Transaction> txns = customerId
                .map(transactionRepository::findByCustomerId)
                .orElseGet(transactionRepository::findAll);

        if (txns.isEmpty()) {
            // still return empty result; caller can decide
            LocalDate now = LocalDate.now();
            return new RewardsSummaryResponse(now, now, List.of());
        }

        LocalDate minDate = txns.stream().map(Transaction::date).min(LocalDate::compareTo).orElseThrow();
        LocalDate maxDate = txns.stream().map(Transaction::date).max(LocalDate::compareTo).orElseThrow();

        LocalDate effectiveFrom = from.orElse(minDate);
        LocalDate effectiveTo = to.orElse(maxDate);

        if (effectiveFrom.isAfter(effectiveTo)) {
            throw new IllegalArgumentException("'from' date must be before or equal to 'to' date");
        }

        txns = txns.stream()
                .filter(t -> !t.date().isBefore(effectiveFrom) && !t.date().isAfter(effectiveTo))
                .toList();

        Map<String, List<Transaction>> groupedByCustomer = txns.stream()
                .collect(Collectors.groupingBy(Transaction::customerId));

        List<CustomerRewardResponse> customers = new ArrayList<>();

        for (Map.Entry<String, List<Transaction>> entry : groupedByCustomer.entrySet()) {
            String custId = entry.getKey();
            List<Transaction> custTxns = entry.getValue();

            String name = custTxns.stream().map(Transaction::customerName).findFirst()
                    .orElseGet(() -> transactionRepository.findCustomerName(custId).orElse("Unknown"));

            Map<YearMonth, Integer> monthPoints = new HashMap<>();
            int total = 0;

            for (Transaction t : custTxns) {
                int points = rewardsCalculator.calculatePoints(t.amount());
                YearMonth ym = YearMonth.from(t.date());
                monthPoints.merge(ym, points, Integer::sum);
                total += points;
            }

            // Return months sorted
            Map<String, Integer> monthPointsStr = monthPoints.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            Map.Entry::getValue,
                            (a, b) -> a,
                            LinkedHashMap::new
                    ));

            customers.add(new CustomerRewardResponse(custId, name, monthPointsStr, total));
        }

        customers.sort(Comparator.comparing(CustomerRewardResponse::getCustomerName));

        return new RewardsSummaryResponse(effectiveFrom, effectiveTo, customers);
    }
}
