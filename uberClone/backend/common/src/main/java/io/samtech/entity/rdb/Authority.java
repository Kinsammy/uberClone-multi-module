package io.samtech.entity.rdb;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

import static io.samtech.constants.CommonConstants.EntityName.AUTHORITY;

@Getter
@Setter
@ToString
@Table(value = AUTHORITY)
@Builder
public class Authority extends AbstractJdbcEntity<Long> {
    private Long featureId;
    private String name;
    private String description;
    private Integer status;

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass()!= obj.getClass()) return false;
      Authority authority = (Authority) obj;
      return Objects.equals(featureId, authority.featureId) && Objects.equals(name, authority.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(featureId, name);
    }
}
