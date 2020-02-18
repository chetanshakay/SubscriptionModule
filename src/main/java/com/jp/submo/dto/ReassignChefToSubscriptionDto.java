package com.jp.submo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chetan
 */

@Data
@NoArgsConstructor
public class ReassignChefToSubscriptionDto extends AssignChefToSubscriptionDto {
    private Long endType;
    private String modifiedBy;
}
