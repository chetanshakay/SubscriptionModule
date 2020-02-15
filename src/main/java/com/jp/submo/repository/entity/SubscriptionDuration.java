package com.jp.submo.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author chetan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription_duration_pl")
public class SubscriptionDuration extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_duration_id", updatable = false, nullable = false)
    private Long subscriptionDurationId;
    @Column(name = "description")
    private String description;

}

