package com.jp.submo.service;

import com.jp.submo.dto.AssignChefToSubscriptionDto;
import com.jp.submo.dto.ConfirmSubscriptionDto;
import com.jp.submo.dto.CookingDto;
import com.jp.submo.dto.EndSubscriptionDto;
import com.jp.submo.dto.JpResponseModel;
import com.jp.submo.dto.ReassignChefToSubscriptionDto;
import com.jp.submo.dto.SubscriptionDto;
import com.jp.submo.repository.AllSubscriptionRepository;
import com.jp.submo.repository.ChefSubEarningRepository;
import com.jp.submo.repository.SubscribedChefRepository;
import com.jp.submo.repository.SubscriptionActualRepository;
import com.jp.submo.repository.SubscriptionCostRepository;
import com.jp.submo.repository.SubscriptionMealRepository;
import com.jp.submo.repository.SubscriptionPaymentRepository;
import com.jp.submo.repository.entity.AllSubscription;
import com.jp.submo.repository.entity.ChefSubEarning;
import com.jp.submo.repository.entity.OffsetReason;
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
import java.time.LocalDate;
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
    ChefSubEarningRepository chefSubEarningRepository;
    @Autowired
    EntityManager entityManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpResponseModel createSubscription(SubscriptionDto subscriptionDto, String createdBy) {

        AllSubscription subscription = getSubscription(subscriptionDto, entityManager, createdBy);
        SubscriptionCost cost = getSubscriptionCost(subscriptionDto, subscription, createdBy);
        SubscriptionPayment payment = getSubscriptionPayment(subscriptionDto, subscription, entityManager, createdBy);
        Collection<SubscriptionMeal> meals = getSubscriptionMeals(subscriptionDto, subscription, entityManager,
                createdBy);

        allSubscriptionRepository.save(subscription);
        subscriptionCostRepository.save(cost);
        subscriptionMealRepository.saveAll(meals);
        subscriptionPaymentRepository.saveAndFlush(payment);
        return success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpResponseModel confirmSubscription(ConfirmSubscriptionDto confirmSubscriptionDto, String modifiedBy) {

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
        subscriptionEntity.setModifiedBy(modifiedBy);
        subscriptionEntity.setLastModifiedDateTime(modifiedDate);

        SubscriptionPayment payment = subscriptionEntity.getSubscriptionPayment();
        payment.setPaymentStatus(entityManager.getReference(PaymentStatus.class, 2L));
        payment.setModifiedBy(modifiedBy);
        payment.setLastModifiedDateTime(modifiedDate);
        payment.setTransRefKey(confirmSubscriptionDto.getTransRefKey());
        payment.setTransactionComment(confirmSubscriptionDto.getTransactionComment());

        allSubscriptionRepository.save(subscriptionEntity);
        subscriptionPaymentRepository.saveAndFlush(payment);

        //todo: Add logic for sending notification message to admin

        return success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpResponseModel assignChefToSubscription(AssignChefToSubscriptionDto assignChefToSubscriptionDto, String
            createdBy) {

        Optional<AllSubscription> subscription = allSubscriptionRepository.findById(assignChefToSubscriptionDto
                .getSubscriptionId());

        if (!subscription.isPresent()) {
            throwError("Not a valid subscription");
        }
        AllSubscription allSubscription = subscription.get();

        assignChefToSubscription(createdBy, assignChefToSubscriptionDto.getChefId(),
                allSubscription.getStartDate().toLocalDateTime(), allSubscription);

        return success();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpResponseModel startCooking(CookingDto cookingDto, String modifiedBy) {
        Optional<SubscriptionMeal> subscriptionMeal = subscriptionMealRepository.findById(cookingDto
                .getSubscriptionMealId());

        if (!subscriptionMeal.isPresent()) {
            throwError("No SubscriptionMeal found!");
        }

        SubscriptionMeal subscriptionMealEntity = subscriptionMeal.get();

        SubscriptionActual subscriptionActual = subscriptionActualRepository
                .findOneBySubscriptionAndMealTypeAndDateAndActualStatusId
                        (subscriptionMealEntity.getAllSubscription(),
                                subscriptionMealEntity.getMealType(),
                                Timestamp.valueOf
                                        (LocalDate.now().atStartOfDay()), 1L);

        if (null == subscriptionActual) {
            throwError("No actual subscription found!");
        }

        subscriptionActual.setActualStatusId(2L);
        subscriptionActual.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        subscriptionActual.setModifiedBy(modifiedBy);
        subscriptionActual.setLastModifiedDateTime(Timestamp.valueOf(LocalDateTime.now()));

        subscriptionActualRepository.saveAndFlush(subscriptionActual);

        return success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpResponseModel endCooking(CookingDto cookingDto, String modifiedBy) {
        Optional<SubscriptionMeal> subscriptionMeal = subscriptionMealRepository.findById(cookingDto
                .getSubscriptionMealId());

        if (!subscriptionMeal.isPresent()) {
            throwError("No SubscriptionMeal found!");
        }
        SubscriptionMeal subscriptionMealEntity = subscriptionMeal.get();

        SubscriptionActual subscriptionActual = subscriptionActualRepository
                .findOneBySubscriptionAndMealTypeAndDateAndActualStatusId
                        (subscriptionMealEntity.getAllSubscription(),
                                subscriptionMealEntity.getMealType(),
                                Timestamp.valueOf
                                        (LocalDate.now().atStartOfDay()), 2L);


        if (null == subscriptionActual) {
            throwError("No actual subscription found!");
        }

        subscriptionActual.setActualStatusId(3L);
        subscriptionActual.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        subscriptionActual.setModifiedBy(modifiedBy);
        subscriptionActual.setLastModifiedDateTime(Timestamp.valueOf(LocalDateTime.now()));

        subscriptionActualRepository.saveAndFlush(subscriptionActual);

        return success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpResponseModel endSubscription(EndSubscriptionDto endSubscriptionDto, String createdBy, String modifiedBy) {
        Optional<AllSubscription> subscription = allSubscriptionRepository.findById(endSubscriptionDto
                .getSubscriptionId());

        if (!subscription.isPresent()) {
            throwError("Not a valid subscription");
        }
        AllSubscription allSubscription = subscription.get();

        endSubscriptionsForAllChefs(endSubscriptionDto.getActualStatusId(), createdBy,
                allSubscription);

        allSubscription.setSubscriptionStatus(entityManager.getReference(SubscriptionStatus.class, endSubscriptionDto
                .getActualStatusId()));
        allSubscription.setModifiedBy(modifiedBy);
        allSubscription.setLastModifiedDateTime(Timestamp.valueOf(LocalDateTime.now()));

        allSubscriptionRepository.saveAndFlush(allSubscription);

        //todo: send notification and emails


        return success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpResponseModel reassignChefToSubscription(ReassignChefToSubscriptionDto reassignChefToSubscriptionDto,
                                                      String createdBy) {
        Optional<AllSubscription> subscription = allSubscriptionRepository.findById(reassignChefToSubscriptionDto
                .getSubscriptionId());

        if (!subscription.isPresent()) {
            throwError("Not a valid subscription");
        }
        AllSubscription allSubscription = subscription.get();

        endSubscriptionsForAllChefs(reassignChefToSubscriptionDto.getActualStatusId(), createdBy, allSubscription);


        LocalDateTime startDate = LocalDate.now().atStartOfDay();

        assignChefToSubscription(createdBy, reassignChefToSubscriptionDto
                .getChefId(), startDate, allSubscription);

        return success();
    }

    private void assignChefToSubscription(String createdBy, Long chefId, LocalDateTime startDate, AllSubscription
            allSubscription) {
        SubscribedChef subscribedChef = getSubscribedChef(createdBy,
                chefId, allSubscription, entityManager);


        LocalDateTime endDate = allSubscription.getEndDate().toLocalDateTime();

        List<SubscriptionActual> subscriptionActualList = new ArrayList<>();
        for (LocalDateTime date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            final Timestamp dateTime = Timestamp.valueOf(date);
            allSubscription.getSubscriptionMeals().forEach(subscriptionMeal -> {
                subscriptionActualList.add(getSubscriptionActual(createdBy,
                        chefId, dateTime,
                        allSubscription,
                        subscriptionMeal.getMealType()));
            });
        }

        subscriptionActualRepository.saveAll(subscriptionActualList);
        subscribedChefRepository.saveAndFlush(subscribedChef);
    }

    private void endSubscriptionsForAllChefs(Long actualStatusId, String createdBy, AllSubscription allSubscription) {
        Collection<SubscribedChef> allChefs = allSubscription.getSubscribedChefs();
        if (allChefs != null && !allChefs.isEmpty()) {
            allChefs.forEach(subscribedChef -> {
                endSubProcessing(actualStatusId, subscribedChef.getChefId(), allSubscription,
                        createdBy);
            });
        }
    }

    private void endSubProcessing(Long actualStatusId, Long chefId, AllSubscription allSubscription, String createdBy) {
        ChefSubEarning chefSubEarning = new ChefSubEarning();

        chefSubEarning.setAllSubscription(allSubscription);
        chefSubEarning.setChefId(chefId);
        chefSubEarning.setCreatedBy(createdBy);
        chefSubEarning.setCreatedDateTime(Timestamp.valueOf(LocalDateTime.now()));
        chefSubEarning.setSyncDateTime(Timestamp.valueOf(LocalDateTime.now()));

        //todo: passing 1 for cancelled reason, fix as per requirement.
        chefSubEarning.setOffsetReason(entityManager.getReference(OffsetReason.class, 1L));

        calculateAndSetChefEarning(chefSubEarning);

        chefSubEarningRepository.save(chefSubEarning);

        subscriptionActualRepository.cancelSubscriptionActual(actualStatusId, chefId, Timestamp.valueOf(LocalDate.now()
                .atStartOfDay()));
    }

    private void calculateAndSetChefEarning(ChefSubEarning chefSubEarning) {
        //todo: complete these calculations
        chefSubEarning.setChefEarnings(100.0);
        chefSubEarning.setOffsetValue(10.0);
        chefSubEarning.setTaxComponent1(0.0);
        chefSubEarning.setTaxComponent2(0.0);
    }


}

