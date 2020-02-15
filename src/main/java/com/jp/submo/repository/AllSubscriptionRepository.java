package com.jp.submo.repository;

import com.jp.submo.repository.entity.AllSubscription;
import com.jp.submo.repository.entity.UserProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chetan
 */
@Repository
public interface AllSubscriptionRepository extends JpaRepository<AllSubscription,Long> {

}
