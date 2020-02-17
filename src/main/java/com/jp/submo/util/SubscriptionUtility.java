package com.jp.submo.util;

import static com.jp.submo.constant.AppConstant.*;

import com.jp.submo.dto.JpResponseModel;

/**
 * @author chetan
 */

public class SubscriptionUtility {

    public static JpResponseModel success() {
        JpResponseModel response = new JpResponseModel();
        response.setResponseMessage(REQUEST_SUCCESSFUL);
        response.setResponseStatus(String.valueOf(SERVICE_RESPONSE_ON_SUCCESS));
        return response;
    }

    public static JpResponseModel success(Object requestResult) {
        JpResponseModel response = new JpResponseModel();
        response.setResponse(requestResult);
        response.setResponseMessage(REQUEST_SUCCESSFUL);
        response.setResponseStatus(String.valueOf(SERVICE_RESPONSE_ON_SUCCESS));
        return response;
    }

    public static JpResponseModel error() {
        JpResponseModel response = new JpResponseModel();
        response.setResponseMessage(INTERNAL_ERROR);
        response.setResponseStatus(String.valueOf(SERVICE_RESPONSE_ON_FAILED));
        return response;
    }

    public static JpResponseModel error(String errorMessage) {
        JpResponseModel response = new JpResponseModel();
        response.setResponseMessage(INTERNAL_ERROR);
        response.setResponseMessage(errorMessage);
        response.setResponseStatus(String.valueOf(SERVICE_RESPONSE_ON_FAILED));
        return response;
    }

}
