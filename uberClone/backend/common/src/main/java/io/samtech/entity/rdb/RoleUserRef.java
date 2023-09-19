package io.samtech.entity.rdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

import static io.samtech.constants.CommonConstants.EntityName.USER_ROLE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = USER_ROLE)
class RoleUserRef implements Serializable {
    private Long userId;
}
