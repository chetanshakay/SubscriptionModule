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
@Table(name="subscription_payments")
@DynamicUpdate
public class SubscriptionPayment extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sub_payment_id")
	private Long subPaymentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="payment_mode")
	private PaymentMode paymentMode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="payment_status")
	private PaymentStatus paymentStatus;

	@Column(name="payment_time")
	private Timestamp paymentTime;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="subscription_id")
	private AllSubscription allSubscription;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="third_party_provider")
	private ThirdPartyProvider thirdPartyProvider;

	@Column(name="total_amount_paid")
	private double totalAmountPaid;

	@Column(name="trans_ref_key")
	private String transRefKey;

	@Column(name="transaction_comment")
	private String transactionComment;

}
