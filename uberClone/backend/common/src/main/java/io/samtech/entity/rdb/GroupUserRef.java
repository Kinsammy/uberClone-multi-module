package io.samtech.entity.rdb;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

import static io.samtech.constants.CommonConstants.EntityName.GROUP_MEMBER;

@Getter
@Setter
@ToString
@Table(value = GROUP_MEMBER)
public class GroupUserRef implements Serializable {
    private Long userId;
}
