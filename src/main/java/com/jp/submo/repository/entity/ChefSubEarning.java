package com.jp.submo.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

/**
 * @author chetan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chef_sub_earnings")
public class ChefSubEarning extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chef_sub_earning_id")
    private Long chefSubEarningId;

    @Column(name="chef_id")
    private Long chefId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subscription_id")
    private AllSubscription allSubscription;

    @Column(name = "chef_earnings")
    private Double chefEarnings;

    @Column(name = "tax_component_1")
    private Double taxComponent1;

    @Column(name = "tax_component_2")
    private Double taxComponent2;

    @Column(name = "offset_value")
    private Double offsetValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="offset_reason")
    private OffsetReason offsetReason;
}
