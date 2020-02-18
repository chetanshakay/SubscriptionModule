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
public class AssignChefToSubscriptionDto {

    Long subscriptionId;
    Long chefId;
    String createdBy;

}
