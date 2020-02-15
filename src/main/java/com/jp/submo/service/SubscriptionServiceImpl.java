package com.jp.submo.service;

import com.jp.submo.dto.JpResponseModel;
import com.jp.submo.dto.SubscriptionDto;
import com.jp.submo.repository.AllSubscriptionRepository;
import com.jp.submo.repository.SubscriptionCostRepository;
import com.jp.submo.repository.SubscriptionMealRepository;
import com.jp.submo.repository.SubscriptionPaymentRepository;
import com.jp.submo.repository.entity.AllSubscription;
import com.jp.submo.repository.entity.SubscriptionCost;
import com.jp.submo.repository.entity.SubscriptionMeal;
import com.jp.submo.repository.entity.SubscriptionPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;

import static com.jp.submo.util.DtoToEntityMapper.getSubscription;
import static com.jp.submo.util.DtoToEntityMapper.getSubscriptionCost;
import static com.jp.submo.util.DtoToEntityMapper.getSubscriptionMeals;
import static com.jp.submo.util.DtoToEntityMapper.getSubscriptionPayment;
import static com.jp.submo.util.SubscriptionUtility.success;


/**
 * @author chetan
 */

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    SubscriptionCostRepository subscriptionCostRepository;
    @Autowired
    SubscriptionMealRepository subscriptionMealRepository;
    @Autowired
    SubscriptionPaymentRepository subscriptionPaymentRepository;
    @Autowired
    AllSubscriptionRepository allSubscriptionRepository;
    @Autowired
    EntityManager entityManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpResponseModel createSubscription(SubscriptionDto subscriptionDto) {

        AllSubscription subscription = getSubscription(subscriptionDto, entityManager);
        SubscriptionCost cost = getSubscriptionCost(subscriptionDto, subscription);
        SubscriptionPayment payment = getSubscriptionPayment(subscriptionDto, subscription, entityManager);
        Collection<SubscriptionMeal> meals = getSubscriptionMeals(subscriptionDto, subscription, entityManager);

        allSubscriptionRepository.save(subscription);
        subscriptionCostRepository.save(cost);
        subscriptionMealRepository.saveAll(meals);
        subscriptionPaymentRepository.saveAndFlush(payment);
        return success("");
    }


}

