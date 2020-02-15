package com.jp.submo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chetan
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionCostDto {
    private double actualCost;
    private double discount;
    private Long discountRefKey;
    private double taxesComponent1;
    private double taxesComponent2;
    private double taxesComponent3;
    private double totalAmountPaid;
    private double totalCost;
}
