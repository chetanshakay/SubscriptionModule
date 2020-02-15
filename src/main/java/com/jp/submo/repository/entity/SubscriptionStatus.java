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
import java.time.LocalDateTime;

/**
 * @author chetan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription_status_pl")
public class SubscriptionStatus extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_status_id", updatable = false, nullable = false)
    private Long subscriptionStatusId;
    @Column(name = "description")
    private String description;

}
