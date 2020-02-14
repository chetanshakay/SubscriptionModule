package com.jp.submo.repository;

import com.jp.submo.repository.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chetan
 */
@Repository
public interface SubscriptionStatusRepository extends JpaRepository<SubscriptionStatus,Long> {
}
