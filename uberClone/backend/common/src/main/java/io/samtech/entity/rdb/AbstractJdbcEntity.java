package io.samtech.entity.rdb;

import io.samtech.entity.AbstractAuditable;
import jakarta.persistence.Id;
import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class AbstractJdbcEntity<ID extends Serializable> extends AbstractAuditable<Long> {
    @Id
    protected ID id;

    public void setId(ID id) {
        this.id = id;
    }
}
