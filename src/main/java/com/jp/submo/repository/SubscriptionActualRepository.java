package com.jp.submo.repository;

import com.jp.submo.repository.entity.SubscriptionActual;
import com.jp.submo.repository.entity.SubscriptionMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * @author chetan
 */

@Repository
public interface SubscriptionActualRepository extends JpaRepository<SubscriptionActual, Long> {
    SubscriptionActual findOneBysubscriptionMealAndDateAndActualStatusId(SubscriptionMeal subscriptionMeal, Timestamp
            date, Long actualStatusId);
}
