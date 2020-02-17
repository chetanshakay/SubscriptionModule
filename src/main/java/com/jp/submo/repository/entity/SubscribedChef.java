package com.jp.submo.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author chetan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="subscribed_chefs")
@DynamicUpdate
public class SubscribedChef extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscribed_chef_id")
    private Long subscribedChefId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subscription_id")
    private AllSubscription subscription;

    @Column(name = "chef_id")
    private Long chefId;

    @Column(name="start_date")
    private Timestamp startDate;

    @Column(name="end_date")
    private Timestamp endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subscribed_chef_status_id")
    private SubscribedChefStatus subscribedChefStatus;


}
