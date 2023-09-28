package io.samtech.entity.mongodb;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document
@Data
@Builder
public class MongoAccessToken implements Serializable {
    private String id;
    private Long userId;
    private Integer status;
    private LocalDateTime expiredAt;
    @DocumentReference(lazy = true)
    private MongoRefreshToken refreshToken;
}
