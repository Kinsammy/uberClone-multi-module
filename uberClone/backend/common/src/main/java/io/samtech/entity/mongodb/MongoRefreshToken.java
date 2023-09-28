package io.samtech.entity.mongodb;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document
@Data
@Builder
public class MongoRefreshToken implements Serializable {

    private String id;

    private String accessTokenId;

    private Long userId;

    private Integer status;

    private LocalDateTime expiredAt;
}
