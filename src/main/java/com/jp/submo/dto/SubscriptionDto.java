package com.jp.submo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chetan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {

    private String description;
    private long noOfPeople;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private long userId;

    private Long subscriptionDuration;


    private SubscriptionCostDto subscriptionCost;
    private SubscriptionPaymentDto subscriptionPayment;

    private List<SubscriptionMealDto> subscriptionMeals;


}
