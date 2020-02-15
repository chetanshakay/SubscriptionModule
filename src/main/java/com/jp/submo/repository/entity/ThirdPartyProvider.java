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
@Table(name = "third_party_provider_pl")
public class ThirdPartyProvider extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "third_party_provider")
    private Long thirdPartyProviderId;

    @Column(name = "third_party_provider_description")
    private String thirdPartyProviderDescription;
}
