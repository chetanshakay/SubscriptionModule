package com.jp.submo.service;

import com.jp.submo.dto.AssignChefToSubscriptionDto;
import com.jp.submo.dto.ConfirmSubscriptionDto;
import com.jp.submo.dto.CookingDto;
import com.jp.submo.dto.EndSubscriptionDto;
import com.jp.submo.dto.JpResponseModel;
import com.jp.submo.dto.ReassignChefToSubscriptionDto;
import com.jp.submo.dto.SubscriptionDto;

/**
 * @author chetan
 */
public interface SubscriptionService {

    JpResponseModel createSubscription(SubscriptionDto subscriptionDto);

    JpResponseModel confirmSubscription(ConfirmSubscriptionDto confirmSubscriptionDto);

    JpResponseModel assignChefToSubscription(AssignChefToSubscriptionDto assignChefToSubscriptionDto);

    JpResponseModel startCooking(CookingDto cookingDto);

    JpResponseModel endCooking(CookingDto cookingDto);

    JpResponseModel endSubscription(EndSubscriptionDto endSubscriptionDto);

    JpResponseModel reassignChefToSubscription(ReassignChefToSubscriptionDto reassignChefToSubscriptionDto);


}
