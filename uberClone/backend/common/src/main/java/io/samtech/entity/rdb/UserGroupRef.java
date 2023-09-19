package io.samtech.entity.rdb;

import io.samtech.constants.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

import static io.samtech.constants.CommonConstants.EntityName.GROUP_MEMBER;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = GROUP_MEMBER)
class UserGroupRef implements Serializable {

    private Long groupId;
}
