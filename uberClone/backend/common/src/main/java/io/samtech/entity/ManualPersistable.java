package io.samtech.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Objects;

public abstract class ManualPersistable<ID> implements Persistable<ID>, Serializable {

    @Transient
    protected Boolean newEntity;
    @Override
    public abstract ID getId();

    public boolean isNew(){
        return Objects.isNull(newEntity) ? Objects.isNull(getId()) : newEntity;    }

}
