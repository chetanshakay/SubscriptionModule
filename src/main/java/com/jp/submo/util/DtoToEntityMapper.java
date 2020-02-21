package com.jp.submo.util;

import com.jp.submo.dto.AssignChefToSubscriptionDto;
import com.jp.submo.dto.SubscriptionCostDto;
import com.jp.submo.dto.SubscriptionDto;
import com.jp.submo.dto.SubscriptionPaymentDto;
import com.jp.submo.repository.entity.AllSubscription;
import com.jp.submo.repository.entity.MealType;
import com.jp.submo.repository.entity.PaymentMode;
import com.jp.submo.repository.entity.PaymentStatus;
import com.jp.submo.repository.entity.SubscribedChef;
import com.jp.submo.repository.entity.SubscribedChefStatus;
import com.jp.submo.repository.entity.SubscriptionActual;
import com.jp.submo.repository.entity.SubscriptionCost;
import com.jp.submo.repository.entity.SubscriptionDuration;
import com.jp.submo.repository.entity.SubscriptionMeal;
import com.jp.submo.repository.entity.SubscriptionPayment;
import com.jp.submo.repository.entity.SubscriptionStatus;
import com.jp.submo.repository.entity.ThirdPartyProvider;
import com.jp.submo.repository.entity.UserProfiles;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author chetan
 */
public class DtoToEntityMapper {

    public static AllSubscription getSubscription(SubscriptionDto subscriptionDto, EntityManager entityManager, String createdBy) {

        AllSubscription subscription = new AllSubscription();
        subscription.setCreatedBy(createdBy);
        subscription.setCreatedDateTime(Timestamp.valueOf(LocalDateTime.now()));
        subscription.setEndDate(Timestamp.valueOf(subscriptionDto.getEndDate().atStartOfDay()));
        subscription.setStartDate(Timestamp.valueOf(subscriptionDto.getStartDate().atStartOfDay()));
        subscription.setDescription(subscriptionDto.getDescription());
        subscription.setNoOfPeople(subscriptionDto.getNoOfPeople());
        subscription.setSubscriptionStatus(entityManager.getReference(SubscriptionStatus.class, 1L));
        subscription.setSubscriptionDuration(entityManager.getReference(SubscriptionDuration.class, subscriptionDto
                .getSubscriptionDuration()));

        subscription.setUserProfile(entityManager.getReference(UserProfiles.class, subscriptionDto.getUserId()));
        return subscription;
    }


    public static SubscriptionCost getSubscriptionCost(SubscriptionDto subscriptionDto, AllSubscription
            allSubscription,String createdBy) {
        SubscriptionCostDto costDto = subscriptionDto.getSubscriptionCost();
        SubscriptionCost cost = new SubscriptionCost();
        cost.setAllSubscription(allSubscription);
        cost.setActualCost(costDto.getActualCost());
        cost.setCreatedBy(createdBy);
        cost.setCreatedDateTime(Timestamp.valueOf(LocalDateTime.now()));
        cost.setDiscount(costDto.getDiscount());
        cost.setDiscountRefKey(costDto.getDiscountRefKey());
        cost.setTaxesComponent1(costDto.getTaxesComponent1());
        cost.setTaxesComponent2(costDto.getTaxesComponent2());
        cost.setTaxesComponent3(costDto.getTaxesComponent3());
        cost.setTotalAmountPaid(costDto.getTotalAmountPaid());
        cost.setTotalCost(costDto.getTotalCost());
        return cost;
    }

    public static SubscriptionPayment getSubscriptionPayment(SubscriptionDto subscriptionDto, AllSubscription
            allSubscription, EntityManager entityManager,String createdBy) {
        SubscriptionPaymentDto paymentDto = subscriptionDto.getSubscriptionPayment();
        SubscriptionPayment payment = new SubscriptionPayment();
        payment.setAllSubscription(allSubscription);
        payment.setCreatedBy(createdBy);
        payment.setCreatedDateTime(Timestamp.valueOf(LocalDateTime.now()));
        payment.setPaymentMode(entityManager.getReference(PaymentMode.class, paymentDto.getPaymentMode()));
        payment.setPaymentStatus(entityManager.getReference(PaymentStatus.class
                , 1L));
        payment.setPaymentTime(Timestamp.valueOf(LocalDateTime.now()));
        payment.setThirdPartyProvider(entityManager.getReference(ThirdPartyProvider.class, paymentDto
                .getThirdPartyProvider()));
        payment.setTransactionComment(paymentDto.getTransactionComment());
        payment.setTotalAmountPaid(paymentDto.getTotalAmountPaid());


        return payment;

    }

    public static Collection<SubscriptionMeal> getSubscriptionMeals(SubscriptionDto subscriptionDto, AllSubscription
            allSubscription, EntityManager entityManager,String createdBy) {
        List<SubscriptionMeal> meals = new ArrayList();
        subscriptionDto.getSubscriptionMeals().forEach(mealDto -> {
            SubscriptionMeal meal = new SubscriptionMeal();
            meal.setCreatedBy(createdBy);
            meal.setMealType(entityManager.getReference(MealType.class, mealDto.getMealType()));
            meal.setTime(LocalTime.parse(mealDto.getTime()));
            meal.setCreatedDateTime(Timestamp.valueOf(LocalDateTime.now()));
            meal.setAllSubscription(allSubscription);
            meals.add(meal);
        });
        return meals;
    }

    public static SubscriptionActual getSubscriptionActual(String createdBy,Long chefId,
                                                           Timestamp date,
                                                           AllSubscription allSubscription,
                                                           MealType mealType) {
        SubscriptionActual actual = new SubscriptionActual();
        actual.setCreatedBy(createdBy);
        actual.setChefId(chefId);
        actual.setMealType(mealType);
        actual.setActualStatusId(1L);
        actual.setDate(date);
        actual.setSubscription(allSubscription);
        actual.setCreatedDateTime(Timestamp.valueOf(LocalDateTime.now()));
        return actual;

    }

    public static SubscribedChef getSubscribedChef(String createdBy,Long chefId,
                                                   AllSubscription allSubscription, EntityManager entityManager) {
        SubscribedChef chef = new SubscribedChef();
        chef.setChefId(chefId);
        chef.setSubscribedChefStatus(entityManager.getReference(SubscribedChefStatus.class, 1L));
        chef.setSubscription(allSubscription);
        chef.setCreatedBy(createdBy);
        chef.setCreatedDateTime(Timestamp.valueOf(LocalDateTime.now()));
        chef.setStartDate(allSubscription.getStartDate());
        chef.setEndDate(allSubscription.getEndDate());
        return chef;
    }
}
