package com.jp.submo.repository;

import com.jp.submo.repository.entity.SubscriptionCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chetan
 */
@Repository
public interface SubscriptionCostRepository extends JpaRepository<SubscriptionCost,Long> {
}
