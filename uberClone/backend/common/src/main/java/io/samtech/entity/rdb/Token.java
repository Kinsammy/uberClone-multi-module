package io.samtech.entity.rdb;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.models.User;
import jakarta.persistence.*;
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
@Entity
//@Table(value = CommonConstants.EntityName.TOKEN)
public class Token  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private Integer tokenType;
    private boolean expired;
    private boolean revoked;
    private final LocalDateTime createAt = LocalDateTime.now();
    private final LocalDateTime expiryTime = createAt.plusMinutes(5);

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
