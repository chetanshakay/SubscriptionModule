package com.jp.submo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chetan
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndSubscriptionDto {

    private Long subscriptionId;
    private Long endType;
    private String modifiedBy;
    private String createdBy;
}
