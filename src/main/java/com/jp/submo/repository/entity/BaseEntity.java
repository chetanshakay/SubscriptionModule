package com.jp.submo.repository.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * @author chetan
 */
@Data
@MappedSuperclass
public  class BaseEntity {

    @Column(name = "createdby", updatable=false)
     String createdBy;
    @Column(name = "modifiedby",insertable = false)
     String modifiedBy;
    @Column(name = "createdts",updatable = false)
     Timestamp createdDateTime;
    @Column(name = "lastmodifiedts")
     Timestamp lastModifiedDateTime;
    @Column(name = "synctimestamp")
     Timestamp syncDateTime;
    @Column(name = "softdeleteflag")
     byte softDeleteFlag;
}
