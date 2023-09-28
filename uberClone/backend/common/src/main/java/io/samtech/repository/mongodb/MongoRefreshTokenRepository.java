package io.samtech.repository.mongodb;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.mongodb.MongoRefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MongoRefreshTokenRepository extends CrudRepository<MongoRefreshToken, String> {
    default Optional<MongoRefreshToken> findActiveMongoRefreshTokenId(String id){
        return findActiveMongoRefreshTokenIdAfterAndStatus(id, CommonConstants.EntityStatus.ACTIVE);
    }

    Optional<MongoRefreshToken> findActiveMongoRefreshTokenIdAfterAndStatus(String id, Integer status);

    default void deactivateRefreshTokenById(String id) {
        this.findById(id).ifPresent(mongoRefreshToken -> {
            mongoRefreshToken.setStatus(CommonConstants.EntityStatus.INACTIVE);
            save(mongoRefreshToken);
        });
    }
}
