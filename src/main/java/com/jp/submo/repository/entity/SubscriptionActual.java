package com.jp.submo.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;


/**
 * @author chetan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription_actuals")
public class SubscriptionActual extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_actual_id")
    private Long subscriptionActualId;

    @Column(name = "actual_status_id")
    private BigInteger actualStatusId;

    @Column(name = "chef_id")
    private BigInteger chefId;

    private Timestamp date;
    @Column(name = "end_date")
    private Timestamp endDate;
    @Column(name = "start_date")
    private Timestamp startDate;

    @ManyToOne
    @JoinColumn(name = "subscription_meal_id")
    private SubscriptionMeal subscriptionMeal;


}