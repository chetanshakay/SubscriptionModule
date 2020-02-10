package com.jp.submo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author EHTESHAM MAZHAR
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JpResponseModel {

	private String responseStatus;
	private String responseMessage;
	private Object response;
	private String customMessage;
	
}
