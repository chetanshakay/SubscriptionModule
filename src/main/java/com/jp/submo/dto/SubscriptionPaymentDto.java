package com.jp.submo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author chetan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPaymentDto {

    private long paymentMode;

    private long thirdPartyProvider;

    private double totalAmountPaid;

    private String transactionComment;
}
