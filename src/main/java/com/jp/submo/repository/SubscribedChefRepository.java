package com.jp.submo.repository;

import com.jp.submo.repository.entity.SubscribedChef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chetan
 */

@Repository
public interface SubscribedChefRepository extends JpaRepository<SubscribedChef,Long> {
}
