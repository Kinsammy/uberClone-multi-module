package io.samtech.entity.rdb;

import io.samtech.entity.AbstractAuditable;
import jakarta.persistence.Id;

import java.io.Serializable;

public abstract class AbstractJdbcEntity<ID extends Serializable> extends AbstractAuditable<Long> {
    @Id
    protected ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
