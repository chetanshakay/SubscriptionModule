package com.jp.submo.repository;

import com.jp.submo.repository.entity.SubscriptionMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chetan
 */
@Repository
public interface SubscriptionMealRepository extends JpaRepository<SubscriptionMeal,Long> {
}
