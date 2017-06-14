package com.template.core.model;

import java.util.Date;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseEntity {
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}