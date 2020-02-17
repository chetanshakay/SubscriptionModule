
package com.jp.submo.repository;

import com.jp.submo.repository.entity.SubscriptionPayment;
import com.jp.submo.repository.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chetan
 */
@Repository
public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment, Long> {

}
