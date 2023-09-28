package io.samtech.repository.mongodb;

import io.samtech.constants.CommonConstants;
import io.samtech.entity.mongodb.MongoAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface MongoAccessTokenRepository extends CrudRepository<MongoAccessToken, String> {
    boolean existsMongoAccessTokenByIdAndStatus(String id, Integer status);

    default boolean existsActiveMongoAccessTokenById(String id) {
        return existsMongoAccessTokenByIdAndStatus(id, CommonConstants.EntityStatus.ACTIVE);
    }

    default void deactivateAccessTokenById(String id) {
        this.findById(id).ifPresent(mongoAccessToken -> {
            mongoAccessToken.setStatus(CommonConstants.EntityStatus.INACTIVE);
            save(mongoAccessToken);
        });
    }
}
