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
import javax.persistence.OneToOne;
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
@Table(name = "subscription_costs")
public class SubscriptionCost extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_costs_id")
    private Long subscriptionCostsId;

    @Column(name = "actual_cost")
    private double actualCost;

    private double discount;

    @Column(name = "discount_ref_key")
    private Long discountRefKey;

    @OneToOne
    @JoinColumn(name = "subscription_id")
    private AllSubscription allSubscription;

    @Column(name = "taxes_component_1")
    private double taxesComponent1;

    @Column(name = "taxes_component_2")
    private double taxesComponent2;

    @Column(name = "taxes_component_3")
    private double taxesComponent3;

    @Column(name = "total_amount_paid")
    private double totalAmountPaid;

    @Column(name = "total_cost")
    private double totalCost;

}