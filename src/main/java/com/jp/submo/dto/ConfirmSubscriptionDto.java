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
public class ConfirmSubscriptionDto {

    private long subscriptionId;
    private String transRefKey;
    private String transactionComment;


}
