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

    JpResponseModel createSubscription(SubscriptionDto subscriptionDto, String createdBy);

    JpResponseModel confirmSubscription(ConfirmSubscriptionDto confirmSubscriptionDto, String modifiedBy);

    JpResponseModel assignChefToSubscription(AssignChefToSubscriptionDto assignChefToSubscriptionDto, String createdBy);

    JpResponseModel startCooking(CookingDto cookingDto, String modifiedBy);

    JpResponseModel endCooking(CookingDto cookingDto, String modifiedBy);

    JpResponseModel endSubscription(EndSubscriptionDto endSubscriptionDto, String createdBy, String modifiedBy);

    JpResponseModel reassignChefToSubscription(ReassignChefToSubscriptionDto reassignChefToSubscriptionDto, String
            createdBy);


}
