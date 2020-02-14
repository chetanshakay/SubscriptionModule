package com.jp.submo.repository.entity;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * @author chetan
 */
@Data

public abstract class BaseEntity {

    @Column(name = "createdby")
    protected String createdBy;
    @Column(name = "modifiedby")
    protected String modifiedBy;
    @Column(name = "createdts")
    protected Timestamp createdDateTime;
    @Column(name = "lastmodifiedts")
    protected Timestamp lastModifiedDateTime;
    @Column(name = "synctimestamp")
    protected Timestamp syncDateTime;
    @Column(name = "softdeleteflag")
    protected byte softDeleteFlag;
}
