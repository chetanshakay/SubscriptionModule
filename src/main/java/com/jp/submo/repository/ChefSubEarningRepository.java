package com.jp.submo.repository;

import com.jp.submo.repository.entity.ChefSubEarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chetan
 */
@Repository
public interface ChefSubEarningRepository extends JpaRepository<ChefSubEarning,Long>{
}
