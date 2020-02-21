package com.jp.submo.repository;

import com.jp.submo.repository.entity.AllSubscription;
import com.jp.submo.repository.entity.MealType;
import com.jp.submo.repository.entity.SubscriptionActual;
import com.jp.submo.repository.entity.SubscriptionMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * @author chetan
 */

@Repository
public interface SubscriptionActualRepository extends JpaRepository<SubscriptionActual, Long> {
    SubscriptionActual findOneBySubscriptionAndMealTypeAndDateAndActualStatusId(AllSubscription allSubscription, MealType mealType, Timestamp
            date, Long actualStatusId);

    @Modifying
    @Query("Update SubscriptionActual sa SET sa.actualStatusId=:statusId WHERE sa.actualStatusId=1L and sa.date > :today and sa.chefId=:chefId")
    void cancelSubscriptionActual(@Param("statusId") Long statusId, @Param("chefId") Long chefId,@Param("today") Timestamp today);
}
