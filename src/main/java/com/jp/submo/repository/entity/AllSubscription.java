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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author chetan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "all_subscriptions")
@DynamicUpdate
public class AllSubscription extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id", updatable = false, nullable = false)
    private Long subscriptionId;

    @Column(name = "description")
    private String description;
    @Column(name="number_of_people")
    private Long noOfPeople;

    @Column(name = "start_date")
    private Timestamp startDate;
    private Timestamp endDate;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private UserProfiles userProfile;

    @ManyToOne()
    @JoinColumn(name="subscription_duration_id")
    private SubscriptionDuration subscriptionDuration;

    @ManyToOne()
    @JoinColumn(name="subscription_status_id")
    private SubscriptionStatus subscriptionStatus;

    @OneToOne(mappedBy = "allSubscription")
    private SubscriptionCost subscriptionCost;
    @OneToOne(mappedBy = "allSubscription")
    private SubscriptionPayment subscriptionPayment;

    @OneToMany(mappedBy = "allSubscription")
    private Collection<SubscriptionMeal> subscriptionMeals;

    @OneToMany(mappedBy = "subscription")
    private Collection<SubscribedChef> subscribedChefs;

}
