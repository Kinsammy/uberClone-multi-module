package io.samtech.entity.rdb;

import io.samtech.constants.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = CommonConstants.EntityName.TOKEN)
public class Token extends AbstractJdbcEntity<Long> {
    private String token;
    private Integer tokenType;
    private boolean expired;
    private boolean revoked;
    private final LocalDateTime expiryTime = createdDate.plusMinutes(5);

    @MappedCollection(idColumn = "token_id")
    private User user;
}
