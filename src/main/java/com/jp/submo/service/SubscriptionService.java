package com.jp.submo.service;

import com.jp.submo.dto.JpResponseModel;
import com.jp.submo.dto.SubscriptionDto;

/**
 * @author chetan
 */
public interface SubscriptionService {

    JpResponseModel createSubscription(SubscriptionDto subscriptionDto);

}
