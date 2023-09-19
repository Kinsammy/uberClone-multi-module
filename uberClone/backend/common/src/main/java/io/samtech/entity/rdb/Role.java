package io.samtech.entity.rdb;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

import static io.samtech.constants.CommonConstants.EntityName.ROLE;

@Getter
@Setter
@Builder

@Table(value = ROLE)
public class Role extends AbstractJdbcEntity<Long>{
    private String name;
    private String description;
    private Integer status;

    @Transient
    private Set<Authority> authorities;

    @MappedCollection(idColumn = "role_id")
    private Set<RoleUserRef> userRefs;

    @MappedCollection(idColumn = "role_id")
    private Set<RoleAuthorityRef> authorityRefs;
}
