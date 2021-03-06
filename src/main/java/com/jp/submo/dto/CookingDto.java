package com.jp.submo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author chetan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CookingDto {

    private Long subscriptionActualId;
}
