package com.ics.oauth2server.common.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public @Data class BaseEntity implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", length = 19)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", length = 19)
    private Date updatedDate;
//    @Column(name = "is_flag")
//    private Integer isFlag = 1;
//    @ManyToOne
//    @JoinColumn(name="created_by")
//    private CreatedUpdatedBy createdBy;
//    @ManyToOne
//    @JoinColumn(name="updated_by")
//    private CreatedUpdatedBy updatedBy;
//    @ManyToOne
//    @JoinColumn(name = "state_id")
//    private State state;
}
