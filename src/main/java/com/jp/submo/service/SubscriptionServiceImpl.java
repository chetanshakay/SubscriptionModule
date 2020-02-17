package com.jp.submo.service;

import com.jp.submo.dto.AssignChefToSubscriptionDto;
import com.jp.submo.dto.ConfirmSubscriptionDto;
import com.jp.submo.dto.CookingDto;
import com.jp.submo.dto.JpResponseModel;
import com.jp.submo.dto.SubscriptionDto;
import com.jp.submo.repository.AllSubscriptionRepository;
import com.jp.submo.repository.SubscribedChefRepository;
import com.jp.submo.repository.SubscriptionActualRepository;
import com.jp.submo.repository.SubscriptionCostRepository;
import com.jp.submo.repository.SubscriptionMealRepository;
import com.jp.submo.repository.SubscriptionPaymentRepository;
import com.jp.submo.repository.entity.AllSubscription;
import com.jp.submo.repository.entity.PaymentStatus;
import com.jp.submo.repository.entity.SubscribedChef;
import com.jp.submo.repository.entity.SubscriptionActual;
import com.jp.submo.repository.entity.SubscriptionCost;
import com.jp.submo.repository.entity.SubscriptionMeal;
import com.jp.submo.repository.entity.SubscriptionPayment;
import com.jp.submo.repository.entity.SubscriptionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.jp.submo.exception.SubscriptionException.throwError;
import static com.jp.submo.util.DtoToEntityMapper.getSubscribedChef;
import static com.jp.submo.util.DtoToEntityMapper.getSubscription;
import static com.jp.submo.util.DtoToEntityMapper.getSubscriptionActual;
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
    SubscriptionActualRepository subscriptionActualRepository;
    @Autowired
    SubscribedChefRepository subscribedChefRepository;
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
        return success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpResponseModel confirmSubscription(ConfirmSubscriptionDto confirmSubscriptionDto) {

        Optional<AllSubscription> subscription = allSubscriptionRepository.findById(confirmSubscriptionDto
                .getSubscriptionId());

        if (!subscription.isPresent()) {
            throwError("Not a valid subscription");
        }

        AllSubscription subscriptionEntity = subscription.get();

        if (subscriptionEntity.getSubscriptionStatus().getSubscriptionStatusId() != 1L) {
            throwError("Subscription status is not in valid state!");
        }

        Timestamp modifiedDate = Timestamp.valueOf(LocalDateTime.now());
        subscriptionEntity.setSubscriptionStatus(entityManager.getReference(SubscriptionStatus.class, 2L));
        subscriptionEntity.setModifiedBy(confirmSubscriptionDto.getModifiedBy());
        subscriptionEntity.setLastModifiedDateTime(modifiedDate);

        SubscriptionPayment payment = subscriptionEntity.getSubscriptionPayment();
        payment.setPaymentStatus(entityManager.getReference(PaymentStatus.class, 2L));
        payment.setModifiedBy(confirmSubscriptionDto.getModifiedBy());
        payment.setLastModifiedDateTime(modifiedDate);

        allSubscriptionRepository.save(subscriptionEntity);
        subscriptionPaymentRepository.saveAndFlush(payment);

        //todo: Add logic for sending notification message to admin

        return success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpResponseModel assignChefToSubscription(AssignChefToSubscriptionDto assignChefToSubscriptionDto) {

        Optional<AllSubscription> subscription = allSubscriptionRepository.findById(assignChefToSubscriptionDto
                .getSubscriptionId());

        if (!subscription.isPresent()) {
            throwError("Not a valid subscription");
        }
        AllSubscription allSubscription = subscription.get();

        SubscribedChef subscribedChef = getSubscribedChef(assignChefToSubscriptionDto, allSubscription, entityManager);

        LocalDateTime startDate = allSubscription.getStartDate().toLocalDateTime();
        LocalDateTime endDate = allSubscription.getEndDate().toLocalDateTime();

        List<SubscriptionActual> subscrptionActualList = new ArrayList<>();
        for (LocalDateTime date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            final Timestamp dateTime = Timestamp.valueOf(date);
            allSubscription.getSubscriptionMeals().forEach(subscriptionMeal -> {
                subscrptionActualList.add(getSubscriptionActual(assignChefToSubscriptionDto, dateTime, subscriptionMeal));
            });
        }

        subscriptionActualRepository.saveAll(subscrptionActualList);
        subscribedChefRepository.saveAndFlush(subscribedChef);

        return success();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpResponseModel startCooking(CookingDto cookingDto) {
        SubscriptionActual subscriptionActual = subscriptionActualRepository
                .findOneBysubscriptionMealAndDateAndActualStatusId
                (entityManager.getReference(SubscriptionMeal.class, cookingDto.getMealId()), Timestamp.valueOf
                        (cookingDto.getDate().atStartOfDay()), 1L);

        if (null == subscriptionActual) {
            throwError("No actual subscription found!");
        }

        subscriptionActual.setActualStatusId(2L);
        subscriptionActual.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
        subscriptionActual.setModifiedBy(cookingDto.getModifiedBy());
        subscriptionActual.setLastModifiedDateTime(Timestamp.valueOf(LocalDateTime.now()));

        subscriptionActualRepository.saveAndFlush(subscriptionActual);

        return success();
    }


}

