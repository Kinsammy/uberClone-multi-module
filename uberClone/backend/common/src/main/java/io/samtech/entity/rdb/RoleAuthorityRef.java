package io.samtech.entity.rdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

import static io.samtech.constants.CommonConstants.EntityName.ROLE_AUTHORITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = ROLE_AUTHORITY)
public class RoleAuthorityRef implements Serializable {
    private Long authorityId;
}
